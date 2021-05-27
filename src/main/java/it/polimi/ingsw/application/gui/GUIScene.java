package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.listeners.PacketListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public enum GUIScene {
    MAIN_MENU("GUIMainMenu.fxml", true),
    GAME_MODE_SELECTION("GUIGameModeSelection.fxml", true),
    MP_SELECTION("GUIMPSelection.fxml", false),
    MP_ROOM("GUIMPRoom.fxml", false),
    PRE_GAME("GUIPreGame.fxml", false),
    MAIN_GAME("GUIMainGame.fxml", false),
    SETTINGS("GUISettings.fxml", true),
    CONN_SETTINGS("GUIConnSettings.fxml", true),
    CHOOSE_RESOURCE("GUIChooseResource.fxml", false),
    WAREHOUSE("GUIWarehouse.fxml", false);

    /* FXML ATTRIBUTES */
    private static final String FXML_DIRECTORY = "/scenes/";
    private final String fxmlPath;

    /* SCENE ATTRIBUTES */
    private FXMLLoader fxmlLoader = null;
    private Scene scene = null;
    private final boolean loadOnStarting;

    /* ACTIVE SCENE */
    private static PacketListener activeScene;

    /**
     * Create a new GUIScene.
     * @param fxmlPath name of the fxml file.
     * @param loadOnStarting whether the scene can be loaded on the application starting (for example, for GUI scenes that are not strongly related with the game itself, to speed up their loading.
     */
    GUIScene(String fxmlPath, boolean loadOnStarting) {
        this.fxmlPath = FXML_DIRECTORY + fxmlPath;
        this.loadOnStarting = loadOnStarting;
    }

    public void load() {
        if(fxmlLoader == null) {
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource(this.fxmlPath));
                Parent sceneParent = fxmlLoader.load();
                scene = new Scene(sceneParent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(fxmlLoader.getController() instanceof PacketListener) {
            activeScene = fxmlLoader.getController();
            System.out.println("GUIScene.load: changed active scene");
        }
        GUIApplication.setScene(scene);
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
        System.out.println("GUIScene.getActiveScene. Active scene: " + activeScene);
        return activeScene;
    }

    public static void setPacketListener(PacketListener activeScene) {
        GUIScene.activeScene = activeScene;
    }

    public static void init() {
        for(GUIScene guiScene: GUIScene.values()) {
            if(guiScene.loadOnStarting) {
                try {
                    guiScene.fxmlLoader = new FXMLLoader(guiScene.getClass().getResource(guiScene.fxmlPath));
                    Parent sceneParent = guiScene.fxmlLoader.load();
                    guiScene.scene = new Scene(sceneParent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
