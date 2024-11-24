package org.example.restaurant.domain.entities;

public class Mesero {
    private final int id;

    public Mesero(int id) {
        this.id = id;
    }

    public Orden tomarOrden(Comensal comensal) {
        System.out.println("Mesero" + id + "atendiendo al comensal" + comensal.getId());
        return new Orden(comensal.getId());
    }

}