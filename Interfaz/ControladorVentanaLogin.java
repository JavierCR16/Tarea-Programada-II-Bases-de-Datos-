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
import java.util.*;
import java.util.Date;

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
    Button botonConsultarLogearGuestPublico;

    @FXML
    Button botonLoguearAgente;

    @FXML
    Button botonLoguearParticipante;

    @FXML
    Label contrasennaOlvidadaAdministrador;

    @FXML
    Label contrasennaOlvidadaAgente;

    @FXML
    Label labelColonesCambioPromPublico;

    @FXML
    Label labelDolaresCambioPromPublico;

    @FXML
    Label labelMontoTransPublico;

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

        botonConsultarLogearGuestPublico.setOnAction(event -> {
            Connection nuevaConexion = devolverConnection("guest","guest1");
            Statement nuevoEstado = devolverStatement("guest","guest1");
            escribirMovimientoTipoLogin("guest");
        });
        contrasennaOlvidadaAdministrador.setOnMouseClicked(event -> {

            if(cuadroUsuarioAdministrador.getText().equals("") || cuadroContrasennaAdministrador.getText().equals(""))
                llamarAlerta("Se debe ingresar todos los datos para poder cambiar la contraseña");
            else{
                String usuarioAdministrador = cuadroUsuarioAdministrador.getText();
                String contrasennaAdministrador = cuadroContrasennaAdministrador.getText();
                boolean existeUsuario = existeConexionUsuarios(usuarioAdministrador,contrasennaAdministrador);
                String esAdministrador = procedureBuscarLogin(1,usuarioAdministrador);

                if(!existeUsuario || esAdministrador==null)
                    llamarAlerta("El usuario ingresado no existe o usted no tiene permisos como Administrador. Debe ingresar un usuario valido para poder cambiar la contraseña");
                else {
                    Connection nuevaConexion = devolverConnection(usuarioAdministrador,contrasennaAdministrador);
                    Statement nuevoEstado = devolverStatement(usuarioAdministrador,contrasennaAdministrador);
                    abrirVentanaCambiarContrasenna(nuevaConexion,nuevoEstado,usuarioAdministrador,contrasennaAdministrador);
                }
            }
            limpiarPantallaLogin();

        });


        contrasennaOlvidadaAgente.setOnMouseClicked(event -> {

            if(cuadroUsuarioAgente.getText().equals("")||cuadroContrasennaAgente.getText().equals(""))
                llamarAlerta("Se debe ingresar todos los datos para poder cambiar la contraseña");

            else{

                String usuarioAgente = cuadroUsuarioAgente.getText();
                String contrasennaAgente = cuadroContrasennaAgente.getText();
                boolean existeUsuario = existeConexionUsuarios(usuarioAgente,contrasennaAgente);
                String esAgente = procedureBuscarLogin(2,usuarioAgente);

                if(!existeUsuario || esAgente==null)
                    llamarAlerta("El usuario ingresado no existe o usted no tiene permisos como Agente. Debe ingresar un usuario valido para poder cambiar la contraseña");
                else {
                    Connection nuevaConexion = devolverConnection(usuarioAgente,contrasennaAgente);
                    Statement nuevoEstado = devolverStatement(usuarioAgente,contrasennaAgente);
                    abrirVentanaCambiarContrasenna(nuevaConexion,nuevoEstado,usuarioAgente,contrasennaAgente);
                }
            }
            limpiarPantallaLogin();

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

    public void abrirVentanaCambiarContrasenna(Connection conexion, Statement estado,String usuario,String contrasennaAnterior){

        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaCambiarContrasenna.fxml").openStream());
            ControladorVentanaCambiarContrasenna controladorContrasenna = loader.getController();
            controladorContrasenna.contrasennaAnteriorUsuario = contrasennaAnterior;
            controladorContrasenna.loginACambiar=usuario;
            controladorContrasenna.connection = conexion;
            controladorContrasenna.statement = estado;
            Stage escenario = new Stage();
            escenario.setTitle("Contraseña");
            escenario.setScene(new Scene(root,498,354));
            escenario.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaAgente(Connection connection, Statement statement,String usuarioActual){

        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaAgente.fxml").openStream());
            ControladorVentanaAgente controlador = loader.getController();
            controlador.connection= connection;
            controlador.statement = statement;
            controlador.agenteActual = usuarioActual;
            Stage escenario = new Stage();
            escenario.setTitle("Agente");
            escenario.setScene(new Scene(root,1053,445));
            escenario.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaParticipante(Connection connection,Statement statement,String usuarioActual){

            try{
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("VentanaParticipante.fxml").openStream());
                ControladorVentanaParticipante controlador = loader.getController();
                controlador.connection = connection;
                controlador.statement = statement;
                controlador.participanteActual = usuarioActual;
                controlador.setSesionActual();
                Stage escenario = new Stage();
                escenario.setTitle("Participante");
                escenario.setScene(new Scene(root,1053,417));
                escenario.show();
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }

    public void abrirVentanaAdministrador(Connection connection, Statement statement,String usuarioActual){
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("VentanaAdministrador.fxml").openStream());
            ControladorVentanaAdministrador controlador = loader.getController();
            controlador.connection = connection;
            controlador.statement = statement;
            controlador.administradorActual = usuarioActual;
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
                 abrirVentanaAdministrador(nuevaConexion,nuevoEstado,usuarioAdmi);
                 escribirMovimientoTipoLogin(usuarioAdmi);
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
                abrirVentanaAgente(nuevaConexion,nuevoEstado,usuarioAgente);
                escribirMovimientoTipoLogin(usuarioAgente);
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
            String esParticipante = procedureBuscarLogin(3,usuarioParticipante);
            String sesionAbierta = ultimoEstadoSesion()[1];

            if(!existeUsuario || esParticipante==null || sesionAbierta==null ||!sesionAbierta.equals("ABIERTO"))
                llamarAlerta("El usuario ingresado no existe, fue suspendido, usted no tiene permisos como Participante o no existe una sesion disponible");

            else{
                Connection nuevaConexion = devolverConnection(usuarioParticipante,contrasennaParticipante);
                Statement nuevoEstado = devolverStatement(usuarioParticipante,contrasennaParticipante);
                abrirVentanaParticipante(nuevaConexion,nuevoEstado,usuarioParticipante);
                escribirMovimientoTipoLogin(usuarioParticipante);
            }

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

    public void escribirMovimientoTipoLogin(String personaLogueada){

        String mensaje = "El usuario: "+personaLogueada+" se ha logueado satisfactoriamente en la fecha: ";


        try{
            String movimientoLogin = "{call escribirMovimientoLogin(?,?)}";
            CallableStatement ejecutarProcedimientoMovimiento = connection.prepareCall(movimientoLogin);
            ejecutarProcedimientoMovimiento.setString(1,personaLogueada);
            ejecutarProcedimientoMovimiento.setString(2,mensaje);
            ejecutarProcedimientoMovimiento.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            llamarAlerta("Error al escribir en la bitácora.");
        }
    }

    public String[] ultimoEstadoSesion(){
        String estadoSesion = "";
        String idSesion="";
        String [] infoSesion = new String [2];
        try{
            String ultimoEstado = "{call estadoUltimaSesion(?,?)}";
            CallableStatement procedimientoEstado = connection.prepareCall(ultimoEstado);
            procedimientoEstado.registerOutParameter(1,Types.INTEGER);
            procedimientoEstado.registerOutParameter(2, Types.VARCHAR);
            procedimientoEstado.executeUpdate();
            idSesion = String.valueOf(procedimientoEstado.getInt(1));
            estadoSesion = procedimientoEstado.getString(2);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println(estadoSesion);
        infoSesion[0] = idSesion;
        infoSesion[1]= estadoSesion;

        return infoSesion;

    }


}
