/*
 * ImportFilesService.c
 *
 *  Created on: 19/05/2020
 *      Author: isep
 */
#include "ImportFiles.h"
#include "ImportFilesFactory.h"
#include "../Domain/Machine.h"
#include <pthread.h>

extern Machine *m;

void* sendmessages_func(void *arg) {
	ImportStruct *import;
	import=build(m->internal_code);
	if(import!=NULL) {
		import->strategy(m->id,m->time,m->sock,m->internal_code);
	}
	pthread_exit((void*)NULL);
}
	
