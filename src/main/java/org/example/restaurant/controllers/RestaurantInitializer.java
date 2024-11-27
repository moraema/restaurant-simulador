package org.example.restaurant.controllers;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import org.example.restaurant.domain.entities.Comensal;
import org.example.restaurant.domain.entities.Mesa;
import org.example.restaurant.domain.entities.Orden;
import org.example.restaurant.domain.logic.Buffer;
import org.example.restaurant.domain.logic.BufferComidas;
import org.example.restaurant.domain.logic.Recepcionista;
import org.example.restaurant.threads.CocineroThread;
import org.example.restaurant.threads.ComensalThread;
import org.example.restaurant.threads.MeseroThread;

import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.List;

public class RestaurantInitializer {

    private List<Mesa> mesas;
    private Buffer<Orden> bufferOrdenes;
    private BufferComidas<Orden> bufferComidas;
    private Recepcionista recepcionista;
    private List<MeseroThread> meseros;
    private List<CocineroThread> cocineros;

    public RestaurantInitializer() {
        initRestaurantSimulation();
    }

    private void initRestaurantSimulation() {
        int capacidadRestaurante = 6;
        int numMeseros = 1;
        int numCocineros = 1;

        mesas = new ArrayList<>();
        for (int i = 0; i < capacidadRestaurante; i++) {

            mesas.add(new Mesa());
        }

        recepcionista = new Recepcionista(mesas);
        bufferOrdenes = new Buffer<>();
        bufferComidas = new BufferComidas<>();

        meseros = new ArrayList<>();
        for (int i = 0; i < numMeseros; i++) {
            MeseroThread meseroThread = new MeseroThread(bufferComidas, bufferOrdenes);
            meseroThread.start();
            meseros.add(meseroThread);
        }

        cocineros = new ArrayList<>();
        for (int i = 0; i < numCocineros; i++) {
            CocineroThread cocineroThread = new CocineroThread(bufferOrdenes, bufferComidas);
            cocineroThread.start();
            cocineros.add(cocineroThread);
        }

    }

    public void crearComensal(Entity client) {
        int clienteId = client.getInt("id");
        Entity clienteVisual = client;


        Comensal comensal = new Comensal(clienteId, clienteVisual);
        ComensalThread comensalThread = new ComensalThread(comensal, recepcionista);
        comensalThread.start();

        Orden orden = new Orden(comensal.getId());
        bufferOrdenes.agregar(orden);

    }

    public List<MeseroThread> getWaiters(){
        return meseros;
    }


}
