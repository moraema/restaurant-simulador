package org.example.restaurant.domain.logic;

import java.util.ArrayList;
import java.util.List;

public class Buffer<T> {
    private final List<T> buffer = new ArrayList<>();


    public synchronized void agregar(T item) {
        buffer.add(item);
        System.out.println("Se agregó un elemento " + item);
        notifyAll();  // Notifica a los hilos esperando que el buffer ha cambiado
    }


    public synchronized T retirar() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();  // Espera hasta que haya algo que retirar
        }
        T item = buffer.remove(0); // Elimina el primer item de la lista
        System.out.println("Se eliminó un elemento " + item);
        return item;
    }


    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }
}

