package org.example.restaurant.domain.entities;

import org.example.restaurant.domain.logic.Recepcionista;

import javax.swing.text.html.parser.Entity;

public class Comensal {
    private final int id;
    private Mesa mesaAsignada;
    private boolean atendido;
    private Recepcionista recepcionista;
    private Entity entidadVisual;
    private Entity comidaEntity;
    private Mesa mesa;

    public Comensal(int id, Entity entidadVisual) {
        this.id = id;
        this.atendido = false;
        this.entidadVisual = entidadVisual;
    }

    public int getId() {
        return id;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public Entity getEntidadVisual() {
        return entidadVisual;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Entity getComidaEntity() {
        return comidaEntity;
    }

    public void setcomidaEntity(Entity comidaEntity) {
        this.comidaEntity = comidaEntity;
    }

}
