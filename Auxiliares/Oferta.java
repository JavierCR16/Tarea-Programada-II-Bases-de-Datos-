package Auxiliares;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Javier on 6/6/2017.
 */
public class Oferta {

    SimpleStringProperty idOferta;
    SimpleStringProperty usuario;
    SimpleStringProperty tipo;
    SimpleStringProperty monto;
    SimpleStringProperty moneda;
    SimpleStringProperty tipoCambio;


    public Oferta(String pIdOferta, String pUsuario, String pTipo, String pMonto, String pMoneda, String pTipoCambio){
        idOferta = new SimpleStringProperty(pIdOferta);
        usuario = new SimpleStringProperty(pUsuario);
        tipo = new SimpleStringProperty(pTipo);
        monto = new SimpleStringProperty(pMonto);
        moneda = new SimpleStringProperty(pMoneda);
        tipoCambio = new SimpleStringProperty(pTipoCambio);
    }


    public String getIdOferta() {
        return idOferta.get();
    }

    public SimpleStringProperty idOfertaProperty() {
        return idOferta;
    }

    public void setIdOferta(String idOferta) {
        this.idOferta.set(idOferta);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public SimpleStringProperty usuarioProperty() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getTipo() {
        return tipo.get();
    }

    public SimpleStringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public String getMonto() {
        return monto.get();
    }

    public SimpleStringProperty montoProperty() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto.set(monto);
    }

    public String getMoneda() {
        return moneda.get();
    }

    public SimpleStringProperty monedaProperty() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda.set(moneda);
    }

    public String getTipoCambio() {
        return tipoCambio.get();
    }

    public SimpleStringProperty tipoCambioProperty() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio.set(tipoCambio);
    }


}
