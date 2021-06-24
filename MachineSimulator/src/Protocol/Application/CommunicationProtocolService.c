#include "../Domain/Message.h"
#include "../Domain/MessageCodes.h"
#include <string.h>
#include <stdlib.h>


void messageToPacket(Message *m, char *line)
{
    line[0] = m->version;
    line[1] = m->code;
    line[2] = ((char *)&m->id)[0];
    line[3] = ((char *)&m->id)[1];
    line[4] = ((char *)&m->data_length)[0];
    line[5] = ((char *)&m->data_length)[1];
    if (m->raw_data != NULL)
        strcpy(&line[6], m->raw_data);
    else
        line[6] = '\0';
    
}

void packetToMessage(char *line, Message *m)
{
    m->version = line[0];
    m->code = line[1];
    m->id = (short)line[2];
    m->data_length = (short)line[4];
    m->raw_data = (char *)&line[6];
}