package org.example.restaurant.controllers;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import com.almasb.fxgl.entity.Entity;
import org.example.restaurant.factory.Factory;
import org.example.restaurant.threads.MeseroThread;

import java.net.URL;
import java.util.*;


public class Controllers implements Initializable {
    private static RestaurantInitializer restaurantInitializer;

    private static final List<int[]> MESA_POSICIONES = List.of(
            new int[]{160, 180},
            new int[]{390, 180},
            new int[]{600, 180},
            new int[]{160, 390},
            new int[]{390, 390},
            new int[]{600, 390}
    );



    public static void initGame() {
        restaurantInitializer = new RestaurantInitializer();
        FXGL.getGameWorld().addEntityFactory(new Factory());
        mostrarCocinero();
        FXGL.getGameTimer().runAtInterval(Controllers::spawnCliente, Duration.seconds(3));

    }


    private static void mostrarCocinero() {
        SpawnData data = new SpawnData(650, 0);
        Entity cocinero = FXGL.spawn("Cocinero", data);

        FXGL.getGameWorld().addEntity(cocinero);
    }



    private static int clientIdCounter = 0;

    private static void spawnCliente() {
        int id = ++clientIdCounter;
        int mesaIndex = (id - 1) % MESA_POSICIONES.size();
        int[] mesaPosition = MESA_POSICIONES.get(mesaIndex);

        SpawnData data = new SpawnData(mesaPosition[0], mesaPosition[1]).put("id", id);
        Entity client = FXGL.spawn("Cliente", data);

        restaurantInitializer.crearComensal(client);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initGame();
    }


}