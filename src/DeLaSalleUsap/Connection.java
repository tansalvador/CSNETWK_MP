package DeLaSalleUsap;

import java.io.*;
import java.net.*;
import java.util.*;

public class Connection extends Thread {

    private String clientName;
    private Socket clientEndpoint;
    private Server master;

    private DataInputStream disReader;
    private DataOutputStream dosWriter;

    // Constructor of Connection class
    public Connection(Server masterServer, String clientName, Socket clientEndpoint) {
        this.clientName = clientName;
        this.clientEndpoint = clientEndpoint;
        this.master = masterServer;
    }

    public String getClientName() {
        return this.clientName;
    }

    // Method that can be used by other Connection classes to send message to
    // other Connection class
    public void receiveMessage(String message, String cName) throws IOException {
        if (!this.clientName.equals(cName))
            dosWriter.writeUTF(message);
    }

    // Called when start() function is called from Server class
    @Override
    public void run() {
        try {
            this.disReader = new DataInputStream(clientEndpoint.getInputStream());
            this.dosWriter = new DataOutputStream(clientEndpoint.getOutputStream());

            // Tell other Client classes that this Client class is online through their connections
            ArrayList<Connection> otherConnections = master.getAllConnections();
            for(Connection otherCon : otherConnections) {
                otherCon.receiveMessage(this.clientName + " is online", this.clientName);
            }

            //
            String message;
            while ((message = this.disReader.readUTF()) != null) {
                if (message.equalsIgnoreCase("LOGOUT")){
                    ArrayList<Connection> otherConnections1 = master.getAllConnections();
                    otherConnections1.remove(this);
                    for (Connection otherCon1 : otherConnections1) {
                        otherCon1.receiveMessage(this.clientName + " has logged out", this.clientName);
                    }
                    break;
                }
                else {
                    ArrayList<Connection> otherConnections1 = master.getAllConnections();
                    for (Connection otherCon1 : otherConnections1) {
                        otherCon1.receiveMessage(this.clientName + " says: " + message, this.clientName);
                    }
                }
            }

            clientEndpoint.close();
        }
        catch (Exception e) {
            //System.out.println("[" + new Date() + "] Server: Client " + this.clientName + "'s connection has been cut");
            //e.printStackTrace();
        } finally {
            System.out.println("[" + new Date() + "] Server: Client " + this.clientName + " has disconnected");
        }
    }

}
