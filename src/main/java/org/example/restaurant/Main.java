package org.example.restaurant;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Main extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(1000);
        settings.setHeight(600);
        settings.setTitle("Restaurant-simulador");
    }


    @Override
    protected void initGame() {
        try {
            Image bgImage = FXGL.getAssetLoader().loadImage("Restaurante.png");
            Rectangle bg = new Rectangle(1000, 600);
            bg.setFill(new ImagePattern(bgImage));
            FXGL.getGameScene().addUINode(bg);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la imagen de fondo: " + e.getMessage(), e);
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}