#include "src/CommunicationUDP/Application/UDPCommunicationService.h"
#include "src/Simulation/Domain/Machine.h"

void main(){
	
	Machine *mach ;
	
	mach = createMachine(100, 2, "3", 2);
	mach->status = 151;
	
	establishConnectionToTheMonitoringSystem(mach);
}
	
	