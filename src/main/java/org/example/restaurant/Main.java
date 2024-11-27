package org.example.restaurant;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


public class Main extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(1053);
        settings.setHeight(560);
        settings.setTitle("restaurant");
    }


    @Override
    protected void initGame(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello.fxml"));
            AnchorPane root = loader.load();
            FXGL.getGameScene().addUINode(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getGameScene().setBackgroundRepeat("Restaurante.png");


    }

    public static void main(String[] args) {
        launch(args);
    }
}