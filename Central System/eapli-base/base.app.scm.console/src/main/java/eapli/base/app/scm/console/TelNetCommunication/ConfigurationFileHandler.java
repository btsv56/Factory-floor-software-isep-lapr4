package eapli.base.app.scm.console.TelNetCommunication;

import eapli.base.app.scm.console.BaseSCM;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConfigurationFileHandler implements Runnable {

    ServerSocket Soc;

    public ConfigurationFileHandler() {

    }

    @Override
    public void run() {
        try {
            try {
                Soc = new ServerSocket(23);
            } catch (IOException e) {
                System.out.println("(Telnet): Failed to open TelNet socket");
                e.printStackTrace();
                return;
            }
            System.out.println("(Telnet): TelNet Server on. Listening for TelNet requests.");
            while (true) {
                Socket CSoc = Soc.accept();
                new AcceptTelnetClient(CSoc);
            }
        } catch (Exception e) {
            System.out.println("(Telnet): Exception in TelNet server");
            e.printStackTrace();
        }
    }
}

class AcceptTelnetClient extends Thread {
    Socket ClientSocket;

    AcceptTelnetClient(Socket CSoc) {
        ClientSocket=CSoc;
        start();
    }

    public void run() {
        try {
            System.out.println("(Telnet): New TelNet connection from Backoffice.");
            DataInputStream din=new DataInputStream(ClientSocket.getInputStream());

            String in=din.readUTF();

            String[] line=in.split(";");
            String id=line[0];
            byte[] conf=line[1].getBytes();

            String ip=BaseSCM.getKnownIPs().get(id);
            if (ip==null) {
                System.out.println("(Telnet): Machine does not have an associated IP address. Canceling.");
                ClientSocket.close();
                return;
            }
            System.out.println("(Telnet): Sending configuration file to machine "+id+"...");
            try {
                System.setProperty("javax.net.ssl.keyStore", ".\\base.app.scm.console\\src\\main\\resources\\scm.jks");
                System.setProperty("javax.net.ssl.keyStorePassword", "ouroboros");
                System.setProperty("javax.net.ssl.trustStore", ".\\base.app.scm.console\\src\\main\\resources\\scm.jks");
                System.setProperty("javax.net.ssl.trustStorePassword", "ouroboros");

                SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
                SSLSocket sock = (SSLSocket) sf.createSocket(ip, 9999);
                sendConfiguration(Integer.parseInt(id), conf, sock);
                sock.close();
            } catch (IOException io) {
                System.out.println("(Telnet): Error opening TCP socket");
                io.printStackTrace();
                ClientSocket.close();
                return;
            }
            System.out.println("(Telnet): Sent configuration file, closing TCP connection.");
            ClientSocket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void sendConfiguration(int id, byte[] conf, SSLSocket s) throws IOException {
        DataOutputStream sOut = new DataOutputStream(s.getOutputStream());
        byte[] byteArray=new byte[6];
        byteArray[0]=1; byteArray[1]=2;
        byteArray[2]= (byte) (id%256);
        byteArray[3]= (byte) (id/256);
        byteArray[4]= (byte) (conf.length%256);
        byteArray[5]= (byte) (conf.length/256);
        sOut.write(byteArray);
        if (awaitAcknowledgement(s) >= 0) {
            System.out.println("(Telnet): ACK received, sending configuration...");
            sOut.write(conf);
        }
    }

    private int awaitAcknowledgement(SSLSocket s) throws IOException {
        DataInputStream sIn = new DataInputStream(s.getInputStream());
        byte msg;
        int length;
        msg = sIn.readByte();
        if (msg != (byte) 0) {
            return -1;
        }
        msg = sIn.readByte();
        if (msg == (byte) 150) {
            msg = sIn.readByte();
            if (msg != (byte) 0) {
                return -1;
            }
            msg = sIn.readByte();
            msg += sIn.readByte();
            length = msg;
        } else {
            sIn.skipBytes(4);
            return -1;
        }
        return length;
    }
}
