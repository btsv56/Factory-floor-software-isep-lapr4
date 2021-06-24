#include "../../config/config.h"
#include "../../Simulation/Domain/Machine.h"
#include <stdlib.h>

typedef struct
{
    char version;
    char code;
    short id;
    short data_length;
    char *raw_data;
} Message;

void newMessage(Message *m, char id, char code, short data_length, char *raw_data)
{
    m->version = PROTOCOL_VERSION;
    m->code = code;
    m->id = id;
    m->data_length = data_length;
    m->raw_data = raw_data;
}

void newMessageWithMachine(Message *m, Machine *machine, unsigned short data_length, char *raw_data)
{
    m->version = PROTOCOL_VERSION;
    m->code = machine->status;
    m->id = machine->id;
    m->data_length = data_length;
    m->raw_data = raw_data;
}

