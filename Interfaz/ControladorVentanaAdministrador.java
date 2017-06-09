package Interfaz;

import Auxiliares.Oferta;
import Auxiliares.Transaccion;
import Auxiliares.Usuario;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.nashorn.internal.codegen.CompilerConstants;
import sun.util.resources.cldr.vai.CalendarData_vai_Latn_LR;

import java.io.IOException;
import java.math.BigDecimal;
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
    TextField cajaPorcentajeComision;

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
    TableColumn columnaIDlistarPizarra;

    @FXML
    TableColumn columnaUsuarioListarPizarra;

    @FXML
    TableColumn columnaTipoListarPizarra;

    @FXML
    Button botonActualizarCajaVisualizarTransacciones;

    @FXML
    TableColumn columnaMonedaListarPizarra;

    @FXML
    TableColumn columnaTipoCambioListarPizarra;

    @FXML
    TableColumn columnaMontoListarPizarra;

    @FXML
    Button botonOrdenarPizarra;

    @FXML
    ComboBox cajaTipoOrdenamiento;

    @FXML
    TextField cuadroUsuarioASuspender;

    @FXML
    Button botonSuspenderUsuario;

    @FXML
    ComboBox cajaSesionVisualizarTransacciones;

    @FXML
    TableColumn columUsuario1VisualizarTransacciones;

    @FXML
    TableColumn columnTransaccionVisualizarTransacciones;

    @FXML
    TableColumn columnMontoVisualizarTransacciones;

    @FXML
    TableColumn columUsuario2VisualizarTransacciones;

    @FXML
    TableColumn columnTipoCambioVisualizarTransacciones;



    public Connection connection;
    public Statement statement;

    public String administradorActual;

    public void initialize(URL fxmlLocations, ResourceBundle resources) {

        setDatosDefecto();

        configurarTablas();


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
            cajaPorcentajeComision.clear();
        });

        botonListarUsuarios.setOnAction(event -> {
            listarUsuarios();
        });

        botonActualizarCajaVisualizarTransacciones.setOnAction(event -> {
            llenarComboboxIDsesiones();
        });

        botonOrdenarPizarra.setOnAction(event -> {
            listarPizarra();
        });

        botonVisualizarTransaccionesC.setOnAction(event -> {
            listarUltimasTransacciones();
        });
    }

    public void abrirSesion() {

        try {
            String abrirSesion = "{call abrirCerrarSesion(?,?)}";
            CallableStatement procedimientoSesion = connection.prepareCall(abrirSesion);
            procedimientoSesion.setString(1, administradorActual);
            procedimientoSesion.setString(2, "ABRIR");
            procedimientoSesion.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace();
            llamarAlerta("Ya se encuentra una sesion abierta. Intente más tarde");
        }

    }

    public void cerrarSesion() {
        try {
            String cerrarSesion = "{call abrirCerrarSesion(?,?)}";
            CallableStatement procedimientoSesion = connection.prepareCall(cerrarSesion);
            procedimientoSesion.setString(1, administradorActual);
            procedimientoSesion.setString(2, "CERRAR");
            procedimientoSesion.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
            llamarAlerta("No hay ninguna sesion para cerrar. Intente más tarde");
        }
    }

    public void setDatosDefecto() {

        for (int i = 68; i > 0; i--) {  //TODO CAMBIAR PARA PONER OTROS VALORESEN LAS CAJAS
            cajaAno.getItems().add(68 - i, (i + 1950));
        }

        for (int i = 1; i < 13; i++) {
            if (i < 10)
                cajaMes.getItems().add(i - 1, "0" + i);
            else
                cajaMes.getItems().add(i - 1, i);
        }

        for (int i = 1; i < 32; i++) {
            if (i < 10)
                cajaDia.getItems().add(i - 1, "0" + i);
            else
                cajaDia.getItems().add(i - 1, i);
        }

        cajaTipoOrdenamiento.getItems().addAll( "Venta", "Compra","Ambos");

    }

    public void crearUsuarioAgente() {
        String cedulaAgente = cedulaCrearAgente.getText();
        String nombreAgente = nombreCrearAgente.getText();
        String telefonoAgente = telefonoCrearAgente.getText();
        String correoAgente = correoCrearAgente.getText();
        String palabraClave = contrasenaCrearAgente.getText();
        String confirmacionClave = confirmacionCrearAgente.getText();
        Object annoNacimiento = cajaAno.getSelectionModel().getSelectedItem();
        Object mesNacimiento = cajaMes.getSelectionModel().getSelectedItem();
        Object diaNacimiento = cajaDia.getSelectionModel().getSelectedItem();

        if (cedulaAgente.equals("") || nombreAgente.equals("") || telefonoAgente.equals("") || correoAgente.equals("")
                || palabraClave.equals("") || confirmacionClave.equals("") || annoNacimiento == null || mesNacimiento == null
                || diaNacimiento == null)
            llamarAlerta("Deben ingresarse todos los datos del Agente");
        else if (!palabraClave.equals(confirmacionClave))
            llamarAlerta("La clave seleccionada no coincide. Intente de nuevo");
        else {
            try {
                Integer.parseInt(cedulaAgente);

                String fechaNacimiento = annoNacimiento.toString() + "/" + mesNacimiento.toString() + "/" + diaNacimiento.toString();
                java.util.Date miFecha = new java.util.Date(fechaNacimiento);
                java.sql.Date sqlDate = new java.sql.Date(miFecha.getTime());

                String procedimientoInsertarAgente = "{call crearLoginParaAgente(?,?)}"; // TODO EDITAR PARA PONER PERMISOS(USAR EXECUTE), QUE BASE USAR, y poder otorgarle el rol sysadmin para que pueda crear logins el agente.
                String procedimientoInsertarUsuarioAgente = "{call insertarAgente(?,?,?,?,?)}";

                CallableStatement ejecutarProcedimiento = connection.prepareCall(procedimientoInsertarAgente);
                ejecutarProcedimiento.setString(1, cedulaAgente);
                ejecutarProcedimiento.setString(2, palabraClave);
                ejecutarProcedimiento.executeUpdate();


                CallableStatement ejecutarProcedimientoInsercion = connection.prepareCall(procedimientoInsertarUsuarioAgente);
                ejecutarProcedimientoInsercion.setString(1, cedulaAgente);
                ejecutarProcedimientoInsercion.setString(2, nombreAgente);
                ejecutarProcedimientoInsercion.setString(3, cedulaAgente);
                ejecutarProcedimientoInsercion.setString(4, telefonoAgente);
                ejecutarProcedimientoInsercion.setDate(5, sqlDate);
                ejecutarProcedimientoInsercion.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                llamarAlerta("El agente ingresado ya existe o hubo un error en los datos ingresados. Intente de nuevo");
            }
        }

    }

    public void llamarAlerta(String error) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }


    public boolean estaSuspendido(String cedulaParticipante) {
        String valorDevuelto = "";
        try {

            String procedimiento = "{call existeParticipante(?,?)}";
            CallableStatement procParticipante = connection.prepareCall(procedimiento);
            procParticipante.setString(1, cedulaParticipante);
            procParticipante.registerOutParameter(2, Types.VARCHAR);
            procParticipante.executeUpdate();
            valorDevuelto = procParticipante.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (valorDevuelto == null)
            return true;
        return false;
    }

    public void suspenderUser() {
        String identificacionUsuario = cuadroUsuarioASuspender.getText();
        boolean isSuspended = estaSuspendido(identificacionUsuario);


        if (identificacionUsuario.equals(""))
            llamarAlerta("Se debe ingresar la identificación del usuario a suspender");

        else if (isSuspended)
            llamarAlerta("El usuario ingresado no existe o ya se encuentra suspendido");

        else {
            try {
                Integer.parseInt(identificacionUsuario);
                String suspenderUsuario = "{call suspenderUsuarioParticipante(?)}";
                CallableStatement ejecutarProcSuspender = connection.prepareCall(suspenderUsuario);
                ejecutarProcSuspender.setString(1, identificacionUsuario);
                ejecutarProcSuspender.executeUpdate();
                escribirMovimientoSuspension(identificacionUsuario);

            } catch (SQLException e) {
                llamarAlerta("Ingrese la cedula sin guiones y sin espacios");
            }
        }
    }

    public void establecerPorcentajeComision() {
        String comision = cajaPorcentajeComision.getText();
        if (comision.equals(""))
            llamarAlerta("Se debe introducir un porcentaje de comision");
        else {
            try {
                String procedimientoComision = "{call agregarComision(?)}";
                CallableStatement ejecutarComision = connection.prepareCall(procedimientoComision);
                ejecutarComision.setBigDecimal(1, new BigDecimal(comision));
                ejecutarComision.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void escribirMovimientoSuspension(String cedulaUsuario) {
        String mensaje = "El usuario: " + cedulaUsuario + " ha sido suspendido en la fecha: ";
        try {
            String movimientoSuspension = "{call escribirMovimientoSuspension(?,?)}";
            CallableStatement procedimientoSuspension = connection.prepareCall(movimientoSuspension);
            procedimientoSuspension.setString(1, administradorActual);
            procedimientoSuspension.setString(2, mensaje);
            procedimientoSuspension.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            llamarAlerta("Error al escribir en la bitácora");

        }
    }

    public void listarUsuarios() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        try {
            String getUsers = "{call extraerUsuarios()}";
            CallableStatement procedimientoUsuarios = connection.prepareCall(getUsers);
            procedimientoUsuarios.execute();
            ResultSet tuplesUsuarios = procedimientoUsuarios.getResultSet();

            while (tuplesUsuarios.next()) {
                listaUsuarios.add(new Usuario(tuplesUsuarios.getString("CEDULAUSUARIO"), tuplesUsuarios.getString("SUSPENDIDO")));
            }

            ObservableList<Usuario> usuariosListados = FXCollections.observableArrayList(listaUsuarios);
            tablaUsuariosListados.setItems(usuariosListados);
        } catch (SQLException e) {
            e.printStackTrace();
            llamarAlerta("Error al solicitar los usuarios.");
        }
    }

    public void configurarTablas() {

        columnaUsuarioListado.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
        columnaEstadoUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("estado"));

        columnaIDlistarPizarra.setCellValueFactory(new PropertyValueFactory<Oferta,String>("idOferta"));
        columnaUsuarioListarPizarra.setCellValueFactory(new PropertyValueFactory<Oferta,String>("usuario"));
        columnaTipoListarPizarra.setCellValueFactory(new PropertyValueFactory<Oferta,String>("tipo"));
        columnaMonedaListarPizarra.setCellValueFactory(new PropertyValueFactory<Oferta,String>("moneda"));
        columnaMontoListarPizarra.setCellValueFactory(new PropertyValueFactory<Oferta,String>("monto"));
        columnaTipoCambioListarPizarra.setCellValueFactory(new PropertyValueFactory<Oferta,String>("tipoCambio"));

        columUsuario1VisualizarTransacciones.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("usuario1"));
        columUsuario2VisualizarTransacciones.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("usuario2"));
        columnMontoVisualizarTransacciones.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("monto"));
        columnTipoCambioVisualizarTransacciones.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("tipoCambio"));
        columnTransaccionVisualizarTransacciones.setCellValueFactory(new PropertyValueFactory<Transaccion,String>("transaccion"));
    }

    public void llenarComboboxIDsesiones() {
        try {
            String adquirirIdSesiones = "{call adquirirIdSesiones()}";
            CallableStatement procedimientoAdquirirIDsesiones = connection.prepareCall(adquirirIdSesiones);
            procedimientoAdquirirIDsesiones.execute();
            ResultSet tuplesIDsesiones = procedimientoAdquirirIDsesiones.getResultSet();
            ArrayList<String> idSesiones = new ArrayList<>();
            while (tuplesIDsesiones.next()) {
                String idSesion = String.valueOf(tuplesIDsesiones.getInt("IDSESION"));
                idSesiones.add(idSesion);
            }
            ObservableList<String> listaSesiones = FXCollections.observableArrayList(idSesiones);
            cajaSesionVisualizarTransacciones.setItems(listaSesiones);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String[] ultimoEstadoSesion(){
        String estadoSesion = "";
        String idSesion="";
        String [] infoSesion = new String [2];
        try{
            String ultimoEstado = "{call estadoUltimaSesion(?,?)}";
            CallableStatement procedimientoEstado = connection.prepareCall(ultimoEstado);
            procedimientoEstado.registerOutParameter(1, Types.INTEGER);
            procedimientoEstado.registerOutParameter(2, Types.VARCHAR);
            procedimientoEstado.execute();
            idSesion = String.valueOf(procedimientoEstado.getInt(1));
            estadoSesion = procedimientoEstado.getString(2);

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        infoSesion[0] = idSesion;
        infoSesion[1]= estadoSesion;

        return infoSesion;

    }
    public void listarPizarra(){
        int idSesion = Integer.parseInt(ultimoEstadoSesion()[0]);
        String estadoSesion = ultimoEstadoSesion()[1];
        ArrayList<Oferta> ofertas = new ArrayList();

        if(estadoSesion.equals("ABIERTO")){
            try {
                String adquireOferta = "{call adquirirOfertas(?)}";
                CallableStatement procedimientoAdquirirOfertas = connection.prepareCall(adquireOferta);
                procedimientoAdquirirOfertas.setInt(1, idSesion);
                procedimientoAdquirirOfertas.execute();
                ResultSet tuplesOfertas = procedimientoAdquirirOfertas.getResultSet();
                String filtro;
                try{
                    filtro =cajaTipoOrdenamiento.getSelectionModel().getSelectedItem().toString();
                }catch(Exception e){
                    filtro = "Ambos";
                }
                while (tuplesOfertas.next()) {
                    String idOferta = String.valueOf(tuplesOfertas.getInt("ID"));
                    String tipoOferta = tuplesOfertas.getString("TIPOOFERTA");
                    String monto = String.valueOf(tuplesOfertas.getBigDecimal("MONTO"));
                    String tipoCambio = String.valueOf(tuplesOfertas.getBigDecimal("TIPOCAMBIO"));
                    String moneda = tuplesOfertas.getString("MONEDA");
                    String participante = tuplesOfertas.getString("CEDULAPARTICIPANTE");
                    if(filtro.equals("Ambos")||filtro.equals(tipoOferta))
                        ofertas.add(new Oferta(idOferta, participante, tipoOferta, monto, moneda, tipoCambio));
                }

                ObservableList<Oferta> listaOfertas = FXCollections.observableArrayList(ofertas);
                pizarraAdministrador.setItems(listaOfertas);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void listarUltimasTransacciones(){

        int idSesion = Integer.parseInt(cajaSesionVisualizarTransacciones.getSelectionModel().getSelectedItem().toString());
            try{
                ArrayList<Transaccion> transacciones= new ArrayList<>();
                Double totalCompraDolaresVar = 0.0;
                Double totalVentaDolaresVar = 0.0;
                Double promedioTipoCambioVar = 0.0;
                int contador = 0;
                String extraerTransacciones = "{call extraerUltimasTransacciones(?)}";
                CallableStatement procedimientoTransaccion = connection.prepareCall(extraerTransacciones);
                procedimientoTransaccion.setInt(1,idSesion);
                procedimientoTransaccion.execute();
                ResultSet tuplesTransaccion = procedimientoTransaccion.getResultSet();

                while(tuplesTransaccion.next()){
                    String usuario1 = tuplesTransaccion.getString("CEDULAUSUARIO1");
                    String transaccion = tuplesTransaccion.getString("ACCION");
                    String monto = String.valueOf(tuplesTransaccion.getBigDecimal("MONTO"));
                    String tipoCambio = String.valueOf(tuplesTransaccion.getBigDecimal("TIPOCAMBIO"));
                    String usuario2 = tuplesTransaccion.getString("CEDULAUSUARIO2");
                    transacciones.add(new Transaccion(usuario1,transaccion,monto,tipoCambio,usuario2));
                    promedioTipoCambioVar+= Double.parseDouble(tipoCambio);
                    contador++;
                    if(transaccion.equals("COMPRA"))
                    totalCompraDolaresVar+= Double.parseDouble(monto);
                    else
                    totalVentaDolaresVar += Double.parseDouble(monto);
                }
                promedioTipoCambioVar = promedioTipoCambioVar/contador;

                totalCompraDolares.setText(totalCompraDolaresVar.toString());
                totalVentaDolares.setText(totalVentaDolaresVar.toString());
                promedioTipoCambio.setText(promedioTipoCambioVar.toString());
                ObservableList<Transaccion> listaTransacciones = FXCollections.observableArrayList(transacciones);
                int contador1 = 0;

                for(int i = 0;i<listaTransacciones.size();i++){
                    if(contador1==2) {
                        tablaVisualizarTransaccionesC.getItems().add(i, new Oferta("", "", "", "", "", ""));
                        contador1=0;
                        tablaVisualizarTransaccionesC.getItems().add(i+1,listaTransacciones.get(i));
                        i=i+1;
                    }
                    tablaVisualizarTransaccionesC.getItems().add(i, listaTransacciones.get(i));
                    contador1++;

                }

               // tablaVisualizarTransaccionesC.setItems(listaTransacciones);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
    }
}