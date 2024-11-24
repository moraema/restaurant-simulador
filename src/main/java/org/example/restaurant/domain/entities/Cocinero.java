package org.example.restaurant.domain.entities;

public class Cocinero {
    private final int id;

    public Cocinero(int id) {
        this.id = id;
    }

    public void cocinarOrden(Orden orden)  throws  InterruptedException {
        System.out.println("Cocinero" + id + "Cocinando orden" + orden.getId());
        Thread.sleep(3000);
        orden.setEstado("LISTO");
        System.out.println("Cocinero" + id + "completó la orden" + orden.getId());
    }
}
