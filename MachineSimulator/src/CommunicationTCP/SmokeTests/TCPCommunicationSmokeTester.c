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
#include <openssl/crypto.h>
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include "../Application/TCPCommunicationService.h"
#include "../../Simulation/Domain/Machine.h"
#define BUF_SIZE 300
#define SERVER_PORT "9999"

#define SERVER_SSL_CERT_FILE "server.pem"
#define SERVER_SSL_KEY_FILE "server.key"
#define AUTH_CLIENTS_SSL_CERTS_FILE "authentic-clients.pem"

Machine *m;
pthread_mutex_t mux;


static void receiveHelloMessage(SSL *sslConn) {
	char hellomessage[6];
	char exp=0;
	if(SSL_read(sslConn,&hellomessage,6)==-1) {
		perror("Hello message error\n");
		exit(1);
	}
	if(*(hellomessage+1)!=exp) {
		perror("Hello message denied\n");
		exit(1);
	}
}

static void sendAckMessage(SSL *sslConn) {
	char *ackMessage=(char*) malloc(2);
	sprintf(ackMessage,"%c%c",0,150);
	if(SSL_write(sslConn,ackMessage,2)==-1) {perror("Sending confirmation error\n");exit(1);}
}

static void sendConfigFile(SSL *sslConn) {
	char config[4];
	sprintf(config,"%c",0);
	sprintf((config+1),"%c",2);
	sprintf((config+2),"%c%c",0,1);
	if(SSL_write(sslConn,config,4)==-1) {perror("Config message error\n");exit(1);}
}

static void receiveConfigResponse(SSL *sslConn) {
	char response[100];
	char exp=150;
	if(SSL_read(sslConn,&response,100) ==-1) {
		perror("Error receiving config response\n");
		exit(1);
	}
	if(*(response+1)!=exp) {
		perror("Wrong message type\n");
		exit(1);
	}
}

static void receiveMessage(SSL *sslConn) {
	char message[100];
	char exp=1;
	if(SSL_read(sslConn,&message,100)==-1){
		perror("Message not received\n");
		exit(1);
	}
	if(*(message+1)!=exp) {
		perror("Wrong message code\n");
		exit(1);
	}
}

void* client_func(void* arg) {
	int sock;
	SSL *sslConn;
	sslConn=validateMachine(1,&sock);
	char message1[20],message2[20];
	sprintf(message1,"%c%c%c%c%c%cNova mensagem1",0,1,1,0,14,0);
	sprintf(message2,"%c%c%c%c%c%cNova mensagem2",0,1,1,0,14,0);
	exportMessage(message1,sslConn);
	exportMessage(message2,sslConn);
	pthread_exit((void*)NULL);
}

int test(void) {
	m=(Machine*) malloc(sizeof(Machine));
	pthread_mutex_init(&mux, NULL);
	pthread_t t,t2;
	struct sockaddr_storage from;
	int err, newSock, sock,i;
	unsigned int adl;
	char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
	struct addrinfo req, *list;
	bzero((char *)&req,sizeof(req));
	// requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
	req.ai_family = AF_INET6;
	req.ai_socktype = SOCK_STREAM;	
	req.ai_flags = AI_PASSIVE; // local address
	
	err=getaddrinfo(NULL, SERVER_PORT , &req, &list);
	if(err) { 
		printf("Failed to get local address, error: %s\n",gai_strerror(err)); 
		exit(1); 
	}
	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
	if(sock==-1) { 
		perror("Failed to open socket"); 
		freeaddrinfo(list); 
		exit(1);
	}
	if(bind(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
		perror("Bind failed");
		close(sock);
		freeaddrinfo(list);
		exit(1);
	}
	freeaddrinfo(list);
	listen(sock,SOMAXCONN);
	
	const SSL_METHOD *method;
	SSL_CTX *ctx;
	method = SSLv23_server_method();
	ctx = SSL_CTX_new(method);	
	// Load the server's certificate and key
	SSL_CTX_use_certificate_file(ctx, SERVER_SSL_CERT_FILE, SSL_FILETYPE_PEM);
	SSL_CTX_use_PrivateKey_file(ctx, SERVER_SSL_KEY_FILE, SSL_FILETYPE_PEM);
	if (!SSL_CTX_check_private_key(ctx)) {
		puts("Error loading server's certificate/key");
		close(sock);
		exit(1);
	}
	// THE CLIENTS' CERTIFICATES ARE TRUSTED
	SSL_CTX_load_verify_locations(ctx, AUTH_CLIENTS_SSL_CERTS_FILE, NULL);
	// Restrict TLS version and cypher suite
	SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
	SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");
	// Clients must provide a trusted certificate, the handshake will fail otherwise
	SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);

	puts("Accepting TCP connections (IPv6/IPv4). Use CTRL+C to terminate the server");
	pthread_create(&t,NULL,client_func,NULL);
	pthread_create(&t2,NULL,client_func,NULL);
	adl=sizeof(from);
	for(i=0;i<2;i++) { 
		newSock=accept(sock,(struct sockaddr *)&from,&adl);
		if(!fork()) {
			close(sock);
			getnameinfo((struct sockaddr *)&from,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE, NI_NUMERICHOST|NI_NUMERICSERV);
			printf("New connection from %s, port number %s\n", cliIPtext, cliPortText);
			SSL *sslConn = SSL_new(ctx);
			SSL_set_fd(sslConn, newSock);
			if(SSL_accept(sslConn)!=1) {
				puts("TLS handshake error: client not authorized");
				SSL_free(sslConn);
				close(newSock);
				exit(1);
			}
			printf("TLS version: %s\nCypher suite: %s\n",
			SSL_get_cipher_version(sslConn),SSL_get_cipher(sslConn));
			X509* cert=SSL_get_peer_certificate(sslConn);
			X509_free(cert);
			X509_NAME_oneline(X509_get_subject_name(cert),cliIPtext,BUF_SIZE);
			printf("Client's certificate subject: %s\n",cliIPtext);
			receiveHelloMessage(sslConn);
			printf("Received hello message\n");
			sendAckMessage(sslConn);
			printf("Sent ack message\n");
			sendConfigFile(sslConn);
			printf("Sent config file\n");
			receiveConfigResponse(sslConn);
			printf("Configuration response received\n");
			receiveMessage(sslConn);
			printf("Received message\n");
			receiveMessage(sslConn);
			printf("Received message\n");
			SSL_free(sslConn);
			close(newSock);
			printf("Connection %s, port number %s closed\n", cliIPtext, cliPortText);
			exit(0);
		}
		close(newSock);
	}
	pthread_join(t,NULL);
	pthread_join(t2,NULL);
	pthread_mutex_destroy(&mux);
	sleep(2);
	close(sock);
	return 0;
}
