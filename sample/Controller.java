package sample;

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


public class Controller{

    public Button bConnect, bLogout, bSend, bExit1, bExit2;
    public TextField username, address, nPort, message;
    public ListView messageList;
    private static boolean answer1, answer2;

    public void connectClient(){

    }

    /*
        Add client's message from the input box to the screen
        Reset the input box
     */
    public void addMessage(){
        messageList.getItems().add(message.getText());
        message.clear();
    }

    public void clearFields() throws Exception {
        Scene exit = bExit1.getScene();
        Window w = exit.getWindow();
        Stage window = (Stage) w;

        answer2 = false;

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

        if(answer2 == true){
            // save logs
        }

        username.clear();
        address.clear();
        nPort.clear();
        message.clear();
        messageList.getItems().clear();
    }

    public boolean exitChat(){
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

        return answer1;
    }

    public void exitProgram(){
        Scene exit = bExit2.getScene();
        Window w = exit.getWindow();
        Stage window = (Stage)w;

        answer1 = true;

        window.close();
    }

}
