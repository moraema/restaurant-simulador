package org.example.restaurant.domain.entities;

public class Mesa {
    private boolean ocupado;

    public synchronized void ocuparMesa() throws InterruptedException {
        while (ocupado) {
            wait();
        }
        ocupado = true;
        System.out.println("Mesa ocupada");
    }

    public synchronized void removerMesa() throws InterruptedException {
        ocupado = false;
        notifyAll();
        System.out.println("Mesa liberada");
    }

    public boolean isOcupado() {
        return ocupado;
    }
}

