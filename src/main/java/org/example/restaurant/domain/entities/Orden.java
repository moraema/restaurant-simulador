package org.example.restaurant.domain.entities;

public class Orden {
    private final int idOrden;
    private String estado;

    public Orden(int id) {
        this.idOrden = id;
        this.estado = "EN PROCESO";
    }

    public int getId() {
        return idOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
