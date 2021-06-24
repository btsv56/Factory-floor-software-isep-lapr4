all:TCPCommunicationSmokeTester.o ../Application/TCPCommunicationService.o ../../Protocol/Application/CommunicationProtocolService.o
	gcc ../Application/TCPCommunicationService.o TCPCommunicationSmokeTester.o ../../Protocol/Application/CommunicationProtocolService.o -o test -lpthread -lssl -lcrypto

TCPCommunicationSmokeTester.o:TCPCommunicationSmokeTester.c
	gcc -g -Wall -c TCPCommunicationSmokeTester.c -o TCPCommunicationSmokeTester.o -lpthread -lssl -lcrypto

../Application/TCPCommunicationService.o: ../Application/TCPCommunicationService.c
	gcc -g -Wall -c ../Application/TCPCommunicationService.c -o ../Application/TCPCommunicationService.o -lpthread -lssl -lcrypto
	
../../Protocol/Application/CommunicationProtocolService.o: ../../Protocol/Application/CommunicationProtocolService.c
	gcc -g -Wall -c ../../Protocol/Application/CommunicationProtocolService.c -o ../../Protocol/Application/CommunicationProtocolService.o -lpthread -lssl -lcrypto
	
run: all
	./test
	
clean:
	rm ../Application/*.o
	rm *.o test
	
TCPConfigFilesCommunicationSmokeTester.o: TCPConfigFilesCommunicationSmokeTester.c
	gcc -g -Wall -c TCPConfigFilesCommunicationSmokeTester.c -o TCPConfigFilesCommunicationSmokeTester.o -lpthread
	
configFiles:TCPConfigFilesCommunicationSmokeTester.o ../Application/TCPCommunicationService.o ../../Protocol/Application/CommunicationProtocolService.o
	gcc ../Application/TCPCommunicationService.o TCPConfigFilesCommunicationSmokeTester.o ../../Protocol/Application/CommunicationProtocolService.o -o test -lpthread -lssl -lcrypto