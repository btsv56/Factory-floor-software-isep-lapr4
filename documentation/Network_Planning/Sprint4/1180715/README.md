# UDP Client

UDP client was developed to when running it, the first thing it does  is send a broadcast request to all the devices in the network, and then monitors the machines state by sending hello requests every 30 seconds. With the responses from the machines, it updates the list of machines being tracked

## Main UDP Client class

```java
public class MachineMonitoringSystem implements Runnable{

    private static Map<InetAddress, List<Integer>> machineIP = new HashMap<>();
    static DatagramSocket sock;

    public void run() {
        byte[] data = new byte[300];
        DatagramPacket udpPacket;

        try {
            sock = new DatagramSocket();
        } catch(IOException ex){
            System.out.println("Failed to open local port");
            System.exit(1);
        }

        System.out.println("UDP Client Running. Listening for Machine Requests. Press CTRL + C to exit\n\n\n");
        InetAddress broadcastAddr = null;
        try {
            broadcastAddr = InetAddress.getByName(Settings.BROADCAST);
        } catch (UnknownHostException e) {
            System.out.println("Host Address not found");
        }

        try {
            sock.setSoTimeout(Settings.TIMEOUT);
            sock.setBroadcast(true);
        } catch (SocketException e) {
            System.out.println("Error Preparing Socket");
        }


        try {
            data = prepareMessage(MessageType.HELLO, (short) Settings.NO_ID);
        } catch (IOException e) {
            System.out.println("Error Creating message");
        }

        udpPacket = new DatagramPacket(data, data.length, broadcastAddr, Settings.SERVICE_PORT);
        System.out.println("Sending HELLO message to broadcast address to find all machines\n\n");
        try {
            sock.send(udpPacket);
        } catch (IOException e) {
            System.out.println("Error Sending HELLO request");
        }

        Thread udpReceiver = new Thread(new MachineMonitoringSystemThread(sock));
        udpReceiver.start();

        new Timer().scheduleAtFixedRate(new DuplicatesTask(),0, 1000 * 60 );

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
                System.out.println("Thread couldnt sleep. There are monsters nearby");
            }
        }
    }

    /**
     * writes the necessary data in the data byte to be sent to the machines as an hello
     * request
     * @param msgType
     * @param id
     * @return
     * @throws IOException
     */
    private static byte[] prepareMessage(MessageType msgType, short id) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(20);
        DataOutputStream message = new DataOutputStream(os);
        byte[] msg;

        message.writeByte((byte) Settings.PROTOCOL_VERSION);            //versao
        message.writeByte((byte) msgType.getMsgCode().shortValue());    //codigo
        message.writeShort(id);                                         //id
        message.writeByte(0);
        message.writeByte(0);

        message.flush();

        msg = os.toByteArray();
        return msg;
    }

    /**
     * adds an ip, as well as the machine code and state of the machine
     * @param ip
     * @param machineCode
     */
    public static synchronized void addIP(InetAddress ip, List<Integer> machineCode){
        machineIP.putIfAbsent(ip, machineCode);
    }

    /**
     * updates the state of the machine
     * @param ip
     */
    public static synchronized void updateState(InetAddress ip){
        List<Integer> tmp = machineIP.get(ip);
        tmp.remove(1);
        tmp.add(0);
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

    public static synchronized void sendRESET(String machineIP) throws IOException {
        byte[] resetMSG;
        InetAddress address = InetAddress.getByName(machineIP);
        resetMSG = prepareMessage(MessageType.RESET, (short) Settings.NO_ID);
        DatagramPacket resetPacket = new DatagramPacket(resetMSG, resetMSG.length, address, Settings.SERVICE_PORT);
        sock.send(resetPacket);
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
        for(Map.Entry<InetAddress, List<Integer>> entry : machineIP.entrySet()){
            for(Map.Entry<InetAddress, List<Integer>> entry2 : machineIP.entrySet()){
                if(entry.getValue().get(0) == entry2.getValue().get(0)){
                    System.out.printf("Alert with Machine %s and machine %s \nThey are sharing the same Machine Code! %d\n", entry.getKey(), entry.getKey(), entry.getValue().get(0));
                }
            }
        }
        System.out.println("Verification Done.");
    }
}
```



## UDP Client thread class

