package DeLaSalleUsap;

import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String strName = "default";
        String strAddress = "localhost";
        int nPort = 4000;

        System.out.print("Enter your name: ");
        strName = reader.nextLine();
        System.out.print("Enter the address: ");
        strAddress = reader.nextLine();
        System.out.print("Enter the port number: ");
        nPort = reader.nextInt();

        try {
            Socket clientSocket = new Socket(strAddress, nPort);
            System.out.println(strName + ": Connecting to server at " + clientSocket.getRemoteSocketAddress() + "\n");


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println(strName + ": Connection terminated");
        }
    }
}
