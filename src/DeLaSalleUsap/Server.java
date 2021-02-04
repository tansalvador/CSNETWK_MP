package DeLaSalleUsap;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server {

    private ArrayList<Connection> clientConnections = new ArrayList<>();
    private int nPort;


    // Constructor for Server class
    public Server(int port) {
        this.nPort = port;
    }

    public ArrayList<Connection> getAllConnections() {
        return this.clientConnections;
    }

    public static void main(String[] args) {

        int nPort = 8888;
        ServerSocket serverSocket;
        Socket serverEndpoint;
        String strName;

        Server masterServer = new Server(nPort);

        try {
            serverSocket = new ServerSocket(nPort);

            while (true) {
                // Server waits for a Client class to connect
                System.out.println("[" + new Date() + "] Server: Listening to port " + nPort);

                // Accept a connecting Client class
                serverEndpoint = serverSocket.accept();

                // For log printing during Client class connection
                DataInputStream disReader = new DataInputStream(serverEndpoint.getInputStream());
                DataOutputStream dosWriter = new DataOutputStream(serverEndpoint.getOutputStream());

                // Read the name sent by the Client class
                strName = disReader.readUTF();
                System.out.println("[" + new Date() + "] Server: New client " + strName + " connected at: " + serverEndpoint.getRemoteSocketAddress() + "\n");

                // Send a message to the Client class about successful connection
                String message1 = "[" + new Date() + "] Welcome " + strName
                        + "! You have connected to the server at " + serverEndpoint.getRemoteSocketAddress() + ": " + nPort;
                dosWriter.writeUTF(message1);

                // Make the thread for a Client class using Connection class
                // Add the Connection class to the list of Client Connections
                // Start the thread
                Connection client = new Connection(masterServer, strName, serverEndpoint);
                masterServer.clientConnections.add(client);
                System.out.println("Current Clients:");
                for(Connection one : masterServer.clientConnections) {
                    System.out.println(one);
                }
                client.start();
            }
        } catch (Exception e) {
            System.out.println("Server Error");
            e.printStackTrace();
        }
    }

}
