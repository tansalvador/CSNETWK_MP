package DeLaSalleUsap;

import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int nPort = 4000;

        System.out.print("Enter port number: ");
        nPort = reader.nextInt();

        ServerSocket socketOne;

        try {
            socketOne = new ServerSocket(nPort);

            while(true) {                                                       // Server continuously accepts clients
                System.out.println("[" + new Date() + "] Server: Listening on port: " + nPort);
                Socket clientSocket = socketOne.accept();                       // Represents the connection to a client
                System.out.println("[" + new Date() + "] Server: New Client connected: " + clientSocket.getRemoteSocketAddress());
                OutputStream clientOutput = clientSocket.getOutputStream();
                clientOutput.write("You are now connected to the server!\n".getBytes());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("[" + new Date() + "] Server: Connection terminated");
        }
    }
}
