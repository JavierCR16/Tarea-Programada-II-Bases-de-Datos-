package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControladorVentanaLogin implements Initializable{

    @FXML
    TextField cuadroUsuarioAdministrador;

    @FXML
    PasswordField cuadroContrasennaAdministrador;

    @FXML
    TextField cuadroUsuarioAgente;

    @FXML
    PasswordField cuadroContrasennaAgente;

    @FXML
    TextField cuadroUsuarioParticipante;

    @FXML
    PasswordField cuadroContrasennaParticipante;

    @FXML
    Button botonLoguearAdministrador;

    @FXML
    Button botonLoguearAgente;

    @FXML
    Button botonLoguearParticipante;

    @FXML
    Label contrasennaOlvidadaAdministrador;

    @FXML
    Label contrasennaOlvidadaAgente;


    Scene escenaActual;

    Connection connection;
    Statement statement;

    public void initialize(URL fxmlLocations, ResourceBundle resources){
        establecerConexion();

        contrasennaOlvidadaAdministrador.setOnMouseEntered(event->{

            escenaActual = contrasennaOlvidadaAdministrador.getScene();
            escenaActual.setCursor(Cursor.HAND);

        });

        contrasennaOlvidadaAdministrador.setOnMouseExited(event->{
            escenaActual = contrasennaOlvidadaAdministrador.getScene();
            escenaActual.setCursor(Cursor.DEFAULT);
        });

        contrasennaOlvidadaAgente.setOnMouseEntered(event->{

            escenaActual = contrasennaOlvidadaAgente.getScene();
            escenaActual.setCursor(Cursor.HAND);

        });

        contrasennaOlvidadaAgente.setOnMouseExited(event->{
            escenaActual = contrasennaOlvidadaAgente.getScene();
            escenaActual.setCursor(Cursor.DEFAULT);
        });

        contrasennaOlvidadaAdministrador.setOnMouseClicked(event -> {

            if(cuadroUsuarioAdministrador.getText().equals("") || cuadroContrasennaAdministrador.getText().equals(""))
                llamarAlerta("Se debe ingresar todos los datos para poder cambiar la contraseña");
            else{
                //TODO Hacer procedure que extraiga una contrasenna dado el usuario.
                abrirVentanaCambiarContrasenna();//Mandar por parametro la contrasena y el usuario
            }

        });

        contrasennaOlvidadaAgente.setOnMouseClicked(event -> {

            if(cuadroUsuarioAgente.getText().equals("")||cuadroContrasennaAgente.getText().equals(""))
                llamarAlerta("Se debe ingresar todos los datos para poder cambiar la contraseña");

            else{
                //TODO Hacer procedure que extraiga una contrasenna dado el usuario.
                abrirVentanaCambiarContrasenna();//Mandar por parametro la contrasena
            }

        });


        botonLoguearAdministrador.setOnAction(event -> {
            ingresarComoAdministrador();
        });

        botonLoguearAgente.setOnAction(event -> {
            ingresarComoAgente();
        });

        botonLoguearParticipante.setOnAction(event -> {
            ingresarComoParticipante();
        });



    }

    public void establecerConexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=PrograBasesTransacciones;integratedSecurity=true;";
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void abrirVentanaCambiarContrasenna(){

        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaCambiarContrasenna.fxml").openStream());
            Stage escenario = new Stage();
            escenario.setTitle("Contraseña");
            escenario.setScene(new Scene(root,498,394));
            escenario.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaAgente(){

        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaAgente.fxml").openStream());
            Stage escenario = new Stage();
            escenario.setTitle("Agente");
            escenario.setScene(new Scene(root,1053,417));
            escenario.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaAdministrador(){}

    public void abrirVentanaParticipante(){}

    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }

    public void ingresarComoAdministrador(){
        String usuarioAdmi = cuadroUsuarioAdministrador.getText();
        String contrasennaAdmi = cuadroContrasennaAdministrador.getText();

        if(usuarioAdmi.equals("") || contrasennaAdmi.equals(""))
            llamarAlerta("Se deben ingresar todos los datos");
        else{
            //TODO PROCEDURE QUE BUSQUE EL USUARIO, SI EXISTE ENTRAR A LA PANTALLA, SINO DISPARAR ALERTA GG IZY
        }
    }

    public void ingresarComoAgente(){
        String usuarioAgente = cuadroUsuarioAgente.getText();
        String contrasennaAgente = cuadroContrasennaAgente.getText();
        if(usuarioAgente.equals("") || contrasennaAgente.equals(""))
            llamarAlerta("Se deben ingresar todos los datos");
        else{
            abrirVentanaAgente();
            //TODO PROCEDURE QUE BUSQUE EL USUARIO, SI EXISTE ENTRAR A LA PANTALLA, SINO DISPARAR ALERTA GG IZY
        }
    }

    public void ingresarComoParticipante(){
        String usuarioParticipante = cuadroUsuarioParticipante.getText();
        String contrasennaParticipante = cuadroContrasennaParticipante.getText();

        if(usuarioParticipante.equals("") || contrasennaParticipante.equals(""))
            llamarAlerta("Se deben ingresar todos los datos");
        else{
            try{//Era de prueba para ver que extrai xdxdxd
                String procedimiento = "{call caca(?)}";
                CallableStatement ejecutarProcPrueba = connection.prepareCall(procedimiento);
                ejecutarProcPrueba.registerOutParameter(1, Types.INTEGER);
                ejecutarProcPrueba.executeUpdate();
                String edad = String.valueOf(ejecutarProcPrueba.getInt(1));
                System.out.println("La edad extraida del procedimiento es: "+edad);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            //TODO PROCEDURE QUE BUSQUE EL USUARIO, SI EXISTE ENTRAR A LA PANTALLA, SINO DISPARAR ALERTA GG IZY
        }
    }


}
