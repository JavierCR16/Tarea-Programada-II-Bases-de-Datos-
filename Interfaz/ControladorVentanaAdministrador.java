package Interfaz;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jdk.nashorn.internal.codegen.CompilerConstants;
import sun.util.resources.cldr.vai.CalendarData_vai_Latn_LR;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Javier on 5/16/2017.
 */
public class ControladorVentanaAdministrador implements Initializable {
    @FXML
    Button botonCerrarSesionAdmi;

    @FXML
    Label totalCompraDolares;

    @FXML
    Label promedioTipoCambio;

    @FXML
    ComboBox cajaPorcentajeComision;

    @FXML
    Button botonEstablecerPorcentaje;

    @FXML
    Button botonAbrirSesion;

    @FXML
    Button botonCerrarSesion;

    @FXML
    TextField cedulaCrearAgente;

    @FXML
    TextField nombreCrearAgente;

    @FXML
    TextField correoCrearAgente;

    @FXML
    TextField telefonoCrearAgente;

    @FXML
    ComboBox cajaAno;

    @FXML
    ComboBox cajaMes;

    @FXML
    ComboBox cajaDia;

    @FXML
    Button botonCrearAgente;

    @FXML
    TableView tablaVisualizarTransaccionesC;

    @FXML
    Button botonVisualizarTransaccionesC;

    @FXML
    Label totalVentaDolares;

    @FXML
    PasswordField contrasenaCrearAgente;

    @FXML
    PasswordField confirmacionCrearAgente;

    @FXML
    TableView tablaUsuariosListados;

    @FXML
    Button botonListarUsuarios;

    @FXML
    TableColumn columnaUsuarioListado;

    @FXML
    TableColumn columnaEstadoUsuario;

    @FXML
    TableView pizarraAdministrador;

    @FXML
    TableColumn columnaConsecutivo;

    @FXML
    TableColumn columnaVentaCompra;

    @FXML
    TableColumn columnaMoneda;

    @FXML
    TableColumn columnaMonto;

    @FXML
    TableColumn columnaTipo;

    @FXML
    Button botonOrdenarPizarra;

    @FXML
    ComboBox cajaTipoOrdenamiento;

    @FXML
    TextField cuadroUsuarioASuspender;

    @FXML
    Button botonSuspenderUsuario;

    public Connection connection;
    public Statement statement;

    public String administradorActual;




    public void initialize(URL fxmlLocations, ResourceBundle resources) {

        setDatosDefecto();

        botonAbrirSesion.setOnAction(event -> {
           abrirSesion();
        });

        botonCerrarSesion.setOnAction(event -> {
            cerrarSesion();
        });

        botonSuspenderUsuario.setOnAction(event -> {
            suspenderUser();
            cuadroUsuarioASuspender.clear();
        });

        botonCerrarSesionAdmi.setOnAction(event -> {
            Stage escenarioActual = (Stage) botonCerrarSesionAdmi.getScene().getWindow();
            escenarioActual.close();
        });

        botonCrearAgente.setOnAction(event -> {
            crearUsuarioAgente();
            cedulaCrearAgente.clear();
            nombreCrearAgente.clear();
            correoCrearAgente.clear();
            contrasenaCrearAgente.clear();
            confirmacionCrearAgente.clear();
            telefonoCrearAgente.clear();
            cajaDia.getSelectionModel().clearSelection();
            cajaMes.getSelectionModel().clearSelection();
            cajaAno.getSelectionModel().clearSelection();

        });

        botonOrdenarPizarra.setOnAction(event -> {
            listarPizarra();
            cajaTipoOrdenamiento.getSelectionModel().clearSelection();
        });

        botonEstablecerPorcentaje.setOnAction(event -> {
            establecerPorcentajeComision();
            cajaPorcentajeComision.getSelectionModel().clearSelection();
        });


    }

    public void abrirSesion(){

        try{
            String abrirSesion = "{call abrirCerrarSesion(?,?)}";
            CallableStatement procedimientoSesion  = connection.prepareCall(abrirSesion);
            procedimientoSesion.setString(1,administradorActual);
            procedimientoSesion.setString(2,"ABRIR");
            procedimientoSesion.executeUpdate();
        }
        catch(SQLException e){
           // e.printStackTrace();
            llamarAlerta("Ya se encuentra una sesion abierta. Intente más tarde");
        }

    }

    public void cerrarSesion(){
        try{
            String cerrarSesion = "{call abrirCerrarSesion(?,?)}";
            CallableStatement procedimientoSesion  = connection.prepareCall(cerrarSesion);
            procedimientoSesion.setString(1,administradorActual);
            procedimientoSesion.setString(2,"CERRAR");
            procedimientoSesion.executeUpdate();
        }
        catch(SQLException e){
//            e.printStackTrace();
            llamarAlerta("No hay ninguna sesion para cerrar. Intente más tarde");
        }
    }

