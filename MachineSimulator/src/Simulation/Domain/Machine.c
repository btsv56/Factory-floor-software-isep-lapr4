#include <stdlib.h>
#include <string.h>
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

Machine *createMachine(int id, int time, char *internal_code, int sock,SSL *sslConn)
{
	Machine *m = (Machine *)malloc(sizeof(Machine));
	m->id = id;
	m->time = time;
	strcpy(m->internal_code, internal_code);
	m->sock = sock;
	m->sslConn=sslConn;
	m->status=150;
	m->reset=0;
	return m;
}

int getMostRecentStatus(Machine *m)
{
	return m->status;
}
