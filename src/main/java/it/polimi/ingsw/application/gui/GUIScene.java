package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.listeners.MessageListener;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.scenes.GUISettings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public enum GUIScene {
    MAIN_MENU("GUIMainMenu.fxml"),
    GAME_MODE_SELECTION("GUIGameModeSelection.fxml"),
    MP_SELECTION("GUIMPSelection.fxml"),
    MP_ROOM("GUIMPRoom.fxml"),
    PRE_GAME("GUIPreGame.fxml"),
    SETTINGS("GUISettings.fxml"),
    CONN_SETTINGS("GUIConnSettings.fxml");

    /* FXML ATTRIBUTES */
    private static final String FXML_DIRECTORY = "/scenes/";
    private final String fxmlPath;

    /* ACTIVE SCENE */
    private static PacketListener activeScene;

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

    public Scene produceScene() {
        Scene loadedScene = null;
        try {
            Parent loadedSceneView = new FXMLLoader(getClass().getResource(fxmlPath)).load();
            loadedScene = new Scene(loadedSceneView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedScene;
    }

    public static PacketListener getActiveScene() {
        return activeScene;
    }

    public static void setPacketListener(PacketListener activeScene) {
        GUIScene.activeScene = activeScene;
    }
}
