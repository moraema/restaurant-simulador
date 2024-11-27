package org.example.restaurant.domain.logic;


import java.util.ArrayList;
import java.util.List;

public class BufferComidas<T>  {
    private final List<T> buffer = new ArrayList<>();

    public synchronized void agregar(T item) {
        buffer.add(item);
        System.out.println("Se agregó una comida: " + item);
        notifyAll();
    }

    public synchronized T retirar() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        T item = buffer.remove(0);
        System.out.println("Se retiró una comida: " + item);
        return item;
    }

    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }


}
