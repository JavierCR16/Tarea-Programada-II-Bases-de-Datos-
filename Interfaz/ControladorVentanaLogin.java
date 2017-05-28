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
            limpiarPantallaLogin();
        });

        botonLoguearAgente.setOnAction(event -> {
            ingresarComoAgente();
            limpiarPantallaLogin();
        });

        botonLoguearParticipante.setOnAction(event -> {
            ingresarComoParticipante();
            limpiarPantallaLogin();
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

    public boolean existeConexionUsuarios(String username,String password){
        Connection conexion = null;
        Statement estado= null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=PrograBasesTransacciones;user="+username
                    +";password="+password;
            conexion = DriverManager.getConnection(connectionUrl);
            estado = connection.createStatement();
            return true;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public Connection devolverConnection(String username, String password){
        Connection conexion = null;
        Statement estado = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=PrograBasesTransacciones;user="+username
                    +";password="+password;
            conexion = DriverManager.getConnection(connectionUrl);
            estado = conexion.createStatement();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return conexion;
    }

    public Statement devolverStatement(String username, String password){
        Connection conexion = null;
        Statement estado = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=PrograBasesTransacciones;user="+username
                    +";password="+password;
            conexion = DriverManager.getConnection(connectionUrl);
            estado = conexion.createStatement();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return estado;
    }

    public void abrirVentanaCambiarContrasenna(){

        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaCambiarContrasenna.fxml").openStream());
            Stage escenario = new Stage();
            escenario.setTitle("Contraseña");
            escenario.setScene(new Scene(root,498,354));
            escenario.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaAgente(Connection connection, Statement statement){

        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaAgente.fxml").openStream());
            ControladorVentanaAgente controlador = loader.getController();
            controlador.connection= connection;
            controlador.statement = statement;
            Stage escenario = new Stage();
            escenario.setTitle("Agente");
            escenario.setScene(new Scene(root,1053,417));
            escenario.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaParticipante(Connection connection,Statement statement){

            try{
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("VentanaParticipante.fxml").openStream());
                Stage escenario = new Stage();
                escenario.setTitle("Participante");
                escenario.setScene(new Scene(root,1053,417));
                escenario.show();
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }

    public void abrirVentanaAdministrador(Connection connection, Statement statement){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaAdministrador.fxml").openStream());
            ControladorVentanaAdministrador controlador = loader.getController();
            controlador.connection = connection;
            controlador.statement = statement;
            Stage escenario = new Stage();
            escenario.setTitle("Administrador");
            escenario.setScene(new Scene(root,1275,440));
            escenario.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ingresarComoAdministrador(){
        String usuarioAdmi = cuadroUsuarioAdministrador.getText();
        String contrasennaAdmi = cuadroContrasennaAdministrador.getText();

        if(usuarioAdmi.equals("") || contrasennaAdmi.equals(""))
            llamarAlerta("Se deben ingresar todos los datos");
        else{
            boolean existeUsuario = existeConexionUsuarios(usuarioAdmi,contrasennaAdmi);
            String esAdministrador = procedureBuscarLogin(1,usuarioAdmi); //si el query no devuelve nada, es null la variable


            if(!existeUsuario ||esAdministrador==null)
                llamarAlerta("El usuario ingresado no existe o usted no tiene permisos como administrador");
            else{
                 Connection nuevaConexion = devolverConnection(usuarioAdmi,contrasennaAdmi);
                 Statement nuevoEstado = devolverStatement(usuarioAdmi,contrasennaAdmi);
                 abrirVentanaAdministrador(nuevaConexion,nuevoEstado);
            }

        }
    }

    public void ingresarComoAgente(){
        String usuarioAgente = cuadroUsuarioAgente.getText();
        String contrasennaAgente = cuadroContrasennaAgente.getText();
        if(usuarioAgente.equals("") || contrasennaAgente.equals(""))
            llamarAlerta("Se deben ingresar todos los datos");
        else{

            boolean existeUsuario = existeConexionUsuarios(usuarioAgente,contrasennaAgente);
            String esAgente = procedureBuscarLogin(2,usuarioAgente);


            if(!existeUsuario || esAgente==null)
                llamarAlerta("El usuario ingresado no existe o usted no tiene permisos como Agente");
           else{
                Connection nuevaConexion = devolverConnection(usuarioAgente,contrasennaAgente);
                Statement nuevoEstado = devolverStatement(usuarioAgente,contrasennaAgente);
                abrirVentanaAgente(nuevaConexion,nuevoEstado);
            }
        }
    }

    public String procedureBuscarLogin(int opcion, String cedula){
        String procedimiento = "";
        String valorDevuelto = "";
        switch (opcion){
            case 1: //Administrador
                try {
                    procedimiento = "{call existeAdministrador(?,?)}";
                    CallableStatement procAdministrador = connection.prepareCall(procedimiento);
                    procAdministrador.setString(1,cedula);
                    procAdministrador.registerOutParameter(2, Types.VARCHAR);
                    procAdministrador.executeUpdate();
                    valorDevuelto = procAdministrador.getString(2);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case 2: //Agente
                try {
                    procedimiento = "{call existeAgente(?,?)}";
                    CallableStatement procAgente = connection.prepareCall(procedimiento);
                    procAgente.setString(1,cedula);
                    procAgente.registerOutParameter(2, Types.VARCHAR);
                    procAgente.executeUpdate();
                    valorDevuelto = procAgente.getString(2);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                break;

            case 3: //Participante
                try {
                    procedimiento = "{call existeParticipante(?,?)}";
                    CallableStatement procParticipante = connection.prepareCall(procedimiento);
                    procParticipante.setString(1,cedula);
                    procParticipante.registerOutParameter(2, Types.VARCHAR);
                    procParticipante.executeUpdate();
                    valorDevuelto = procParticipante.getString(2);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                break;
        }
        return valorDevuelto;
    }

    public void ingresarComoParticipante(){
        String usuarioParticipante = cuadroUsuarioParticipante.getText();
        String contrasennaParticipante = cuadroContrasennaParticipante.getText();

        if(usuarioParticipante.equals("") || contrasennaParticipante.equals(""))
            llamarAlerta("Se deben ingresar todos los datos");
        else{
            boolean existeUsuario = existeConexionUsuarios(usuarioParticipante,contrasennaParticipante);
            String esParticipante = procedureBuscarLogin(2,usuarioParticipante);


            if(!existeUsuario || esParticipante==null)
                llamarAlerta("El usuario ingresado no existe, fue suspendido o usted no tiene permisos como Participante");

            else{
                Connection nuevaConexion = devolverConnection(usuarioParticipante,contrasennaParticipante);
                Statement nuevoEstado = devolverStatement(usuarioParticipante,contrasennaParticipante);
                abrirVentanaParticipante(nuevaConexion,nuevoEstado);
            }

       /*     try{//Era de prueba para ver que extrai xdxdxd
                String procedimiento = "{call caca(?)}";
                CallableStatement ejecutarProcPrueba = connection.prepareCall(procedimiento);
                ejecutarProcPrueba.registerOutParameter(1, Types.VARCHAR);
                ejecutarProcPrueba.executeUpdate();
                String edad = String.valueOf(ejecutarProcPrueba.getString(1));
                System.out.println("La edad extraida del procedimiento es: "+edad);
            }
            catch(SQLException e){
                e.printStackTrace();
            }*/
            //TODO PROCEDURE QUE BUSQUE EL USUARIO, SI EXISTE ENTRAR A LA PANTALLA, SINO DISPARAR ALERTA GG IZY
        }
    }

    public void limpiarPantallaLogin(){
        cuadroUsuarioParticipante.clear();
        cuadroUsuarioAgente.clear();
        cuadroUsuarioAdministrador.clear();
        cuadroContrasennaParticipante.clear();
        cuadroContrasennaAgente.clear();
        cuadroContrasennaAdministrador.clear();
    }

    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }


}
