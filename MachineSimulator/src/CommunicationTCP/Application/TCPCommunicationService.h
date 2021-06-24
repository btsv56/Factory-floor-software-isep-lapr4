#include "../../Protocol/Domain/Message.h"
#include "../../Simulation/Domain/Machine.h"
void *createConfigSocket(void *arg);
SSL *validateMachine(int id,int *sock);
void exportMessage(char *message,SSL *sslConn);
void closeSocket(int sock,SSL *sslConn);
void askToResetTheMachine(Machine *machine, Message *message);
void getReponse(SSL *sslConn);
