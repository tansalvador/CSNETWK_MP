package DeLaSalleUsap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Reader extends Thread {

    private Client masterClient;

    // Constructor of Connection class
    public Reader(Client masterClient) {
        this.masterClient = masterClient;
    }

    // Called when start() function is called from Server class
    @Override
    public void run() {
        try {
            DataInputStream masterStream = masterClient.getInputStream();

            while (true) {
                String message = masterStream.readUTF();
                System.out.println(message);
                if (message.equalsIgnoreCase("LOGOUT"))
                    break;
            }
        }
        catch (Exception e) {
            //System.out.println("Reader Error");
            //e.printStackTrace();
        }
    }

}
