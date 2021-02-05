package DeLaSalleUsap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Main extends Application {

    public Controller exit;

    public static ListView messageList;

    public void setMessageList(ListView messageList) {
        this.messageList = messageList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        exit = new Controller();
        Parent root = FXMLLoader.load(getClass().getResource("Chatbox.fxml"));
        primaryStage.setTitle("De La Salle Usap");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e ->   {
            e.consume();
            try {
                exit.exitChat(messageList);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            primaryStage.close();
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
