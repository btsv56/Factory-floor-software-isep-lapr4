#include "../../Utils/UDP.h"
#include "../../Simulation/Domain/Machine.h"
#include "../../Protocol/Application/CommunicationProtocolService.h"
#include "../../CommunicationTCP/Application/TCPCommunicationService.h"
#include "../../config/config.h"
#include <pthread.h>
#include <stdio.h>

extern Machine *m;
extern pthread_mutex_t mux;
extern pthread_mutex_t mux_reset;
extern pthread_cond_t cond_reset;

void *establishConnectionToTheMonitoringSystem(void *mach)
{
    struct sockaddr_storage client;
    int sock, res;
    unsigned int adl;
    char receivedLine[UDP_BUF_SIZE], responseLine[UDP_BUF_SIZE];
    char cliIPtext[UDP_BUF_SIZE], cliPortText[UDP_BUF_SIZE];
    Message receivedMessage, responseMessage;
    struct addrinfo req, *list;

    bzero((char *)&req, sizeof(req));
    // request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_DGRAM;
    req.ai_flags = AI_PASSIVE; // local address

    getaddrinfo_(NULL, MACHINE_SERVER_PORT, &req, &list); //machien id dangerous

    sock = sock_(list);

    bind_(sock, list);

    freeaddrinfo(list);

    adl = sizeof(client);
    while (1)
    {
        res = recvfrom(sock, receivedLine, UDP_BUF_SIZE, 0, (struct sockaddr *)&client, &adl);
        packetToMessage(receivedLine, &receivedMessage);
        printf("[MACHINE:%s] Received: id-%hu; code-%hhu; data_length-%hu; received bytes:%d; \n", m->internal_code, receivedMessage.id, receivedMessage.code, receivedMessage.data_length, res);
        switch (receivedMessage.code)
        {
        case HELLO: 
        	if(pthread_mutex_lock(&mux)!=0) {perror("Error locking mutex udp\n");exit(1);}
            newMessageWithMachine(&responseMessage, m, 0, NULL);
            messageToPacket(&responseMessage, responseLine);
            printf("[MACHINE:%s] Sent: id-%hu; code-%hhu; data_length-%hu; \n", m->internal_code, responseMessage.id, responseMessage.code, responseMessage.data_length);
            sendto(sock, responseLine, UDP_BUF_SIZE, 0, (struct sockaddr *)&client, adl);
            if(pthread_mutex_unlock(&mux)!=0) {perror("Error unlocking mutex udp\n");exit(1);}
            break;

        case RESET:
        	if(pthread_mutex_lock(&mux_reset)!=0) {perror("Error locking mutex reset\n");exit(1);}
		m->reset=1;
        	if(pthread_mutex_unlock(&mux_reset)!=0) {perror("Error unlocking mutex reset\n");exit(1);}
            sleep(10); /* Machine is restarting */
            if(pthread_mutex_lock(&mux_reset)!=0) {perror("Error locking mutex reset\n");exit(1);}
            m->reset=0;
            askToResetTheMachine(m, &responseMessage);
            if(pthread_cond_broadcast(&cond_reset)!=0){perror("Error in signal for reset\n");exit(1);}
            if(pthread_mutex_unlock(&mux_reset)!=0) {perror("Error unlocking mutex reset\n");exit(1);}
            messageToPacket(&responseMessage, responseLine);
            printf("[MACHINE:%s] Sent: id-%hu; code-%hhu; data_length-%hu; \n", m->internal_code, responseMessage.id, responseMessage.code, responseMessage.data_length);
            sendto(sock, responseLine, UDP_BUF_SIZE, 0, (struct sockaddr *)&client, adl);
            break;
        
        default:
            printf("[ERROR] Machine %s received a unknown message.\n", m->internal_code);
            break;
        }

    }
    close(sock);
    pthread_exit((void*)NULL);
}
