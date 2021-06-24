/*
 * ImportFilesT3.c
 *
 *  Created on: 19/05/2020
 *      Author: isep
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include "../../CommunicationTCP/Application/TCPCommunicationService.h"
#define BUF_SIZE 100

extern Machine *m;
extern pthread_mutex_t mux;
extern pthread_mutex_t mux_reset;
extern pthread_cond_t cond_reset;

/* Imports and sends messages to central system */
char importStandard() {
	int time;
	char fileName[20];
	FILE *f;
	char firstByteID,secondByteID,firstByteLength,secondByteLength;
	char *line=NULL,*message;
	size_t len=0;
	size_t read=0;
	sprintf(fileName,"./MachineFiles/Machine.%s",m->internal_code);
	f = fopen(fileName, "r");
	if (f == NULL){
		perror("Error while opening the file.\n");
		exit(1);
	}
	while ((read = getline(&line, &len, f)) != -1) {
		if (pthread_mutex_lock(&mux_reset) != 0) {perror("Error locking mutex reset\n");exit(1);}
		if(m->reset==1) {
			pthread_cond_wait(&cond_reset,&mux_reset);
		}
		if (pthread_mutex_unlock(&mux_reset) != 0) {perror("Error unlocking mutex reset\n");exit(1);}
		message=(char *) malloc(BUF_SIZE);
		if (message==NULL) { perror("Error in malloc"); break; }
		firstByteID=(char)(m->id%256);
		secondByteID=(char)(m->id/256);
		firstByteLength=(char)(len%256);
		secondByteLength=(char)(len/256);
		sprintf((message+1),"%s",line);
		exportMessage(message,m->sslConn);
		getReponse(m->sslConn);
		sleep(m->time);
		free(message);
    }
	if (fclose(f)!=0) perror("Error closing file");
	return 0;
}
