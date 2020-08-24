package com.mayatech.gesta.Models;

public class RecuperarComentario {

    String fecha;
    String ultimoEstado;
    String usuario;
    String comentario;

    public RecuperarComentario() {
    }

    public RecuperarComentario(String fecha, String ultimoEstado, String usuario, String comentario) {
        this.fecha = fecha;
        this.ultimoEstado = ultimoEstado;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUltimoEstado() {
        return ultimoEstado;
    }

    public void setUltimoEstado(String ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
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

    @Override
    public String toString() {
        return  "Fecha: " + fecha + "\n" +
                "Ultimo Estado: " + ultimoEstado + "\n" +
                "Usuario: " + usuario + "\n" +
                "Comentario: " + comentario + "\n";
    }
}