    public void setDatosDefecto(){

        for (int i = 68; i > 0; i--) {  //TODO CAMBIAR PARA PONER OTROS VALORESEN LAS CAJAS
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

        cajaTipoOrdenamiento.getItems().addAll("Consecutivo","Monto","Cambio: Colones","Cambio: Dolares","Venta","Compra");

    }

    public void crearUsuarioAgente(){
        String cedulaAgente = cedulaCrearAgente.getText();
        String nombreAgente= nombreCrearAgente.getText();
        String telefonoAgente = telefonoCrearAgente.getText();
        String correoAgente = correoCrearAgente.getText();
        String palabraClave = contrasenaCrearAgente.getText();
        String confirmacionClave = confirmacionCrearAgente.getText();
        Object annoNacimiento = cajaAno.getSelectionModel().getSelectedItem();
        Object mesNacimiento = cajaMes.getSelectionModel().getSelectedItem();
        Object diaNacimiento = cajaDia.getSelectionModel().getSelectedItem();

        if(cedulaAgente.equals("") || nombreAgente.equals("")||telefonoAgente.equals("") || correoAgente.equals("")
                || palabraClave.equals("") || confirmacionClave.equals("")|| annoNacimiento==null||mesNacimiento==null
                ||diaNacimiento==null)
            llamarAlerta("Deben ingresarse todos los datos del Agente");
        else if(!palabraClave.equals(confirmacionClave))
            llamarAlerta("La clave seleccionada no coincide. Intente de nuevo");
        else{
            try{
                Integer.parseInt(cedulaAgente);

                String fechaNacimiento = annoNacimiento.toString()+"/"+mesNacimiento.toString()+"/"+diaNacimiento.toString();
                java.util.Date miFecha = new java.util.Date(fechaNacimiento);
                java.sql.Date sqlDate = new java.sql.Date(miFecha.getTime());

                String procedimientoInsertarAgente = "{call crearLoginParaAgente(?,?)}"; // TODO EDITAR PARA PONER PERMISOS(USAR EXECUTE), QUE BASE USAR, y poder otorgarle el rol sysadmin para que pueda crear logins el agente.
                String procedimientoInsertarUsuarioAgente = "{call insertarAgente(?,?,?,?,?)}";

                CallableStatement ejecutarProcedimiento = connection.prepareCall(procedimientoInsertarAgente);
                ejecutarProcedimiento.setString(1,cedulaAgente);
                ejecutarProcedimiento.setString(2,palabraClave);
                ejecutarProcedimiento.executeUpdate();


                CallableStatement ejecutarProcedimientoInsercion = connection.prepareCall(procedimientoInsertarUsuarioAgente);
                ejecutarProcedimientoInsercion.setString(1,cedulaAgente);
                ejecutarProcedimientoInsercion.setString(2,nombreAgente);
                ejecutarProcedimientoInsercion.setString(3,cedulaAgente);
                ejecutarProcedimientoInsercion.setString(4,telefonoAgente);
                ejecutarProcedimientoInsercion.setDate(5,sqlDate);
                ejecutarProcedimientoInsercion.executeUpdate();


            }
            catch(Exception e){
                e.printStackTrace();
                llamarAlerta("El agente ingresado ya existe o hubo un error en los datos ingresados. Intente de nuevo");
            }
        }

    }

    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }

    public void listarPizarra(){
        Object tipoOrdenamiento = cajaTipoOrdenamiento.getSelectionModel().getSelectedItem();
        if(tipoOrdenamiento == null){
            llamarAlerta("Se debe escoger un tipo de ordenamiento");
        }
        else {
            String ordenamiento = tipoOrdenamiento.toString();

            if(ordenamiento.equals("Consecutivo")) {
                int a = 3;

            }
                 //TODO ORDENAR POR CONSECUTIVO IZY xdxd
            else if(ordenamiento.equals("Monto")) {

            }
                 //TODO ORDENAR POR MONTO
            else if(ordenamiento.equals("Cambio: Colones")){

            }
                //TODO ORDENAR POR CAMBIO colones
            else if(ordenamiento.equals("Cambio: Dolares")){

            }
                //TODO ORDENAR POR CAMBIO dolares
            else if(ordenamiento.equals("Venta")){

            }
                //TODO ORDENAR POR venta
            else{

            }
                //TODO ORDENAR POR colones

            // TODO Procedimiento que retorne la tabla actual y dependiendo del tipo de ordenamiento, la ordene, o con la clausula
            // order by creo
        }
    }

    public void suspenderUser(){
        String identificacionUsuario = cuadroUsuarioASuspender.getText();
        if(identificacionUsuario.equals(""))
            llamarAlerta("Se debe ingresar la identificación del usuario a suspender");

        else{
            try{
                Integer.parseInt(identificacionUsuario);
                String suspenderUsuario = "{call suspenderUsuarioParticipante(?)}";
                CallableStatement ejecutarProcSuspender = connection.prepareCall(suspenderUsuario);
                ejecutarProcSuspender.setString(1,identificacionUsuario);
                ejecutarProcSuspender.executeUpdate();
            }
            catch(SQLException e){
                llamarAlerta("Ingrese la cedula sin guiones y sin espacios");
            }
        }
    }

    public void establecerPorcentajeComision(){
        Object comision = cajaPorcentajeComision.getSelectionModel().getSelectedItem();
        if(comision == null)
            llamarAlerta("Se debe introducir un porcentaje de comision");
        else{
            //TODO Hacer procedure que guarde en la base el porcentaje de comision actual
        }
    }


}
