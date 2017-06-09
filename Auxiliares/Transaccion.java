package Auxiliares;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Javier on 6/8/2017.
 */
public class Transaccion {


    SimpleStringProperty usuario1;
    SimpleStringProperty transaccion;
    SimpleStringProperty monto;
    SimpleStringProperty tipoCambio;
    SimpleStringProperty usuario2;

    public Transaccion(String pUsuario, String pTransaccion, String pMonto, String pTipoCambio, String pUsuario2){
        usuario1= new SimpleStringProperty(pUsuario);
        transaccion = new SimpleStringProperty(pTransaccion);
        monto = new SimpleStringProperty(pMonto);
        tipoCambio = new SimpleStringProperty(pTipoCambio);
        usuario2 = new SimpleStringProperty(pUsuario2);
    }

    public String getUsuario1() {
        return usuario1.get();
    }

    public SimpleStringProperty usuario1Property() {
        return usuario1;
    }

    public void setUsuario1(String usuario1) {
        this.usuario1.set(usuario1);
    }

    public String getTransaccion() {
        return transaccion.get();
    }

    public SimpleStringProperty transaccionProperty() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion.set(transaccion);
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

    public String getTipoCambio() {
        return tipoCambio.get();
    }

    public SimpleStringProperty tipoCambioProperty() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio.set(tipoCambio);
    }

    public String getUsuario2() {
        return usuario2.get();
    }

    public SimpleStringProperty usuario2Property() {
        return usuario2;
    }

    public void setUsuario2(String usuario2) {
        this.usuario2.set(usuario2);
    }

}