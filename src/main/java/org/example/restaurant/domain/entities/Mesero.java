package org.example.restaurant.domain.entities;

import org.example.restaurant.domain.entities.Comensal;
import org.example.restaurant.domain.entities.Orden;
import org.example.restaurant.domain.logic.Buffer;

public class Mesero {
    private final int id;
    private boolean ocupado;
    private final Buffer<Orden> bufferOrdenes;

    public Mesero(int id, Buffer<Orden> bufferOrdenes) {
        this.id = id;
        this.ocupado = false;
        this.bufferOrdenes = bufferOrdenes;
    }

    public int getId() {
        return id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public synchronized void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void tomarOrden(Comensal comensal) {
        Orden nuevaOrden = new Orden(comensal.getId());
        synchronized (bufferOrdenes) {
            bufferOrdenes.agregar(nuevaOrden);
            System.out.println("Mesero " + id + " agregó una nueva orden " + nuevaOrden.getId());
            bufferOrdenes.notifyAll();
        }
    }
}
