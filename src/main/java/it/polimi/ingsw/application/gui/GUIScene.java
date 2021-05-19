package it.polimi.ingsw.application.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;

import java.io.IOException;

public enum GUIScene {
    MAIN_MENU("GUIMainMenu.fxml"),
    GAME_MODE_SELECTION("GUIGameModeSelection.fxml"),
    MP_SELECTION("GUIMPSelection.fxml"),
    MP_ROOM("GUIMPRoom.fxml"),
    PRE_GAME("GUIPreGame.fxml");

    private static final String FXML_DIRECTORY = "/scenes/";
    private final String fxmlPath;

    GUIScene(String fxmlPath) {
        this.fxmlPath = FXML_DIRECTORY + fxmlPath;
    }

    public void load() {
        try {
            Parent loadedSceneView = new FXMLLoader(getClass().getResource(fxmlPath)).load();
            Scene loadedScene = new Scene(loadedSceneView);
            GUIApplication.setScene(loadedScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
