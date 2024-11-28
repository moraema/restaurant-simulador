package org.example.restaurant.threads;

import org.example.restaurant.domain.entities.Orden;
import org.example.restaurant.domain.logic.Buffer;
import org.example.restaurant.domain.logic.BufferComidas;

public class CocineroThread extends Thread {
    private final Buffer<Orden> bufferOrdenes; // Recibe las órdenes del mesero
    private final BufferComidas<Orden> bufferComidas; // Envía las comidas preparadas al mesero

    public CocineroThread(Buffer<Orden> bufferOrdenes, BufferComidas<Orden> bufferComidas) {
        this.bufferOrdenes = bufferOrdenes;
        this.bufferComidas = bufferComidas;
    }

    @Override
    public void run() {
        try {
            while (true) {

                Orden orden;
                synchronized (bufferOrdenes) {
                    while (bufferOrdenes.isEmpty()) {
                        System.out.println("Cocinero esperando órdenes");
                        bufferOrdenes.wait();
                    }

                    orden = bufferOrdenes.retirar();
                    System.out.println("Cocinero procesando la orden: " + orden.getId());
                }

                System.out.println("Cocinero preparando comida para la orden: " + orden.getId());
                Thread.sleep(2000);

                synchronized (bufferComidas) {
                    bufferComidas.agregar(orden);
                    bufferComidas.notifyAll(); // Notifica al mesero que la comida está lista
                    System.out.println("Cocinero terminó la comida para la orden: " + orden.getId());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
