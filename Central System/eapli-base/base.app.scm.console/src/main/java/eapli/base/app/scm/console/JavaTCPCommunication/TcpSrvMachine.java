package eapli.base.app.scm.console.JavaTCPCommunication;

import eapli.base.app.scm.console.BaseSCM;
import eapli.base.app.scm.console.errorlogging.ErrorLoggerToFile;
import eapli.base.app.scm.console.messagelogging.MessageLoggerToFile;
import eapli.base.errornotificationmanagement.application.CreateErrorNotificationController;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.domain.Protocol;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.messagesmanagement.application.CreateMessageService;

import java.io.*;
import java.net.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.net.ssl.*;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;

public class TcpSrvMachine implements Runnable {

    static SSLServerSocket sock;
    static boolean on;
    static ErrorLoggerToFile logger;

    static {
        try {
            logger = new ErrorLoggerToFile("TCPSRV.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TcpSrvMachine() {
    }

    public void run() {
        SSLSocket cliSock;
        on = true;
        try {
            System.setProperty("javax.net.ssl.keyStore", ".\\base.app.scm.console\\src\\main\\resources\\scm.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "ouroboros");
            System.setProperty("javax.net.ssl.trustStore", ".\\base.app.scm.console\\src\\main\\resources\\scm.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "ouroboros");

            try {
                SSLServerSocketFactory sf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                sock = (SSLServerSocket) sf.createServerSocket(9999);
                sock.setNeedClientAuth(true);
            } catch (IOException ex) {
                logger.errorLog("(TCP): Failed to open server socket");
                ex.printStackTrace();
                return;
            }
            System.out.println("(TCP): SCM Server on. Listening for TCP requests (IPv6/IPv4).");
            while (on) {
                cliSock = (SSLSocket) sock.accept();
                new Thread(new TcpSrvMachineThread(cliSock)).start();
            }
        } catch (IOException e) {
            logger.errorLog("(TCP): Error in TCP server");
            e.printStackTrace();
        }
    }
}

class TcpSrvMachineThread implements Runnable {

    private SSLSocket s;
    private DataOutputStream sOut;
    private DataInputStream sIn;
    final MachineRepository repositoryMachine = PersistenceContext.repositories().machine();
    static ErrorLoggerToFile logger;

    static {
        try {
            logger = new ErrorLoggerToFile("TCPSRVTHREAD.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TcpSrvMachineThread(SSLSocket cli_s) { s=cli_s; }
    public void run() {

        MessageLoggerToFile msgLogger;

        try {
            msgLogger = new MessageLoggerToFile("MESSAGELOG.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        InetAddress clientIP;
        clientIP = s.getInetAddress();
        boolean validated = true;
        try {
            System.out.println("(TCP): New client connection from " + clientIP.getHostAddress() + ", port number " + s.getPort());
            sOut = new DataOutputStream(s.getOutputStream());
            sIn = new DataInputStream(s.getInputStream());

            int id = parseHelloMessage();
            if (id==-1) {
                System.out.println("(TCP): Invalid 'hello' message.");
                closeSocket(clientIP);
            }
            System.out.println("\n(TCP): Received 'hello' message. Validating machine...");

            Machine machine;
            String machId="";
            try {
                machine = repositoryMachine.getMachine(new Protocol(id));
                machId = machine.identity().toString();
                System.out.println("(TCP): Machine ID: " + machId);
            } catch (PersistenceException pe) {
                validated = false;
            }

            if (validated) System.out.print("(TCP): Machine validated. Sending response...");
            else {
                logger.errorLog("(TCP): Invalid machine.");
                System.out.print("Sending response..."); }
            sendResponse(validated);
            System.out.println(" Sent response.");
            if (!validated) {
                closeSocket(clientIP);
                return;
            }

            if (!BaseSCM.getKnownIPs().containsKey(machId) || !BaseSCM.getKnownIPs().get(machId).equals(clientIP.getHostAddress())) {
                BaseSCM.addIPtoMap(machId, clientIP.getHostAddress());
                System.out.println("(TCP): New entry in known IPs (ID: " + id + " IPv4: " + BaseSCM.getKnownIPs().get(machId) + ")");
            }

            System.out.print("(TCP): Sending configuration...");
            sendConfiguration(id);
            System.out.println(" Sent configuration.");

            System.out.print("(TCP): Waiting for acknowledgement...");
            int length=awaitAcknowledgement();
            if (length<=0) {
                logger.errorLog("Error in acknowledgement.");
                closeSocket(clientIP);
                return;
            }

            System.out.println("(TCP): Receiving messages...");
            ArrayList<String> messages=receiveMessages(clientIP);
            int counter=0;
            if (messages.isEmpty()) { System.out.println(); logger.errorLog("\nError receiving message."); }
            else {
                System.out.println("(TCP): Received all messages from "+ machId +".");
                msgLogger.writeId(machId);
                for (String message : messages) {
                    msgLogger.messageLog(message);
                }
                msgLogger.closeFileReader();
            }
            CreateMessageService cms = new CreateMessageService();
            for (String message : messages) {
                if (createMessage(message, cms)) counter++;
            }
            System.out.println("(TCP): "+counter+" valid messages were detected and parsed.");
            closeSocket(clientIP);
            Thread.currentThread().join();
        } catch(IOException ex) {
            logger.errorLog("(TCP): IOException in TCP server");
            ex.printStackTrace();
            try {
                closeSocket(clientIP);
                Thread.currentThread().join();
            } catch (IOException | InterruptedException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.errorLog("(TCP): Exception in TCP server");
            try {
                closeSocket(clientIP);
                Thread.currentThread().join();
            } catch (IOException | InterruptedException ignored) {
            }
        }
    }

    private int parseHelloMessage() throws IOException {
        byte msg;
        int id, i;
        for (i = 0; i < 1; i++) {
            msg = sIn.readByte();
            if (msg != (byte) 0) {
                return -1;
            }
        }
        msg = sIn.readByte();
        if (msg != (byte) 1) {
            return -1;
        }
        id = sIn.read();
        id += 256 * sIn.read();
        for (i = 0; i < 2; i++) {
            msg = sIn.readByte();
            if (msg != (byte) 0) {
                return -1;
            }
        }
        return id;
    }

    private void sendResponse(boolean validated) throws IOException {
        byte[] byteArray=new byte[2];
        byteArray[0]=(byte) 1;
        if (validated) {
            byteArray[1]=(byte) 150;
        } else {
            byteArray[1]=(byte) 151;
        }
        sOut.write(byteArray);
    }

    private void sendConfiguration(byte[] conf) throws IOException {
        sOut.write(conf);
    }

    private void sendConfiguration(int id) throws IOException {
        byte[] byteArray=new byte[4];
        byteArray[0]=1; byteArray[1]=2;
        byteArray[2]= (byte) (id%256);
        byteArray[3]= (byte) (id/256);
        sOut.write(byteArray);
    }

    private int awaitAcknowledgement() throws IOException {
        byte msg;
        int length;
        msg = sIn.readByte();
        if (msg != (byte) 0) {
            return -1;
        }
        msg = sIn.readByte();
        if (msg == (byte) 150) {
            System.out.println(" ACK received.");
            msg = sIn.readByte();
            if (msg != (byte) 0) {
                return -1;
            }
            msg = sIn.readByte();
            msg += sIn.readByte();
            length = msg;
        } else {
            System.out.println(" NACK received.");
            return -1;
        }
        return length;
    }

    private ArrayList<String> receiveMessages(InetAddress clientIP) throws IOException {
        byte msg;
        ArrayList<String> messages = new ArrayList<>();
        StringBuilder message;
        String mode = "";
        String idToCheck;
        int idHello;
        byte skip=99;
        sIn.skipBytes(96);
        sendResponse(true);
        do {
            sIn.skipBytes(99-skip);
            idHello=-1;
            message=new StringBuilder();
            skip=0;
            for (int i = 0; i < 100; i++) {
                msg = sIn.readByte();
                if (msg==(byte) 10) break;
                if (msg==(byte) 1) {
                    idHello=checkIfReset();
                    if (idHello>=0) {
                        System.out.println("Received 'hello' message (Machine "+idHello+" has been reset)");
                        skip = 4;
                        break;
                    }
                }
                message.append((char) msg);
                skip++;
            }
            if (idHello>=0) idToCheck=String.valueOf(idHello);
            else idToCheck=message.toString().split(";")[0];
            String ip=BaseSCM.getKnownIPs().get(idToCheck);
            if (ip==null || !ip.equals(clientIP.getHostAddress())) {
                logger.errorLog("IP address doesn't match ID.");
                sendResponse(false);
                continue;
            } else {
                sendResponse(true);
            }
            if (message.length()>0) {
                messages.add(message.toString());
                mode=message.toString().split(";")[1];
            }
        } while (mode.compareTo("S9")!=0);
        return messages;
    }

    private int checkIfReset() throws IOException {
        byte msg;
        int id=sIn.read();
        id+=sIn.read()*256;
        for (int i=0;i<2;i++) {
            msg = sIn.readByte();
            if (msg != 0) return -1;
        }
        return id;
    }

    private void closeSocket(InetAddress clientIP) throws IOException {
        s.close();
        System.out.println("(TCP): Client " + clientIP.getHostAddress() + ", port number: " + s.getPort() + " disconnected\n");
    }

    private boolean createMessage(String message, CreateMessageService cms) {
        List<String> attributes;
        String[] attributeSplit = message.split(";");
        String format = attributeSplit[1];
        attributes = Arrays.asList(attributeSplit);
        try {
            cms.createMessage(format, attributes);
            return true;
        } catch (IllegalStateException ex) {
            new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), ex.getMessage(), attributes.get(1).trim());
        } catch (ArrayIndexOutOfBoundsException ex2) {
            System.out.println("Wrong format");
        } catch (NoResultException ex3) {
            new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), "Lot doesn't exist", attributes.get(1).trim());
        } catch (DateTimeParseException | IllegalArgumentException ex4) {
            new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), "Wrong date hour", attributes.get(1).trim());
        } catch (Exception ex5) {
            new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), "Wrong format", attributes.get(1).trim());
        }
        return false;
    }
}
