#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <pthread.h>
#include "../Application/TCPCommunicationService.h"

#define BUF_SIZE 30
#define SERVER_PORT "9999"

Machine *m;
pthread_mutex_t mux;


// read a string from stdin protecting buffer overflow
#define GETS(B,S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

static void sendConfigFile(int sock) {
	char config[4];
	sprintf(config,"%c",0);
	sprintf((config+1),"%c",2);
	sprintf((config+2),"%c%c",0,1);
	if(write(sock,config,4)==-1) {perror("Config message error\n");exit(1);}
}

static void receiveConfigResponse(int sock) {
	char response[100];
	char exp=150;
	if(read(sock,&response,100) ==-1) {
		perror("Error receiving config response\n");
		exit(1);
	}
	if(*(response+1)!=exp) {
		perror("Wrong message type\n");
		exit(1);
	}
}

int testConfigFiles(int argc, char **argv) {
	pthread_t t;
	int id=1;
	m=(Machine*) malloc(sizeof(Machine));
	pthread_mutex_init(&mux, NULL);
	pthread_create(&t,NULL,createConfigSocket,(void *)&id );
	sleep(5);
	int err, sock;
	int i;
	struct addrinfo req, *list;
	if (argc != 2) {
		puts("Server's IPv4/IPv6 address or DNS name is required as argument");
		exit(1);
	}
	bzero((char *) &req, sizeof(req));
	// let getaddrinfo set the family depending on the supplied server address
	req.ai_family = AF_UNSPEC;
	req.ai_socktype = SOCK_STREAM;
	err = getaddrinfo(argv[1], SERVER_PORT, &req, &list);
	if (err) {
		printf("Failed to get server address, error: %s\n", gai_strerror(err));
		exit(1);
	}
	sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
	if (sock == -1) {
		perror("Failed to open socket");
		freeaddrinfo(list);
		exit(1);
	}
	if (connect(sock, (struct sockaddr *) list->ai_addr, list->ai_addrlen)
			== -1) {
		perror("Failed connect");
		freeaddrinfo(list);
		close(sock);
		exit(1);
	}
	for(i=0;i<4;i++) {
		sendConfigFile(sock);
		printf("Config %d sent\n",(i+1));
		receiveConfigResponse(sock);
		printf("Response received\n");
	}
	pthread_mutex_destroy(&mux);
	close(sock);
	exit(0);
}

