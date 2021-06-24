Server UDP
===========================================
The solution used in the case of the machines was for each machine to run an udp server waiting to receive messages from the monitoring system. For this to be possible, each machine needs to use different ips but in the same ports, as the request will be made in broadcast.
Each machine will wait for an udp response until it receives any message.
If that message is HELLO, it responds with the status of the machine, if the message is RESET it makes a hello request to the central system via TCP and forwards the response from the Central System to the Monitoring System.





```c
#include "../../Utils/UDP.h"
#include "../../Simulation/Domain/Machine.h"
#include "../../Protocol/Application/CommunicationProtocolService.h"
#include "../../CommunicationTCP/Application/TCPCommunicationService.h"
#include "../../config/config.h"
#include <pthread.h>
#include <stdio.h>

extern Machine *m;
extern pthread_mutex_t mux;

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
        	if(pthread_mutex_lock(&mux)!=0) {perror("Error locking mutex udp\n");exit(1);}
            askToResetTheMachine(m, &responseMessage);
            messageToPacket(&responseMessage, responseLine);
            printf("[MACHINE:%s] Sent: id-%hu; code-%hhu; data_length-%hu; \n", m->internal_code, responseMessage.id, responseMessage.code, responseMessage.data_length);
            sendto(sock, responseLine, UDP_BUF_SIZE, 0, (struct sockaddr *)&client, adl);
            if(pthread_mutex_unlock(&mux)!=0) {perror("Error unlocking mutex udp\n");exit(1);}
            break;
        
        default:
            printf("[ERROR] Machine %s received a unknown message.\n", m->internal_code);
            break;
        }

    }
    close(sock);
    pthread_exit((void*)NULL);
}

```

