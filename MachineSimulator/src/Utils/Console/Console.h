#ifndef CONSOLE
#define CONSOLE

#include <stdio.h>

void smokeTestHeader(char *name){
    printf("::-------------------%s--------------------::\n\n", name);
}



void smokeTestBottom(){
    printf("\n\n::---------------------------------------::\n\n");
}

#endif