package Interfaz;

import Auxiliares.Oferta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
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
    TableColumn columnaIdOferta;

    @FXML
    TableColumn columnaUsuario;

    @FXML
    TableColumn columnaTipo;

    @FXML
    TableColumn columnaMoneda;

    @FXML
    TableColumn columnaTipoCambio;

    @FXML
    TableColumn columnaMonto;

    @FXML
    Button botonConsultarPizarraNegociaciones;

    @FXML
    TableView tablaUltimasTransaccionesC;

    @FXML
    Button botonActualizarUltimasTransConc;

    @FXML
    Button botonCerrarSesion;

    String participanteActual;

    int sesionActual;

    public Connection connection;
    public Statement statement;

    public void initialize(URL fxmlLocations, ResourceBundle resources){

        configurarTablas();

        cajaTipoMoneda.getItems().addAll("Dolares","Colones");
        cajaTipoOferta.getItems().addAll("Compra","Venta");

        botonCerrarSesion.setOnAction(event -> {
            Stage escenarioActual = (Stage)botonCerrarSesion.getScene().getWindow();
            escenarioActual.close();
        });

        botonConsultarPizarraNegociaciones.setOnAction(event -> {
            listarOfertas();
        });

        botonEnviarOferta.setOnAction(event -> {
            enviarOferta();
            montoOferta.clear();
            tipoCambioOferta.clear();
            cajaTipoOferta.getSelectionModel().clearSelection();
            cajaTipoMoneda.getSelectionModel().clearSelection();
        });
    }

    public void enviarOferta(){


        Object tipoMoneda = cajaTipoMoneda.getSelectionModel().getSelectedItem();
        Object tipoOferta = cajaTipoOferta.getSelectionModel().getSelectedItem();
        String tipoCambio = tipoCambioOferta.getText();
        String monto = montoOferta.getText();

        if(tipoMoneda==null|| tipoOferta==null || tipoCambio.equals("")||monto.equals(""))
            llamarAlerta("Debe ingresar todos los datos antes de realizar una oferta");
        else{
            try{

                int idSesionActual = Integer.parseInt(ultimoEstadoSesion()[0]);
                String estadoSesion = ultimoEstadoSesion()[1];
                BigDecimal porcentajeComisionActual = adquirirPorcentaje();
                System.out.println(porcentajeComisionActual);
                if(!estadoSesion.equals("ABIERTO") || idSesionActual!=sesionActual) {
                    llamarAlerta("Se ha cerrado la sesión actual. Se le devolverá a la pantalla principal");
                    Stage escenario = (Stage) botonEnviarOferta.getScene().getWindow();
                    escenario.close();
                }
                else{
                    String enviarOferta = "{call agregarOferta(?,?,?,?,?,?,?)}";
                    CallableStatement procedimientoOferta = connection.prepareCall(enviarOferta);
                    procedimientoOferta.setInt(1,idSesionActual);
                    procedimientoOferta.setString(2,tipoOferta.toString());
                    procedimientoOferta.setBigDecimal(3,new BigDecimal(monto));
                    procedimientoOferta.setBigDecimal(4,new BigDecimal(tipoCambio));
                    procedimientoOferta.setString(5,tipoMoneda.toString());
                    procedimientoOferta.setString(6,participanteActual);
                    procedimientoOferta.setBigDecimal(7,porcentajeComisionActual);
                    procedimientoOferta.executeUpdate();

                }

            }
            catch(SQLException e){
                e.printStackTrace();
                llamarAlerta("No tiene los fondos suficientes para realizar dicha oferta. Intente de nuevo.");
            }


        }

    }



  /*  public Oferta ultimaOferta(){
        String id = "";
        String usuario = "";
        String tipo = "";
        String monto = "";
        String moneda = "";
        String tipoCambio = "";
        try{
            String extraerOferta = "{call ultimaOferta()}";
            CallableStatement procOferta = connection.prepareCall(extraerOferta);
            procOferta.execute();
            ResultSet tupleOferta = procOferta.getResultSet();

            while(tupleOferta.next()){
                id = String.valueOf(tupleOferta.getInt("ID"));
                usuario = tupleOferta.getString("CEDULAPARTICIPANTE");
                tipo= tupleOferta.getString("TIPOOFERTA");
                monto = String.valueOf(tupleOferta.getBigDecimal("MONTO"));
                moneda = tupleOferta.getString("MONEDA");
                tipoCambio = String.valueOf(tupleOferta.getBigDecimal("TIPOCAMBIO"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return new Oferta(id,usuario,tipo,monto,moneda,tipoCambio);
    }*/

    public void configurarTablas(){
        columnaIdOferta.setCellValueFactory(new PropertyValueFactory<Oferta,String>("idOferta"));
        columnaUsuario.setCellValueFactory(new PropertyValueFactory<Oferta,String>("usuario"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<Oferta,String>("tipo"));
        columnaMoneda.setCellValueFactory(new PropertyValueFactory<Oferta,String>("moneda"));
        columnaMonto.setCellValueFactory(new PropertyValueFactory<Oferta,String>("monto"));
        columnaTipoCambio.setCellValueFactory(new PropertyValueFactory<Oferta,String>("tipoCambio"));
    }

    public void llamarAlerta(String error){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText(error);
        alerta.showAndWait();
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

    public BigDecimal adquirirPorcentaje(){
        BigDecimal porcentaje = null;
        try{
            String adquirirPorcentaje = "{call adquirirPorcentajeComision(?)}";
            CallableStatement procedimientoPorcentaje = connection.prepareCall(adquirirPorcentaje);
            procedimientoPorcentaje.registerOutParameter(1,Types.DECIMAL);
            procedimientoPorcentaje.executeUpdate();
            porcentaje = procedimientoPorcentaje.getBigDecimal(1);
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        return porcentaje;
    }

    public void listarOfertas(){
        int idSesion = Integer.parseInt(ultimoEstadoSesion()[0]);
        String estadoSesion = ultimoEstadoSesion()[1];
        ArrayList<Oferta> ofertas = new ArrayList();

        if(!estadoSesion.equals("ABIERTO")|| idSesion!=sesionActual){
            llamarAlerta("Se ha cerrado la sesión actual. Se le devolverá a la pantalla principal");
            Stage escenario = (Stage) botonEnviarOferta.getScene().getWindow();
            escenario.close();
        }
        else {

            try {
                String adquireOferta = "{call adquirirOfertas(?)}";
                CallableStatement procedimientoAdquirirOfertas = connection.prepareCall(adquireOferta);
                procedimientoAdquirirOfertas.setInt(1, idSesion);
                procedimientoAdquirirOfertas.execute();
                ResultSet tuplesOfertas = procedimientoAdquirirOfertas.getResultSet();

                while (tuplesOfertas.next()) {
                    String idOferta = String.valueOf(tuplesOfertas.getInt("ID"));
                    String tipoOferta = tuplesOfertas.getString("TIPOOFERTA");
                    String monto = String.valueOf(tuplesOfertas.getBigDecimal("MONTO"));
                    String tipoCambio = String.valueOf(tuplesOfertas.getBigDecimal("TIPOCAMBIO"));
                    String moneda = tuplesOfertas.getString("MONEDA");
                    String participante = tuplesOfertas.getString("CEDULAPARTICIPANTE");
                    ofertas.add(new Oferta(idOferta, participante, tipoOferta, monto, moneda, tipoCambio));

                }

                ObservableList<Oferta> listaOfertas = FXCollections.observableArrayList(ofertas);
                tablaPizarraNegociaciones.setItems(listaOfertas);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSesionActual(){
        sesionActual = Integer.parseInt(ultimoEstadoSesion()[0]);
    }

}
