package com.mayatech.gesta.Models;

public class Cuenta {

    String cuenta;
    String cliente;
    String estado;

    public Cuenta(){
    }

    public Cuenta(String cuenta, String cliente, String estado) {
        this.cuenta = cuenta;
        this.cliente = cliente;
        this.estado = estado;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "cuenta='" + cuenta + '\'' +
                ", cliente='" + cliente + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
