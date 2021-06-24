/*
 * main.c
 *
 *  Created on: 19/05/2020
 *      Author: isep
 */
#include <pthread.h>
#include "ImportFilesService.h"
#include "args.h"
#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>
#include "../../CommunicationTCP/Application/TCPCommunicationService.h"
#include "../../CommunicationUDP/Application/UDPCommunicationService.h"

#define NUM_MACHINES 10

pthread_t thread_ids;
pthread_t thread_MS;
pthread_t thread_cf;
pthread_mutex_t mux;
pthread_mutex_t mux_reset;
pthread_cond_t cond_reset;
int sock ;
SSL *sslConn;
Machine *m=NULL;

/*Turns the machine on and starts the communications protocols */
void *turnOnMachine(void *arg) {
	if(pthread_mutex_init(&mux, NULL)!=0) {perror("Error creating mutex\n");exit(1);}
	if(pthread_mutex_init(&mux_reset, NULL)!=0) {perror("Error creating mutex\n");exit(1);}
	if(pthread_cond_init(&cond_reset, NULL)!=0) {perror("Error creating conditional variable\n");exit(1);}
	args *a;
	a=(args*) arg;
	sslConn = validateMachine(a->id,&sock);
	m = createMachine(a->id, a->time, a->internalCode, sock,sslConn);
	if(pthread_create(&thread_ids, NULL, sendmessages_func, (void*) NULL)!=0) {perror("Error creating tcp thread\n");exit(1);}
	if(pthread_create(&thread_MS, NULL, establishConnectionToTheMonitoringSystem, (void *) NULL)!=0) {perror("Error creating udp thread\n");exit(1);}
	if(pthread_create(&thread_cf,NULL,createConfigSocket,(void *)&(m->id))!=0) {perror("Error creating tcp config thread\n");exit(1);}
	pthread_exit((void*)NULL);
}

/*Turns the machine off*/
int turnOffMachines() {
	int i;

	if (pthread_join(thread_ids, NULL) != 0) {
		perror("Error in closing thread tcp");
		exit(1);
	}
	if (pthread_join(thread_MS, NULL) != 0) {
		perror("Error in closing thread udp");
		exit(1);
	}
	if (pthread_join(thread_cf, NULL) != 0) {
		perror("Error in closing thread tcp config");
		exit(1);
	}
	closeSocket(sock,sslConn);
	if(pthread_mutex_destroy(&mux)!=0) {perror("Error destroying mutex\n");exit(1);}
	if(pthread_mutex_destroy(&mux_reset)!=0) {perror("Error destroying mutex\n");exit(1);}
	if(pthread_cond_destroy(&cond_reset)!=0) {perror("Error destroying conditional variable\n");exit(1);}
	printf("Closed threads\n");
	return 0;
}

