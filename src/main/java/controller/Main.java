package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Main osztály.
 */
public class Main extends Application {

    /**
     * Az alkalmazás indulásakor intított metódul.
     * Megjeleníti a fő nézetet.
     * @param primaryStage az alkalmazás nézete.
     * @throws Exception ha valami hiba történik
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));

        primaryStage.setTitle("ToDoList");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 850, 530));
        primaryStage.show();

    }

    /**
     * A program main metóíusa.
     * @param args beérkező paraméterek
     */
    public static void main(String[] args) {
        launch(args);
    }

}