package DeLaSalleUsap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;


public class Controller extends Main{

    // GUI
    public Button bConnect, bLogout, bSend, bExit1, bExit2;
    public TextField username, address, nPort, message;
    public ListView messageList;
    private static boolean answer1, answer2;

    // model
    private Client client;


    public void connectClient(){
        if(username.getText().equals("") || address.getText().equals("") || nPort.getText().equals("")){
            message.setDisable(true);
        }
        else {
            message.setDisable(false);
            bConnect.setDisable(true);
            bLogout.setDisable(false);

            // Connect to Server class and get confirmation for successful connection
            client = new Client(address.getText(), username.getText(), Integer.parseInt(nPort.getText()), this);
            String connStatus = client.connect();
            messageList.getItems().add(connStatus);

            // Hand off the input stream to Reader class
            String readerStatus = client.notifsON();
            messageList.getItems().add("[" + new Date() + "] " + readerStatus);
        }
    }

    public void reflectNotifs(String newNotif) {
        messageList.getItems().add(newNotif);
        super.setMessageList(this.messageList);
    }

    /*
        Add client's message from the input box to the screen
        Reset the input box
     */
    public void addMessage(){
        messageList.getItems().add("[" + new Date() + "] You: " + message.getText());
        client.readInput(message.getText());
        message.clear();
        super.setMessageList(this.messageList);
    }

    public void openFiles(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
    }

    public void saveLogs(ListView messageList) throws Exception{

        FileWriter writer = new FileWriter("chatlog.txt");
        for(Object content: messageList.getItems()) {
            writer.write(content + System.lineSeparator());
        }
        writer.close();
    }

    public void clearFields() throws Exception {
        Scene exit = bExit1.getScene();
        Window w = exit.getWindow();
        Stage window = (Stage) w;

        answer2 = false;

        window.close();
    }

    public void saveChat(){
        Scene exit = bExit1.getScene();
        Window w = exit.getWindow();
        Stage window = (Stage) w;

        answer2 = true;

        window.close();
    }

    /*
        When a user tries to log out
        Yes = save logs and clear fields
        No = clear fields
     */
    public void logOut() throws Exception{
        Stage wOut = new Stage();
        wOut.setTitle("Exit Chat");
        wOut.setResizable(false);
        wOut.initModality(Modality.APPLICATION_MODAL);
        try
        {
            Parent back = FXMLLoader.load(getClass().getResource("Logout.fxml"));
            Scene s1 = new Scene(back, 300,200);
            wOut.setScene(s1);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        wOut.showAndWait();
        String logOutMsg = client.logOut();
        if(answer2 == true){
            this.messageList.getItems().add(logOutMsg);
            saveLogs(this.messageList);
        }
        else{
            messageList.getItems().clear();
        }

        username.clear();
        address.clear();
        nPort.clear();
        message.clear();
        message.setDisable(true);
        bConnect.setDisable(false);
        bLogout.setDisable(false);
    }

    public void exitChat(ListView messageList) throws Exception{

        Stage wExit = new Stage();
        wExit.setTitle("Exit Chat");
        wExit.setResizable(false);
        wExit.initModality(Modality.APPLICATION_MODAL);
        try
        {
            Parent exit = FXMLLoader.load(getClass().getResource("Exit.fxml"));
            Scene s1 = new Scene(exit, 300,200);
            wExit.setScene(s1);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        wExit.showAndWait();
        if(answer1 == true){
            saveLogs(messageList);
        }
    }

    public void saveAndExit() throws Exception {
        answer1 = true;
        Scene exit = bExit2.getScene();
        Window w = exit.getWindow();
        Stage window = (Stage)w;

        window.close();
    }

    public void exitProgram(){
        Scene exit = bExit2.getScene();
        Window w = exit.getWindow();
        Stage window = (Stage)w;

        answer1 = false;

        window.close();
    }

    // For Sending files... NOT WORKING
    public void openFileExplorer(){
        try {
            System.out.println("THIS FEATURE IS NOT WORKING");
            FileDialog fd = new FileDialog(new JFrame());
            fd.setVisible(true);
            File[] f = fd.getFiles();
            if(f.length > 0){

                String path = fd.getFiles()[0].getAbsolutePath().replaceAll("\\\\", "/");
                System.out.println("file:/" + path);
                client.sendFiles("file:/" + path);
            }
        } catch (Exception e)
        {   System.out.println("Controller Error");
            System.out.println(e);
        }

    }
}
