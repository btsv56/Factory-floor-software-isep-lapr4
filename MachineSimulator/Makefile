SOURCES := $(shell find * -type f -name "*.c" -not -iname "*main.c" )
SMKMAIN := src/SmokeTesters/SmokeTesterMain.c
MAIN := src/Main/Main.c
PRJSOURCES := $(SOURCES) $(MAIN)
SMKSOURCES := $(SOURCES) $(SMKMAIN)
EXEMAIN := main
EXESMKTESTS := smoketests
EXEC := $(EXESMKTESTS) $(EXEMAIN)
FLAGS := -v -lpthread -fno-stack-protector -lssl -lcrypto
SERVER := $(SOURCES) ServerUDPmain.c

all: build runSmokeTests

run: build
	@echo ''
	@echo '..........................::::: RUNNING MACHINE SIMULATIOR :::::..........................'
	@echo ''
	./$(EXEMAIN)

runSmokeTests: buildSmokeTests
	@echo ''
	@echo '..........................::::: RUNNING SMOKE TESTS :::::..........................'
	@echo ''
	./$(EXESMKTESTS)

build: $(PRJSOURCES)
	@echo ''
	@echo '..........................::::: BUILDING MACHINE SIMULATIOR :::::..........................'
	@echo ''
	gcc $^ -o $(EXEMAIN) $(FLAGS)

buildSmokeTests: $(SMKSOURCES) 
	@echo ''
	@echo '..........................::::: BUILDING SMOKE TESTS :::::..........................'
	@echo ''
	gcc $^ -o $(EXESMKTESTS) $(FLAGS)
	
serverudp: $(SERVER)
	gcc $^ -o server $(FLAGS)
	./server

clean :
	rm -f $(EXEC)