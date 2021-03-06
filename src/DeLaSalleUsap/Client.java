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
    private Controller slaveController;

    private BufferedReader buffedReader;

    // Constructor of Client class
    public Client(String strAdd, String strName, int nPort, Controller mySlave) {
        this.strServerAddress = strAdd;
        this.strName = strName;
        this.nPort = nPort;

        this.slaveController = mySlave;
    }

    // Getter of input stream
    public DataInputStream getInputStream() {
        return this.disReader;
    }

    // Connecting to the Server class
    public String connect() {
        try {
            // Connect to the Server class
            this.clientEndpoint = new Socket(this.strServerAddress, this.nPort);

            // Instantiate this Client class' input and output stream
            this.disReader = new DataInputStream(this.clientEndpoint.getInputStream());
            this.dosWriter = new DataOutputStream(this.clientEndpoint.getOutputStream());
            this.buffedReader = new BufferedReader(new InputStreamReader(this.disReader));

            // Sending strName (Client name) to Server for log printing
            this.dosWriter.writeUTF(strName);

            // Confirmation of Server class for successful connection
            String message1 = this.disReader.readUTF();
            System.out.println(message1);
            return message1;
        } catch (Exception e) {
            System.out.println(e);
            return "Client Error\n" + e;
        }
    }

    // Lets the Reader class update the Listview (message list) in Controller class
    public void readerResults(String notif){
        this.slaveController.reflectNotifs("[" + new Date() + "] " + notif);
    }

    // Handing off the input stream handling to threads using Reader class
    public String notifsON() {
        Reader slaveReader = new Reader(this);
        slaveReader.start();

        return "Enabling notifications and messages for " + strName;
    }

    // Putting input in GUI to the Client class' output stream for Connection class to read
    public void readInput(String slaveInput) {
        try {
            Scanner scan = new Scanner(System.in);
            String fileDir;
                /*if(slaveInput.equalsIgnoreCase("FILE TRANSFER")) {
                    System.out.print("Enter the directory of the file you want to send: ");
                    fileDir = scan.nextLine();
                } FOR FILE TRANSFER, NOT YET COMPLETE*/

            this.dosWriter.writeUTF(slaveInput);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendFiles(String PATH) {
        try {
            File fFile = new File(PATH);

            FileInputStream inputFile = new FileInputStream("outside.txt");
            byte[] fileContent = new byte[(int) fFile.length()];
            inputFile.read(fileContent, 0, fileContent.length);
            this.dosWriter.writeUTF("FILE IN");
            this.dosWriter.write(fileContent, 0, fileContent.length);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // When logging off or cutting connection
    public String logOut() {
        try {
            this.dosWriter.writeUTF("LOGOUT");
            this.clientEndpoint.close();

            return "[" + new Date() + "] Goodbye " + this.strName + ", you have terminated your connection";
        } catch (Exception e) {
            System.out.println(e);
            return "Client Error\n" + e;
        }
    }

}
