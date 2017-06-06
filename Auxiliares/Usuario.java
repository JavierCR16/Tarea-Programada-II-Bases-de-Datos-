package Auxiliares;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Javier on 6/5/2017.
 */
public class Usuario {


    SimpleStringProperty usuario;
    SimpleStringProperty estado;

    public Usuario(String pUsuario, String pEstado){

        if(pEstado.equals("NO"))
            pEstado = "Activo";
        else
            pEstado = "Suspendido";
        usuario = new SimpleStringProperty(pUsuario);
        estado = new SimpleStringProperty(pEstado);
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

    public String getEstado() {
        return estado.get();
    }

    public SimpleStringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }





}
