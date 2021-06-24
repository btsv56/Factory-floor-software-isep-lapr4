/*
 * ImportFilesFactory.h
 *
 *  Created on: 19/05/2020
 *      Author: isep
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "ImportFilesStandard.h"

typedef struct {
	ImportFilesStrategy strategy;
}ImportStruct;

ImportStruct* build(char *internalCode) {
	ImportStruct *imp=(ImportStruct*)malloc(sizeof(ImportStruct));
	imp->strategy=(ImportFilesStrategy)importStandard;
	return imp;
}
