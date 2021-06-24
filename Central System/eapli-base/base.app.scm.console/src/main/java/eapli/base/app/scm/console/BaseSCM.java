/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.scm.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import eapli.base.app.scm.console.JavaTCPCommunication.TcpSrvMachine;
import eapli.base.app.scm.console.TelNetCommunication.ConfigurationFileHandler;
import eapli.base.app.scm.console.presentation.ImportMessageFiles;
import eapli.base.app.scm.console.presentation.ImportMessageFilesAction;

/**
 *
 * @author Utilizador
 */
@SuppressWarnings("squid:S106")
public class BaseSCM {

    private static Map<String, String> knownIPs;

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private BaseSCM() {

    }

    public static void main(final String[] args) throws InterruptedException {
        knownIPs=new HashMap<>();
        System.out.println("=====================================");
        System.out.println("SCM App");
        System.out.println("(C) 2020 - 2070");
        System.out.println("=====================================");

        String frase;
        Thread scmServer=new Thread(new TcpSrvMachine());
        Thread importMessages=new Thread(new ImportMessageFiles());
        Thread configurations=new Thread(new ConfigurationFileHandler());

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("At any time, you can enter 'x' to leave.");
        System.out.println("Press Enter to start the server and start importing messages");

        try {
            in.readLine();
            scmServer.start();
            importMessages.start();
            configurations.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                frase = in.readLine();
                if (frase.equalsIgnoreCase("x"))
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Exiting SCM...");
        scmServer.interrupt();
        importMessages.interrupt();
        configurations.interrupt();
        scmServer.join(100);
        importMessages.join(100);
        configurations.join(100);
        System.exit(0);
    }

    public static synchronized void addIPtoMap(String id, String ip) {
        knownIPs.put(id, ip);
    }

    public static synchronized Map<String, String> getKnownIPs() {
        return knownIPs;
    }

}
