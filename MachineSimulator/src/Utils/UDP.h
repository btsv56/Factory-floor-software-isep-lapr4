#ifndef UDP
#define UDP

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

void getaddrinfo_(const char *node, const char *service, const struct addrinfo *hints, struct addrinfo **res);
int sock_(struct addrinfo *list);
void bind_(int sock, struct addrinfo *list);

#endif