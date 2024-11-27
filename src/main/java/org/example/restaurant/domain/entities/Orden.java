package org.example.restaurant.domain.entities;

public class Orden  {
    private final int idOrden;

    public Orden(int id) {
        this.idOrden = id;
    }

    public int getId() {
        return idOrden;
    }

}

