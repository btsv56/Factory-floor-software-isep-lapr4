package eapli.base.app.smm.console;

import eapli.base.app.smm.console.JavaUDPCommunication.MachineMonitoringSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("squid:S106")
public class BaseSMM {

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private BaseSMM() {
    }

    public static void main(final String[] args) throws InterruptedException {
        System.out.println("=====================================");
        System.out.println("SMM App");
        System.out.println("(C) 2020 - 2070");
        System.out.println("=====================================");
        String frase;
        Thread monitoringSystem = new Thread(new MachineMonitoringSystem());

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("At any time, you can:\nPress 'r' to send a RESET request\nPress 'h' to send a new HELLO request to the machines\nPress 'x' to leave");
        System.out.println("Press a key to start the server");

        try {
            in.readLine();
            monitoringSystem.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                frase = in.readLine();
                if (frase.equalsIgnoreCase("h")){
                    System.out.println("Sending HELLO request to every machine in the network");
                    MachineMonitoringSystem.sendHELLO();
                }
                if (frase.equalsIgnoreCase("r")) {
                    System.out.println("Enter machine IP Address");
                    frase = in.readLine();
                    MachineMonitoringSystem.sendRESET(frase);
                }
                if (frase.equalsIgnoreCase("x"))
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Exiting SMM...");
        monitoringSystem.interrupt();
        monitoringSystem.join(100);
        System.exit(0);
    }

}
