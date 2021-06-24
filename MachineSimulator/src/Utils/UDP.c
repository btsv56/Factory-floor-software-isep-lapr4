#include <strings.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

void getaddrinfo_(const char *node, const char *service, const struct addrinfo *hints, struct addrinfo **res)
{
    int err;
    if ((err = getaddrinfo(node, service, hints, res)))
    {
        printf("Failed to get server address, error: %s\n", gai_strerror(err));
        exit(1);
    }
}

int sock_(struct addrinfo *list)
{
    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1)
    {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }
    return sock;
}

void bind_(int sock, struct addrinfo *list)
{
    if (bind(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
    {
        perror("Failed to bind socket");
        close(sock);
        freeaddrinfo(list);
        exit(1);
    }
}