package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by Javier on 5/15/2017.
 */
public class ControladorVentanaAgente implements Initializable {

    @FXML
    ComboBox cajaDia;

    @FXML
    ComboBox cajaMes;

    @FXML
    ComboBox cajaAno;

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

    Connection connection;
    Statement statement;



    public void initialize(URL fxmlLocatios, ResourceBundle resources){


        setDatosDefecto();

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
            cajaAno.getSelectionModel().clearSelection();
            cajaMes.getSelectionModel().clearSelection();
            cajaDia.getSelectionModel().clearSelection();

        });

        botonDepositar.setOnAction(event -> {
            realizarDeposito();
            cuadroCedulaDeposito.clear();
            cuadroDolaresDeposito.clear();
            cuadroColonesDeposito.clear();
            /*
            Se estaban probando los permisos con una cuenta X
            try{
                String insertarCedula = "SELECT * FROM admi WHERE cedula =?";
                PreparedStatement insert = connection.prepareStatement(insertarCedula);
                insert.setString(1,"123");
                insert.executeQuery();
            }
            catch(SQLException e){
                e.printStackTrace();
            }*/
        });

        botonRetirar.setOnAction(event -> {
           realizarRetiro();
           cuadroCedulaRetiro.clear();
           cuadroDolaresRetiro.clear();
           cuadroColonesRetiro.clear();
        });

    }

    public void setDatosDefecto(){
        for (int i = 68; i > 0; i--) {
            cajaAno.getItems().add(68 - i, (i + 1950));
        }

        for(int i=1;i<13;i++){
            if(i<10)
                cajaMes.getItems().add(i-1,"0"+i);
            else
                cajaMes.getItems().add(i-1,i);
        }

        for (int i = 1; i < 32; i++) {
            if(i<10)
                cajaDia.getItems().add(i-1,"0"+i);
            else
                cajaDia.getItems().add(i-1,i);
        }
    }

    public void crearUsuarioParticipante(){
        String cedulaParticipante = cuentaNuevaCedula.getText();
        String nombreParticipante = cuentaNuevaNombre.getText();
        String telefonoParticipante = cuentaNuevaTelefono.getText();
        String correoParticipante = cuentaNuevaCorreo.getText();
        String palabraClave = cuentaNuevaClave.getText();
        String confirmacionClave = cuentaNuevaConfirmacion.getText();
        Object annoNacimiento = cajaAno.getSelectionModel().getSelectedItem();
        Object mesNacimiento = cajaMes.getSelectionModel().getSelectedItem();
        Object diaNacimiento = cajaDia.getSelectionModel().getSelectedItem();

        if(cedulaParticipante.equals("") || nombreParticipante.equals("")||telefonoParticipante.equals("") || correoParticipante.equals("")
                || palabraClave.equals("") || confirmacionClave.equals(""))
            llamarAlerta("Deben ingresarse todos los datos del participante");
        else if(!palabraClave.equals(confirmacionClave))
            llamarAlerta("La clave seleccionada no coincide. Intente de nuevo");
        else{
            try{
                Integer.parseInt(cedulaParticipante);

                String fechaNacimiento = annoNacimiento.toString()+"/"+mesNacimiento.toString()+"/"+diaNacimiento.toString();
                java.util.Date miFecha = new java.util.Date(fechaNacimiento);
                java.sql.Date sqlDate = new java.sql.Date(miFecha.getTime());

                String procedimientoInsertarParticipante = "{call crearLoginParaParticipante(?,?)}"; // TODO EDITAR PARA PONER PERMISOS, QUE BASE USAR(PROGRABASES...)
                String procedimientoInsertarUsuarioParticipante = "{call insertarParticipante(?,?,?,?,?)}";

                CallableStatement ejecutarProcedimiento = connection.prepareCall(procedimientoInsertarParticipante);
                ejecutarProcedimiento.setString(1,cedulaParticipante);
                ejecutarProcedimiento.setString(2,palabraClave);
                ejecutarProcedimiento.executeUpdate();


                CallableStatement ejecutarProcedimientoInsercion = connection.prepareCall(procedimientoInsertarUsuarioParticipante);
                ejecutarProcedimientoInsercion.setString(1,cedulaParticipante);
                ejecutarProcedimientoInsercion.setString(2,nombreParticipante);
                ejecutarProcedimientoInsercion.setString(3,cedulaParticipante);
                ejecutarProcedimientoInsercion.setString(4,telefonoParticipante);
                ejecutarProcedimientoInsercion.setDate(5,sqlDate);
                ejecutarProcedimientoInsercion.executeUpdate();
            }
            catch(Exception e){
                e.printStackTrace();
                llamarAlerta("El participante ingresado ya existe o hubo un error en los datos ingresados. Intente de nuevo");
            }
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
