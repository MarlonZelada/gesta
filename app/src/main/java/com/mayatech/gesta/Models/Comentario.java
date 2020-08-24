package com.mayatech.gesta.Models;

public class Comentario {

    String fecha;
    String usuario;
    String comentario;
    String ultimoEstado;
    String cuenta;

    public Comentario(){
    }

    public Comentario(String fecha, String usuario, String comentario, String ultimoEstado, String cuenta) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.comentario = comentario;
        this.ultimoEstado = ultimoEstado;
        this.cuenta = cuenta;
    }

    public Comentario(String fecha, String usuario, String comentario, String ultimoEstado) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.comentario = comentario;
        this.ultimoEstado = ultimoEstado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUltimoEstado() {
        return ultimoEstado;
    }

    public void setUltimoEstado(String ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
    }

    public String getCuenta() { return cuenta; }

    public void setCuenta(String cuenta) { this.cuenta = cuenta; }

    @Override
    public String toString() {
        return "Comentario{" +
                "fecha='" + fecha + '\'' +
                ", usuario='" + usuario + '\'' +
                ", comentario='" + comentario + '\'' +
                ", ultimoEstado='" + ultimoEstado + '\'' +
                ", cuenta='" + cuenta + '\'' +
                '}';
    }
}
