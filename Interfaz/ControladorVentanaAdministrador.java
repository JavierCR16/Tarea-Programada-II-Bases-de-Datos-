package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
    Button botonSesionNegociaciones;

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
    TextField contrasenaCrearAgente;

    @FXML
    TextField confirmacionCrearAgente;

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




    public void initialize(URL fxmlLocations, ResourceBundle resources){
        for(int i = 68;i>0;i--){
            cajaAno.getItems().add(68-i,(i+1950)%100);
        }
        for(int i = 1;i<13;i++){
            cajaMes.getItems().add(i-1,i);
            cajaPorcentajeComision.getItems().add(i-1,i);
        }
        for(int i = 1;i<32;i++){
            cajaDia.getItems().add(i-1,i);
        }

        botonSesionNegociaciones.setOnAction(event -> {
            if(botonSesionNegociaciones.getText().equals("Abrir Sesión")){
                botonSesionNegociaciones.setText("Cerrar Sesión");
            }
            else{
                botonSesionNegociaciones.setText("Abrir Sesión");
            }
        });
        botonSuspenderUsuario.setOnAction(event -> {
            cuadroUsuarioASuspender.clear();
        });
        botonCerrarSesionAdmi.setOnAction(event -> {
            Stage escenarioActual = (Stage)botonCerrarSesionAdmi.getScene().getWindow();
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

        });

    }
    public void crearUsuarioAgente(){
        String cedulaParticipante = cedulaCrearAgente.getText();
        String nombreParticipante = nombreCrearAgente.getText();
        String telefonoParticipante = telefonoCrearAgente.getText();
        String correoParticipante = correoCrearAgente.getText();
        String palabraClave = contrasenaCrearAgente.getText();
        String confirmacionClave = confirmacionCrearAgente.getText();

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
    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
    }



}
