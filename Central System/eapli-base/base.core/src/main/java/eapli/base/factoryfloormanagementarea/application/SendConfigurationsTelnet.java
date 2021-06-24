/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.application;

import eapli.base.factoryfloormanagementarea.domain.ConfigurationFile;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
 *
 * @author Utilizador
 */
public class SendConfigurationsTelnet {

    public boolean sendConfigurationFile(Machine machine,ConfigurationFile configFile) throws IOException {
        try (Socket soc = new Socket("localhost", 23)) {
            String message;
            DataInputStream din = new DataInputStream(soc.getInputStream());
            DataOutputStream dout = new DataOutputStream(soc.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            message = String.format("%s;%s", machine.identity(),Arrays.toString(configFile.getFile()));
            dout.writeUTF(message);
            return true;
        } catch (ConnectException ce) {
            System.out.println("Unable to connect to SCM.");
        }
        return false;
    }
}
