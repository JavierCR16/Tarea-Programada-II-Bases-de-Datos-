package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Javier on 5/15/2017.
 */
public class ControladorVentanaCambiarContrasenna implements Initializable {

    @FXML
    Label verificacion1;

    @FXML
    Label verificacion2;

    @FXML
    PasswordField cuadroContrasennaAnterior;

    @FXML
    PasswordField cuadroContrasennaNueva;

    @FXML
    PasswordField cuadroRepetirContrasenna;

    @FXML
    Button botonCambiarContrasenna;

    String contrasennaAnteriorUsuario;  //Esto para que el mae solo pueda cambiar la contraseña de el y no la de otros.


    public void initialize(URL fxmlLocations, ResourceBundle resources){

        cuadroRepetirContrasenna.setOnKeyReleased(event -> {

            String nuevaContrasenna = cuadroContrasennaNueva.getText();

            if(nuevaContrasenna.equals(cuadroRepetirContrasenna.getText())) {
                verificacion1.setText("Coinciden");
                verificacion2.setText("Coinciden");
            }
            else{
                verificacion1.setText("No Coinciden");
                verificacion2.setText("No Coinciden");
            }


        });

        botonCambiarContrasenna.setOnAction(event -> {
            String contraAnterior = cuadroContrasennaAnterior.getText();
            String contraNueva = cuadroContrasennaNueva.getText();
            String contraRepetir = cuadroRepetirContrasenna.getText();

            if(contraAnterior.equals("") || contraNueva.equals("") || contraRepetir.equals(""))
                llamarAlerta("Se deben llenar todos los campos");

            else if(!contraAnterior.equals(contrasennaAnteriorUsuario))
                llamarAlerta("La contraseña usada anteriormente no coincide. Intente de nuevo");

            else if(!contraNueva.equals(contraRepetir))
                llamarAlerta("La nueva contraseña no coincide. Intente de nuevo");

            else{
                // TODO Con un procedure hacer update de la contra del usuario
            }

            limpiarPantalla();

        });

    }

    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }

    public void limpiarPantalla(){
        verificacion1.setText("");
        verificacion2.setText("");
        cuadroContrasennaAnterior.clear();
        cuadroContrasennaNueva.clear();
        cuadroRepetirContrasenna.clear();
    }

}
