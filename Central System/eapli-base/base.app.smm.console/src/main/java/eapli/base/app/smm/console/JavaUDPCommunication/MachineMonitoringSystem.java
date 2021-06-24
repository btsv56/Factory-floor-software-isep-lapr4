package eapli.base.app.smm.console.JavaUDPCommunication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import eapli.base.app.smm.console.JavaUDPCommunication.utils.MessageType;
import eapli.base.app.smm.console.JavaUDPCommunication.utils.Settings;

public class MachineMonitoringSystem implements Runnable{

    private static final Map<InetAddress, List<Integer>> machineIP = new HashMap<>();
    static DatagramSocket sock;

    public void run() {
        byte[] data;
        DatagramPacket udpPacket;

        try {
            sock = new DatagramSocket();
        } catch(IOException ex){
            System.out.println("Failed to open local port");
            System.exit(1);
        }

        System.out.println("UDP Client Running. Listening for Machine Requests. Press CTRL + C to exit\n\n\n");

        InetAddress broadcastAddr = getBroadcastAddress();

        prepareSocket();

        data = prepareMessage(MessageType.HELLO, Settings.NO_ID);

        udpPacket = new DatagramPacket(data, data.length, broadcastAddr, Settings.SERVICE_PORT);

        System.out.println("Sending HELLO message to broadcast address to find all machines\n\n");
        sendPacket(udpPacket);

        //Starts a thread to receive the messages from machines
        Thread udpReceiver = new Thread(new MachineMonitoringSystemThread(sock));
        udpReceiver.start();

        new Timer().scheduleAtFixedRate(new DuplicatesTask(),0, Settings.TASK_TIMEOUT );

        while(true) {
            System.out.println("Active machines:");
            printIPs();
            udpPacket.setData(data);
            udpPacket.setLength(data.length);

            System.out.println("Sending HELLO request to every machine being monitored");
            try {
                sendToAll(sock, udpPacket);
            } catch (Exception e) {
                System.out.println("Error sending request");
            }
            try {
                Thread.sleep(Settings.TIMEOUT);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * receives the packet to be sent to the broadcast address as parameter and sends it
     * with the socket
     * @param udpPacket Packet to be sent
     */
    private static void sendPacket(DatagramPacket udpPacket){
        try {
            sock.send(udpPacket);
        } catch (IOException e) {
            System.out.println("Error Sending HELLO request");
        }
    }

    /**
     * Returns the Broadcast Address as an InetAddress
     * @return the broadcast address as InetAddress
     */
    private static InetAddress getBroadcastAddress(){
        try {
            return InetAddress.getByName(Settings.BROADCAST);
        } catch (UnknownHostException e) {
            System.out.println("Host Address not found");
        }
        return null;
    }

    /**
     * Sets the global socket to broadcast mode and the timeout defined in the settings
     */
    private static void prepareSocket(){
        try {
            sock.setSoTimeout(Settings.TIMEOUT);
            sock.setBroadcast(true);
        } catch (SocketException e) {
            System.out.println("Error Preparing Socket");
        }
    }

    /**
     * writes the necessary data in the data byte to be sent to the machines as an hello
     * request
     * @param msgType HELLO / RESET
     * @param id ID of the machine
     * @return byte array with the info passed as a parameter
     * @throws IOException
     */
    private static byte[] prepareMessage(MessageType msgType, short id){
        ByteArrayOutputStream os = new ByteArrayOutputStream(20);
        DataOutputStream message = new DataOutputStream(os);
        byte[] msg;

        try {
            message.writeByte((byte) Settings.PROTOCOL_VERSION);            //versao
            message.writeByte((byte) msgType.getMsgCode().shortValue());    //codigo
            message.writeShort(id);                                         //id
            message.writeByte(0);
            message.writeByte(0);

            message.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg = os.toByteArray();
        return msg;
    }

    /**
     * adds an ip, as well as the machine code and state of the machine
     * @param ip ip to be added to the list
     * @param machineCode code of the machine
     */
    public static synchronized void addIP(InetAddress ip, List<Integer> machineCode){
        machineIP.putIfAbsent(ip, machineCode);
    }

    /**
     * updates the state of the machine
     * @param ip
     */
    public static synchronized void updateState(InetAddress ip, MessageType state){
        List<Integer> tmp = machineIP.get(ip);
        tmp.remove(1);
        tmp.add(state.getMsgCode());
        machineIP.replace(ip,tmp);
    }

    /**
     * sends a packet to every machine being monitored
     * @param s
     * @param p
     * @throws Exception
     */
    public static synchronized void sendToAll(DatagramSocket s, DatagramPacket p) throws Exception {
        for(InetAddress ip: machineIP.keySet()){
            p.setAddress(ip);
            s.send(p);
        }
    }

    public static synchronized void sendRESET(String machineIP){
        byte[] resetMSG;
        InetAddress address = null;
        try {
            address = InetAddress.getByName(machineIP);
            resetMSG = prepareMessage(MessageType.RESET, Settings.NO_ID);
            DatagramPacket resetPacket = new DatagramPacket(resetMSG, resetMSG.length, address, Settings.SERVICE_PORT);
            sendPacket(resetPacket);
        } catch (UnknownHostException e) {
            System.out.println("Couldn't find the address");
            e.printStackTrace();
        }
    }

    public static synchronized void sendHELLO() {
        byte[] msg = prepareMessage(MessageType.HELLO,Settings.NO_ID);
        try {
            InetAddress address = InetAddress.getByName(Settings.BROADCAST);
            DatagramPacket helloPacket = new DatagramPacket(msg, msg.length, address, Settings.SERVICE_PORT);
            sendPacket(helloPacket);
        } catch (UnknownHostException e) {
            System.out.println("Couldn't find the address");
            e.printStackTrace();
        }
    }

    /**
     * Prints all machine ips, and the id and state of the machine associated to that ID
     */
    public static synchronized void printIPs() {
        for(Map.Entry<InetAddress, List<Integer>> entry: machineIP.entrySet()) {
            System.out.println("Machine IP: " + entry.getKey());
            System.out.println("Machine ID: " + entry.getValue().get(0));
            System.out.println("Machine State: " + entry.getValue().get(1));
        }
        System.out.println("");
    }

    public static void checkDuplicates(){
        System.out.println("Verifying for duplicate machine IDs");
        for(Map.Entry<InetAddress, List<Integer>> machine1 : machineIP.entrySet()){
            for (Map.Entry<InetAddress, List<Integer>> machine2 : machineIP.entrySet()) {
                if (machine1.getKey() != machine2.getKey()) {
                    if (machine1.getValue().get(0).equals(machine2.getValue().get(0))) {
                        System.out.printf("Alert with Machine %s and machine %s \nThey are sharing the same Machine Code! %d\n", machine1.getKey(), machine2.getKey(), machine1.getValue().get(0));
                    }
                }

            }
        }
        System.out.println("Verification Done.");
    }
}

class MachineMonitoringSystemThread implements Runnable{

    private DatagramSocket sock;
    private static int code;
    private static int id = 0;

    public MachineMonitoringSystemThread(DatagramSocket sock) {
        this.sock = sock;
    }

    /**
     * this method loops to receive the messages from the machines. when a message is
     * received it's content is validated and according to that content
     * the machine list is updated
     */
    @Override
    public void run() {
        byte[] data = new byte[300];
        DatagramPacket p;
        InetAddress currMachine;

        p = new DatagramPacket(data, data.length);

        while(true){
            boolean error = false;
            p.setLength(data.length);
            try{
                sock.receive(p);
            } catch(SocketTimeoutException stx){
                System.out.println("No Reply from Machine");
                error = true;
            } catch(IOException ex){
                return;
            }
            if(!error){
                currMachine = p.getAddress();

                System.out.println("\nReceived Message from: " + currMachine.getHostAddress());
                System.out.println("Processing message...");
                try {
                    parseMessage(p.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkState(currMachine);
            }
        }

    }

    /**
     * this method verifies if the machine accepted or rejected the request sent
     * @param address
     */
    private static void checkState(InetAddress address){
        if(code == MessageType.ACK.getMsgCode()){
            List<Integer> temp = new ArrayList<>();
            temp.add(id);
            temp.add(1);
            System.out.println("\nThe request was accepted.\nUpdating monitored machines..");
            MachineMonitoringSystem.addIP(address, temp);
            MachineMonitoringSystem.updateState(address, MessageType.ACK);
        } if(code == MessageType.NACK.getMsgCode()){
            System.out.println("The request was rejected");
            MachineMonitoringSystem.updateState(address, MessageType.NACK);
        }
    }

    /**
     * processes the message received by extracting the bytes refering to the code
     * of the message and the id of the machine
     * @param msg
     * @throws IOException
     */
    private static void parseMessage(byte[] msg) throws IOException {

        ByteArrayInputStream is = new ByteArrayInputStream(msg);
        DataInputStream message = new DataInputStream(is);

        message.readByte();
        code = message.readUnsignedByte();
        id = Short.reverseBytes((short) message.readUnsignedShort());

        message.close();
        System.out.println("\nMessage Processed successfully!");
        System.out.println("Message Code: " + code + "\nMachine ID: " + id);
    }
}

class DuplicatesTask extends TimerTask {

    @Override
    public void run() {
        MachineMonitoringSystem.checkDuplicates();
    }

}