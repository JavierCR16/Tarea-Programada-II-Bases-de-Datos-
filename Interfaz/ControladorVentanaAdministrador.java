package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Javier on 5/16/2017.
 */
public class ControladorVentanaAdministrador implements Initializable {

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

    }


}
