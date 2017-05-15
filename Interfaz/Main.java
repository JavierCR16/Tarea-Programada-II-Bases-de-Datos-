package Interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("VentanaLogin.fxml"));
        primaryStage.setTitle("Servicio De Negociación");
        primaryStage.setScene(new Scene(root, 1025, 476));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