``` java
class MachineMonitoringSystemThread implements Runnable{

    private DatagramSocket sock;
    private static int code;
    private static int id;

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
        if(code == MessageType.HELLO.getMsgCode()){
            List<Integer> temp = new ArrayList<>();
            temp.add(id);
            temp.add(1);
            System.out.println("\nThe request was accepted.\nUpdating monitored machines..");
            MachineMonitoringSystem.addIP(address, temp);
        } if(code == MessageType.NACK.getMsgCode()){
            System.out.println("The request was rejected");
        }
    }
}
```

## Timer Task

``` java
class DuplicatesTask extends TimerTask {

    @Override
    public void run() {
        MachineMonitoringSystem.checkDuplicates();
    }

}
```

## Testing

To test this use case, it is necessary to use different Virtual Machines or SSH session. Since we need to use the same port for the UDP broadcast to work, that port can only be bound to one process. More info about testing is provided in the design of the use case [here](https://bitbucket.org/pjoliveira/lei_isep_2019_20_sem4_2db_1180573_1180715_1180723_1180712/wiki/Engineer/1180715/MachineMonitoringSystem/MachineMonitoringSystem.md)
## Explanation

The main class sends a HELLO request to all machines in the network

``` java
		try {
            broadcastAddr = InetAddress.getByName(Settings.BROADCAST);
        } catch (UnknownHostException e) {
            System.out.println("Host Address not found");
        }

        try {
            sock.setSoTimeout(Settings.TIMEOUT);
            sock.setBroadcast(true);
        } catch (SocketException e) {
            System.out.println("Error Preparing Socket");
        }


        try {
            data = prepareMessage(MessageType.HELLO, (short) Settings.NO_ID);
        } catch (IOException e) {
            System.out.println("Error Creating message");
        }

        udpPacket = new DatagramPacket(data, data.length, broadcastAddr, Settings.SERVICE_PORT);
        System.out.println("Sending HELLO message to broadcast address to find all machines\n\n");
        try {
            sock.send(udpPacket);
        } catch (IOException e) {
            System.out.println("Error Sending HELLO request");
        }
```



After that the UPD Thread Class is started, working as a message receiver

``` java
        Thread udpReceiver = new Thread(new MachineMonitoringSystemThread(sock));
        udpReceiver.start();
```

This while loop is listening for messages received

``` java
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
```

The next methods are responsible for analysing the received message and checking the state of the machine received. in this case we are looking for ACK or NACK

``` JAVA
private static void checkState(InetAddress address){
        if(code == MessageType.HELLO.getMsgCode()){
            List<Integer> temp = new ArrayList<>();
            temp.add(id);
            temp.add(1);
            System.out.println("\nThe request was accepted.\nUpdating monitored machines..");
            MachineMonitoringSystem.addIP(address, temp);
        } if(code == MessageType.NACK.getMsgCode()){
            System.out.println("The request was rejected");
        }
    }


    private static void parseMessage(byte[] msg) throws IOException {

        ByteArrayInputStream is = new ByteArrayInputStream(msg);
        DataInputStream message = new DataInputStream(is);

        message.readByte();
        code = message.readUnsignedByte();
        id = message.readUnsignedShort();

        message.close();
        System.out.println("\nMessage Processed successfully!");
        System.out.println("Message Code: " + code + "\nMachine ID: " + id);
    }
```

Every 30 seconds a HELLO request is sent to the machines registered in the list 

``` java
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
        System.out.println("Thread couldnt sleep. There are monsters nearby");
    }
}
```

The following method is responsible to send HELLO request to the machines 

``` java
public static synchronized void sendToAll(DatagramSocket s, DatagramPacket p) throws Exception {
        for(InetAddress ip: machineIP.keySet()){
            p.setAddress(ip);
            s.send(p);
        }
    }
```

There is also the functionality of checking if two different IPs (aka machines) are using the same ID, this verification is done every 60 seconds

``` java
public static void checkDuplicates(){
        System.out.println("Verifying for duplicate machine IDs");
        for(Map.Entry<InetAddress, List<Integer>> entry : machineIP.entrySet()){
            for(Map.Entry<InetAddress, List<Integer>> entry2 : machineIP.entrySet()){
                if(entry.getValue().get(0) == entry2.getValue().get(0)){
                    System.out.printf("Alert with Machine %s and machine %s \nThey are sharing the same Machine Code! %d\n", entry.getKey(), entry.getKey(), entry.getValue().get(0));
                }
            }
        }
        System.out.println("Verification Done.");
    }
```