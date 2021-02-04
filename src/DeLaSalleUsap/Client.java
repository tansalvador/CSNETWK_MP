package DeLaSalleUsap;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

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

            // Connecting to the Server class
            Socket clientEndpoint = new Socket(strServerAddress, nPort);

            DataInputStream disReader = new DataInputStream(clientEndpoint.getInputStream());
            DataOutputStream dosWriter = new DataOutputStream(clientEndpoint.getOutputStream());

            // Sending strName (Client name) to Server for log printing
            dosWriter.writeUTF(strName);

            // Confirmation of Server class for successful connection
            String message1 = disReader.readUTF();
            System.out.println(message1);

            // Client class can continuously put messages on its DataOutputStream until "logout" is typed
            // If the Client class types "logout", it will close the connection from Server class

            while (true) {
                String conMessages = disReader.readUTF();
                System.out.println(conMessages);
                if(conMessages == "STOP")
                    break;
            }

            // Send the terminal String to the Server
            dosWriter.writeUTF("END");

            System.out.println("[" + new Date() + "] Goodbye " + strName + ", you have terminated your connection");

            clientEndpoint.close();
        } catch (Exception e) {
            System.out.println("Client Error");
            e.printStackTrace();
        }
    }

}
