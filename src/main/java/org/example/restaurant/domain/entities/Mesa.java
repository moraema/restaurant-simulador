package org.example.restaurant.domain.entities;


import com.almasb.fxgl.entity.Entity;

public class Mesa {
    private boolean ocupado;
    private Entity clienteVisual;
    private Comensal comensal;

    public Mesa() {
        this.ocupado = false;
        this.clienteVisual = null;
        this.comensal = null;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public synchronized void ocuparMesa() throws InterruptedException {
        while (ocupado) {
            wait();
        }
        ocupado = true;
        System.out.println("Mesa ocupada");
    }

    public synchronized void removerMesa() {
        ocupado = false;
        clienteVisual = null;
        notifyAll();
        System.out.println("Mesa liberada");
    }

    public void setClienteVisual(Entity clienteVisual) {
        this.clienteVisual = clienteVisual;
    }


    public Entity getClienteVisual() {
        return clienteVisual;
    }


    public Comensal getComensal() {
        return comensal;
    }

    public void setComensal(Comensal comensal) {
        this.comensal = comensal;
    }
}


