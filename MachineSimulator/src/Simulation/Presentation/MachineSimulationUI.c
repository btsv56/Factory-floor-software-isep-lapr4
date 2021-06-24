/*
 * ImportFilesUI.c
 *
 *  Created on: 19/05/2020
 *      Author: isep
 */
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include "../Application/MachineSimulationController.h"

#define GETS(B,S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}


void MachineSimulationUI() {
	args *a=(args*)malloc(sizeof(args));
	if(a==NULL) {
		perror("Error allocating memory");
		exit(1);
	}
	int id,on=0;
	pthread_t thread;
	char *internal_code=(char*)malloc(10);
	if(internal_code==NULL) {
		perror("Error allocating memory");
		exit(1);
	}
	a->internalCode=(char*) malloc(10);
	if(a->internalCode==NULL) {
		perror("Error allocating memory");
		exit(1);
	}
	int time = 0;
	printf("Write a machine id to add it, 0 to terminate\n");
	scanf("%d", &id);
	if (id != 0) {
		printf("Write the time the machine takes to send messages\n");
		scanf("%d%*c", &time);
		printf("Write the machine internal code\n");
		GETS(internal_code, sizeof(internal_code));

		a->id=id;
		a->time=time;
		sprintf(a->internalCode,"%s",internal_code);
		if(pthread_create(&thread, NULL, turnOnMachine,(void*) a)!=0) {
				perror("Error creating machine thread\n");
				exit(1);
		}
		printf("Write one to terminate the machine\n");
		while(on!=1) {
			scanf("%d",&on);
		}
		turnOffMachines();
		if(pthread_join(thread,NULL)!=0) {
			perror("Error closing machine thread");
			exit(1);
		}
		free(a->internalCode);
		free(a);
		free(internal_code);
	}
}

