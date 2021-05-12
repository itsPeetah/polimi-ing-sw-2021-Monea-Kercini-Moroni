package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.scenes.GUIMainMenu;
import it.polimi.ingsw.application.gui.scenes.GUIStartup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIStage extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        stage.setTitle("Masters of Renaissance");
        setScene(GUIMainMenu.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }
    
}
