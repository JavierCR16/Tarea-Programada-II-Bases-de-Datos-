package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Javier on 5/15/2017.
 */
public class ControladorVentanaAgente implements Initializable {

    @FXML
    TextField cuentaNuevaCedula;

    @FXML
    TextField cuentaNuevaNombre;

    @FXML
    TextField cuentaNuevaTelefono;

    @FXML
    TextField cuentaNuevaCorreo;

    @FXML
    PasswordField cuentaNuevaClave;

    @FXML
    PasswordField cuentaNuevaConfirmacion;

    @FXML
    Button botonAgregarParticipante;

    @FXML
    Button botonDepositar;

    @FXML
    Button botonRetirar;

    @FXML
    Button botonCerrarSesion;

    @FXML
    TextField cuadroCedulaDeposito;

    @FXML
    TextField cuadroDolaresDeposito;

    @FXML
    TextField cuadroColonesDeposito;

    @FXML
    TextField cuadroCedulaRetiro;

    @FXML
    TextField cuadroDolaresRetiro;

    @FXML
    TextField cuadroColonesRetiro;



    public void initialize(URL fxmlLocatios, ResourceBundle resources){


        botonCerrarSesion.setOnAction(event -> {

            Stage escenarioActual = (Stage)botonCerrarSesion.getScene().getWindow();
            escenarioActual.close();
        });

        botonAgregarParticipante.setOnAction(event -> {
            crearUsuarioParticipante();
            cuentaNuevaCedula.clear();
            cuentaNuevaNombre.clear();
            cuentaNuevaTelefono.clear();
            cuentaNuevaCorreo.clear();
            cuentaNuevaClave.clear();
            cuentaNuevaConfirmacion.clear();

        });

        botonDepositar.setOnAction(event -> {
            realizarDeposito();
            cuadroCedulaDeposito.clear();
            cuadroDolaresDeposito.clear();
            cuadroColonesDeposito.clear();
        });

        botonRetirar.setOnAction(event -> {
           realizarRetiro();
           cuadroCedulaRetiro.clear();
           cuadroDolaresRetiro.clear();
           cuadroColonesRetiro.clear();
        });

    }

    public void crearUsuarioParticipante(){
        String cedulaParticipante = cuentaNuevaCedula.getText();
        String nombreParticipante = cuentaNuevaNombre.getText();
        String telefonoParticipante = cuentaNuevaTelefono.getText();
        String correoParticipante = cuentaNuevaCorreo.getText();
        String palabraClave = cuentaNuevaClave.getText();
        String confirmacionClave = cuentaNuevaConfirmacion.getText();

        boolean datosIncorrectos = false;

        if(cedulaParticipante.equals("") || nombreParticipante.equals("")||telefonoParticipante.equals("") || correoParticipante.equals("")
                || palabraClave.equals("") || confirmacionClave.equals(""))
            llamarAlerta("Deben ingresarse todos los datos del participante");
        else if(!palabraClave.equals(confirmacionClave))
            llamarAlerta("La clave seleccionada no coincide. Intente de nuevo");
        else{
            //TODO PROCEDURE QUE AGREGUE PARTICIPANTES
        }

    }

    public void realizarDeposito(){
        String cedulaADepositar = cuadroCedulaDeposito.getText();
        String dolaresADepositar = cuadroDolaresDeposito.getText();
        String colonesADepositar = cuadroColonesDeposito.getText();

        if(cedulaADepositar.equals(""))
            llamarAlerta("Debe ingresarse la cédula para realizar un depósito");
        else{
            //TODO PROCEDURE QUE DEPOSITE Y SI EN ALGUNA DE LAS VARAS NO SE MANDA NADA ENTONCES MAMELUCO NO PASA NADA
        }

    }

    public void realizarRetiro(){
        String cedulaARetirar = cuadroCedulaRetiro.getText();
        String dolaresARetirar = cuadroDolaresRetiro.getText();
        String colonesARetirar = cuadroColonesRetiro.getText();

        if(cedulaARetirar.equals(""))
            llamarAlerta("Debe ingresarse la cédula para realizar un depósito");
        else{
            //TODO PROCEDURE QUE RETIRE Y SI EN ALGUNA DE LAS VARAS NO SE MANDA NADA ENTONCES MAMELUCO NO PASA NADA
        }

    }

    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }

}
