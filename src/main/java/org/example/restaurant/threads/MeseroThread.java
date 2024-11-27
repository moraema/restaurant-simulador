package org.example.restaurant.threads;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.application.Platform;
import javafx.util.Duration;
import org.example.restaurant.controllers.Controllers;
import org.example.restaurant.domain.entities.Comensal;
import org.example.restaurant.domain.entities.Orden;
import org.example.restaurant.domain.logic.BufferComidas;
import org.example.restaurant.domain.logic.ComensalRegistry;
import org.example.restaurant.domain.logic.Buffer;

import java.util.List;

public class MeseroThread extends Thread {
    private final BufferComidas<Orden> bufferComidas;
    private final Buffer<Orden> bufferOrdenes; // Buffer para las órdenes que el mesero pasará al cocinero

    public MeseroThread(BufferComidas<Orden> bufferComidas, Buffer<Orden> bufferOrdenes) {
        this.bufferComidas = bufferComidas;
        this.bufferOrdenes = bufferOrdenes;
    }

    @Override
    public void run() {
       try {

          synchronized (bufferOrdenes) {
              while (bufferOrdenes.isEmpty()) {
                  System.out.println("El mesero esta descansando");
                  bufferOrdenes.wait();
              }
         //     bufferOrdenes.retirar();
          //    System.out.println("El mesero toma la orden para entregrselo al cocinero");
          }

          synchronized (bufferOrdenes) {
              Orden nuevaorden = nuevaOrden();
              bufferOrdenes.agregar(nuevaorden);
              System.out.println("El mesero agrego una nueva orden " + nuevaOrden().getId());
              bufferOrdenes.notifyAll();
          }

          synchronized (bufferComidas) {
              while (bufferComidas.isEmpty()) {
                  Orden ordenLista = bufferComidas.retirar();
                  System.out.println("El mesero retirado la comida " + ordenLista.getId());
                  entregarComidaAlCliente(ordenLista);
              }
          }
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }

    public  void mostrarComidaDelComesal(Comensal comensal) {
        SpawnData comida = new SpawnData(comensal.getEntidadVisual().getPosition());
        Entity comidadEntity = FXGL.spawn("Comida", comida);

        comensal.setcomidaEntity(comidadEntity);
        FXGL.getGameWorld().addEntity(comidadEntity);
    }

    // Crear visualmente del mesero en la posición del comensal
    public void mostrarMeseroTemporalmente(Comensal comensal) {
        SpawnData spawnData = new SpawnData(comensal.getEntidadVisual().getPosition());
        Entity meseroEntity = FXGL.spawn("Mesero", spawnData);

        FXGL.getGameWorld().addEntity(meseroEntity);

        FXGL.getGameTimer().runOnceAfter(() -> {
            FXGL.getGameWorld().removeEntity(meseroEntity);
        }, Duration.millis(500));
    }

    private int contadorOrdenes = 0; // control de las ordenes
    // Genera orden con ID
    private Orden nuevaOrden() {
        Orden nuevaOrden = new Orden(contadorOrdenes);
        contadorOrdenes++;
        return nuevaOrden;
    }

    private void entregarComidaAlCliente(Orden orden) {
        Comensal comensal = ComensalRegistry.getComensalById(orden.getId());
        if (comensal != null) {
            synchronized (comensal) {
                comensal.setAtendido(true);
                comensal.notify();
                System.out.println("Mesero entregó la comida al comensal: " + comensal.getId());

                Platform.runLater(() -> {
                    mostrarComidaDelComesal(comensal);
                    mostrarMeseroTemporalmente(comensal);
                });
            }
        } else {
            System.out.println("No se encontró al comensal para la orden: " + orden.getId());
        }
    }

}
