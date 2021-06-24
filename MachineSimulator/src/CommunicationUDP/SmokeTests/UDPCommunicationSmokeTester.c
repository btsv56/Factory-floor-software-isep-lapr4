#include <stdio.h>
#include "../../Protocol/Domain/MessageCodes.h"
#include "../../Utils/Console/Console.h"
#include "../../Simulation/Domain/Machine.h"
#include "../../CommunicationUDP/Application/UDPCommunicationService.h"
#include "MonitoringSystemSimulation.h"
#include <pthread.h>
#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

#define MACHINE_ID 1
#define MACHINE_TIME 5
#define MACHINE_INTERNAL_CODE "1000"

void UDPCommunicationSmokeTester(){
    pthread_t thread_ids;

    smokeTestHeader("UDP Communication to Monitoring System");
    SSL *sslConn=NULL;
    printf("Turning on Machine[%d-%d-%s].\n", MACHINE_ID, MACHINE_TIME, MACHINE_INTERNAL_CODE);
    Machine *machine = createMachine(MACHINE_ID, MACHINE_TIME, MACHINE_INTERNAL_CODE, 0,sslConn);
    machine->status = ACK;
    pthread_create(&thread_ids, NULL, establishConnectionToTheMonitoringSystem, (void *)machine);

    printf("Executing client test program.\n");
    startMonitoringSystemSimulation();

    printf("Turning off all machines.\n");

    smokeTestBottom();
}
