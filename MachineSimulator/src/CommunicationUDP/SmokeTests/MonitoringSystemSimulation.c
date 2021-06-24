#include "../../Utils/UDP.h"
#include "../../Protocol/Application/CommunicationProtocolService.h"
#include "../../Protocol/Domain/Message.h"
#include "../../config/config.h"
#include <errno.h>

#define BCAST_ADDRESS "255.255.255.255"

void startMonitoringSystemSimulation()
{
    int received_count = 0, sent_count = 0;
    Message receivedMessage, helloMessage;
    struct sockaddr_storage serverAddr;
    int sock, val, res = 0;
    unsigned int serverAddrLen;
    char responseLine[UDP_BUF_SIZE], receivedLine[UDP_BUF_SIZE];
    char cliIPtext[UDP_BUF_SIZE], cliPortText[UDP_BUF_SIZE];
    struct addrinfo req, *list;
    struct timeval timeout;
    timeout.tv_sec = 4;
    timeout.tv_usec = 0;

    bzero((char *)&req, sizeof(req));
    req.ai_family = AF_INET; // there's no broadcast address in IPv6, so we request an IPv4 address
    req.ai_socktype = SOCK_DGRAM;
    getaddrinfo_(BCAST_ADDRESS, MACHINE_SERVER_PORT, &req, &list);

    serverAddrLen = list->ai_addrlen;
    memcpy(&serverAddr, list->ai_addr, serverAddrLen); // store the broadcast address for later
    freeaddrinfo(list);

    bzero((char *)&req, sizeof(req));
    req.ai_family = AF_INET;
    req.ai_socktype = SOCK_DGRAM;
    req.ai_flags = AI_PASSIVE;            // local address
    getaddrinfo_(NULL, "0", &req, &list); // Port 0 = auto assign

    sock = sock_(list);

    // activate broadcast permission
    val = 1;
    setsockopt(sock, SOL_SOCKET, SO_BROADCAST, &val, sizeof(val));
    setsockopt(sock, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout, sizeof(timeout));

    bind_(sock, list);

    freeaddrinfo(list);

    while (sent_count < 3)
    {
        sent_count++;
        printf("[CLIENT] INFO--->Sending broadcast HELLO message\n");
        newMessage(&helloMessage, 0, HELLO, 0, NULL);
        messageToPacket(&helloMessage, responseLine);
        sendto(sock, responseLine, UDP_BUF_SIZE, 0, (struct sockaddr *)&serverAddr, serverAddrLen);

        res = recvfrom(sock, receivedLine, UDP_BUF_SIZE, 0, (struct sockaddr *)&serverAddr, &serverAddrLen);
        if (res != -1)
        {
            received_count++;
            receivedLine[res] = 0; /* NULL terminate the string */
            packetToMessage(receivedLine, &receivedMessage);
            printf("[CLIENT] INFO--->Received reply: %hu-%hhu\n", receivedMessage.id, receivedMessage.code);
        } else {
            printf("[CLIENT] INFO--->No responses received.\n");
        }
    }
    printf("[CLIENT] INFO--->Client simulator: %d/%d messeges were successfully received.\n", received_count, sent_count);
    close(sock);
    if (received_count == 0){
        exit(1);
    }
}
