/*
 * Machine.h
 *
 *  Created on: 19/05/2020
 *      Author: isep
 */
#ifndef MACHINE
#define MACHINE
#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

typedef struct
{
	int id;
	int time;
	char internal_code[10];
	int sock;
	SSL *sslConn;
	int status;
	int reset;
} Machine;

Machine *createMachine(int id, int time, char *internal_code, int sock,SSL *sslConn);

int getMostRecentStatus(Machine *m);

#endif
