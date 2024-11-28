package org.example.restaurant.domain.logic;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.application.Platform;
import org.example.restaurant.domain.entities.Comensal;
import org.example.restaurant.domain.entities.Mesa;
import org.example.restaurant.domain.entities.Mesero;
import org.example.restaurant.threads.MeseroThread;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Recepcionista {
    private final List<Mesa> mesas;
    private final Queue<Comensal> salaDeEspera = new LinkedList<>();
    private final List<Entity> salaDeEsperaVisual = new ArrayList<>();
    private  final List<Mesero> meseros;
    private static final int SALA_ESPERA = 900;
    private static final int ESPACIOS = 10;
    private static final int INICIO_EN_Y = 400;


    public Recepcionista(List<Mesa> mesas, List<Mesero> meseros) {
        this.mesas = mesas;
        if (meseros == null || meseros.isEmpty()) {
            throw new IllegalArgumentException("La lista de meseros no puede ser nula o vacía");
        }

        this.meseros = meseros;
    }


    public synchronized Mesa asignarMesa(Comensal comensal) throws InterruptedException {
        while (true) {
            for (Mesa mesa : mesas) {
                if (!mesa.isOcupado()) {
                    mesa.ocuparMesa();
                    System.out.println("Mesa ocupada por el comensal " + comensal.getId());
                    mesa.setClienteVisual(comensal.getEntidadVisual());
                    mesa.setComensal(comensal);
                    notificarMeseroParaTomarOrden(comensal);
                    return mesa;
                }
            }

            System.out.println("No hay mesas disponibles para el comensal " + comensal.getId() + ". Lo enviamos a la sala de espera.");
            salaDeEspera.add(comensal);
            mostrarSalaDeEspera();
            wait();
        }
    }


    public synchronized void liberarMesa(Mesa mesa) {
        // Remover la entidad visual
        Entity clienteVisual = mesa.getClienteVisual();
        if (clienteVisual != null) {
            try {
                // se usa platafomr para relizar la funcion en el hilo principal
                Platform.runLater(() -> {
                    try {

                        Comensal comensal = mesa.getComensal();
                        if (comensal != null) {
                            Entity comida = comensal.getComidaEntity();
                            if (comida != null) {
                                // Eliminar la comida
                                FXGL.getGameWorld().removeEntity(comida);
                                System.out.println("Comida eliminada del comensal " + comensal.getId());
                            } else {
                                System.out.println("El comensal " + comensal.getId() + " no tiene comida asignada.");
                            }
                        } else {
                            System.out.println("No hay comensal en la mesa.");
                        }

                        FXGL.getGameWorld().removeEntity(clienteVisual);
                        System.out.println("Cliente visual en la mesa ha sido removido");


                        mesa.removerMesa();  // Liberar la mesa
                        System.out.println("La mesa ha sido liberada.");

                    } catch (IllegalStateException e) {
                        System.out.println("Error al intentar eliminar la entidad visual: " + e.getMessage());
                    }
                });
            } catch (Exception e) {
                System.out.println("Error al intentar eliminar la entidad visual: " + e.getMessage());
            }
        } else {
            System.out.println("No hay cliente en la mesa.");
        }
        System.out.println("El recepcionista liberó una mesa");
        // Verificar si hay comensales esperando en la sala de espera
        if (!salaDeEspera.isEmpty()) {
            Comensal siguienteComensal = salaDeEspera.poll();  // Sacar al siguiente comensal de la sala de espera
            System.out.println("El comensal " + siguienteComensal.getId() + " fue asignado a una mesa.");

        } else {
            notifyAll();
        }
    }


    private void mostrarSalaDeEspera() {
        Platform.runLater(() -> {
            for (Entity entity: salaDeEsperaVisual) {
                FXGL.getGameWorld().removeEntity(entity);
            }
            salaDeEsperaVisual.clear();

            for (int i = 0; i < salaDeEspera.size(); i++) {
                Comensal comensal = ((LinkedList<Comensal>) salaDeEspera).get(i);

                int posicionY = INICIO_EN_Y + (i * ESPACIOS);

                SpawnData data = new SpawnData(SALA_ESPERA, posicionY).put("id", comensal.getId());
                Entity clienteVisual = FXGL.spawn("Cliente", data);

                FXGL.getGameWorld().addEntity(clienteVisual);
            }

        });
    }

    private synchronized void notificarMeseroParaTomarOrden(Comensal comensal) throws InterruptedException {
        while (true) {
            for (Mesero mesero : meseros) {
                if (!mesero.isOcupado()) {
                    mesero.setOcupado(true); // Marcar al mesero como ocupado
                    mesero.tomarOrden(comensal);
                    System.out.println("Mesero " + mesero.getId() + " está tomando la orden del comensal " + comensal.getId());
                    return;
                }
            }

            System.out.println("No hay meseros disponibles, esperando...");
            wait();
        }
    }

    public synchronized void liberarMesero(int meseroId) {
        for (Mesero mesero : meseros) {
            if (mesero.getId() == meseroId) {
                mesero.setOcupado(false);
                System.out.println("Mesero " + meseroId + " ahora está disponible.");
                notifyAll(); // Notifica que hay meseros disponibles
                return;
            }
        }
        System.err.println("Error: No se encontró el mesero con ID " + meseroId);
    }

}
