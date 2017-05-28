package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Juan de Dios on 18/5/2017.
 */
public class ControladorVentanaParticipante implements Initializable {

    @FXML
    ComboBox cajaTipoOferta;

    @FXML
    ComboBox cajaTipoMoneda;

    @FXML
    TextField tipoCambioOferta;

    @FXML
    TextField montoOferta;

    @FXML
    Button botonEnviarOferta;

    @FXML
    TableView tablaPizarraNegociaciones;

    @FXML
    Button botonConsultarPizarraNegociaciones;

    @FXML
    TableView tablaUltimasTransaccionesC;

    @FXML
    Button botonActualizarUltimasTransConc;

    @FXML
    Button botonCerrarSesion;

    public Connection conexion;
    public Statement statement;

    public void initialize(URL fxmlLocations, ResourceBundle resources){

        botonCerrarSesion.setOnAction(event -> {
            Stage escenarioActual = (Stage)botonCerrarSesion.getScene().getWindow();
            escenarioActual.close();
        });

        cajaTipoMoneda.getItems().addAll("Dolares","Colones");
        cajaTipoOferta.getItems().addAll("Compra","Venta");

        botonEnviarOferta.setOnAction(event -> {
            montoOferta.clear();
            tipoCambioOferta.clear();
            cajaTipoOferta.getSelectionModel().clearSelection();
            cajaTipoMoneda.getSelectionModel().clearSelection();
        });
    }
}
