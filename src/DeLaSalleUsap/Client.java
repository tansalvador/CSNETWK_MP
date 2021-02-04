package DeLaSalleUsap;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread {

    private String strServerAddress;
    private String strName;
    private int nPort;
    private Socket clientEndpoint;
    private DataInputStream disReader;
    private DataOutputStream dosWriter;

    private BufferedReader buffedReader;

    public Client(String strAdd, String strName, int nPort) {
        this.strServerAddress = strAdd;
        this.strName = strName;
        this.nPort = nPort;
    }

    public DataInputStream getInputStream() {
        return this.disReader;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String strServerAddress = "localhost";
        String strName = "guest";
        int nPort = 4000;
        String strMessage;

        try {
            // Getting required information before connecting to Server class
            System.out.print("Enter IP address: ");
            strServerAddress = sc.nextLine();
            System.out.print("Enter port number: ");
            nPort = Integer.parseInt(sc.nextLine());
            System.out.print("Enter your username: ");
            strName = sc.nextLine();

            Client slaveClient = new Client(strServerAddress, strName, nPort);

            // Connecting to the Server class
            slaveClient.clientEndpoint = new Socket(strServerAddress, nPort);

            slaveClient.disReader = new DataInputStream(slaveClient.clientEndpoint.getInputStream());
            slaveClient.dosWriter = new DataOutputStream(slaveClient.clientEndpoint.getOutputStream());
            slaveClient.buffedReader = new BufferedReader(new InputStreamReader(slaveClient.disReader));

            // Sending strName (Client name) to Server for log printing
            slaveClient.dosWriter.writeUTF(strName);

            // Confirmation of Server class for successful connection
            String message1 = slaveClient.disReader.readUTF();
            System.out.println(message1);

            // Handing off the input stream handling to threads
            Reader slaveReader = new Reader(slaveClient);
            System.out.println("Enabling notifications and messages for " + strName);
            slaveReader.start();

            Scanner scan = new Scanner(System.in);
            String slaveInput;
            while (!(slaveInput = scan.nextLine()).equalsIgnoreCase("LOGOUT")) {
                slaveClient.dosWriter.writeUTF(slaveInput);
            }

            // When logging off or cutting connection
            slaveClient.dosWriter.writeUTF("LOGOUT");
            System.out.println("[" + new Date() + "] Goodbye " + strName + ", you have terminated your connection");
            slaveClient.clientEndpoint.close();

        } catch (Exception e) {
            System.out.println("Client Error");
            e.printStackTrace();
        }
    }

}
