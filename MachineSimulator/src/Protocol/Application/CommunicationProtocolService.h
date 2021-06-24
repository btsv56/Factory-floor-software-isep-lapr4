#ifndef PROTOCOL
#define PROTOCOL
#include "../Domain/Message.h"
#include "../Domain/MessageCodes.h"

void messageToPacket(Message *m, char *line);

void packetToMessage(char *line, Message *m);
#endif