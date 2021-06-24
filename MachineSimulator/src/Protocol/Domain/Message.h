#ifndef MESSAGE
#define MESSAGE

#include "../../Simulation/Domain/Machine.h"
typedef struct
{
    char version;
    char code;
    short id;
    short data_length;
    char *raw_data;
} Message;

void newMessage(Message *m, char id, char code, short data_length, char *raw_data);
void newMessageWithMachine(Message *m, Machine *machine, unsigned short data_length, char *raw_data);

#endif
