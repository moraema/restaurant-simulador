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


    private static final List<int[]> MESA_POSICIONES = List.of(
            new int[]{160, 180},
            new int[]{390, 180},
            new int[]{600, 180},
            new int[]{160, 390},
            new int[]{390, 390},
            new int[]{600, 390}
    );









    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

