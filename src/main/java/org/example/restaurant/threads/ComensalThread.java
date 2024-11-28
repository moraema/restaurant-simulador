package org.example.restaurant.threads;

import com.almasb.fxgl.core.util.Platform;
import com.almasb.fxgl.dsl.FXGL;
import org.example.restaurant.domain.entities.Comensal;
import org.example.restaurant.domain.entities.Mesa;
import org.example.restaurant.domain.logic.ComensalRegistry;
import org.example.restaurant.domain.logic.Recepcionista;

public class ComensalThread extends Thread {
    private final Comensal comensal;
    private final Recepcionista recepcionista;

    public ComensalThread(Comensal comensal, Recepcionista recepcionista) {
        this.comensal = comensal;
        this.recepcionista = recepcionista;
    }

    @Override
    public void run() {
        try {


            ComensalRegistry.registrarComensal(comensal);

            Mesa mesa = recepcionista.asignarMesa(comensal);
            System.out.println("El comensal " + comensal.getId() + "se le asigno la mesa");

            synchronized (comensal) {
                while (!comensal.isAtendido()) {
                    System.out.println("Comensal " + comensal.getId() + " esperando comida");
                    comensal.wait();
                }
            }

            System.out.println("El comensal " + comensal.getId() + " a empezado a comer");
           Thread.sleep((long) (Math.random() * 5000 + 3000));
            System.out.println("el comensal " + comensal.getId() + " a termiando de comer");
            recepcionista.liberarMesa(mesa);
            System.out.println("Comensal " + comensal.getId() + " ha salido del restaurante");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ComensalRegistry.removerComensal(comensal.getId());
        }
    }
}
