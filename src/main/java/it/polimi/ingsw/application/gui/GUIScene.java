package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.listeners.PacketListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public enum GUIScene {
    MAIN_MENU("GUIMainMenu.fxml", true),
    GAME_MODE_SELECTION("GUIGameModeSelection.fxml", true),
    MP_SELECTION("GUIMPSelection.fxml", true),
    MP_ROOM("GUIMPRoom.fxml", true),
    PRE_GAME("GUIPreGame.fxml", true),
    MAIN_GAME("GUIMainGame.fxml", true),
    END_GAME("GUIEndGame.fxml", true),
    SETTINGS("GUISettings.fxml", true),
    CONN_SETTINGS("GUIConnSettings.fxml", true),
    CHOOSE_RESOURCE("GUIChooseResource.fxml", false),
    WAREHOUSE("GUIWarehouse.fxml", false),
    LOADING("GUILoading.fxml", false);

    /* FXML ATTRIBUTES */
    private static final String FXML_DIRECTORY = "/scenes/";
    private final String fxmlPath;

    /* SCENE ATTRIBUTES */
    private FXMLLoader fxmlLoader = null;
    private Parent root = null;
    private final boolean loadOnStarting;

    /* ACTIVE SCENE */
    private static PacketListener activeScene;
    private static Parent nextLoadingRoot;

    /* STATIC SCENE */
    private static Scene scene;

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
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startCallbacks();
        scene.setRoot(root);
    }

    public static Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }

    public void startCallbacks() {
        if(fxmlLoader.getController() instanceof PacketListener) {
            activeScene = fxmlLoader.getController();
        }
        if(fxmlLoader.getController() instanceof GUIObserverScene) {
            new Thread(((GUIObserverScene)fxmlLoader.getController())::startObserver).start();
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

    public static void init() {
        scene = new Scene(new Pane());
        for(GUIScene guiScene: GUIScene.values()) {
            if(guiScene.loadOnStarting) {
                try {
                    guiScene.fxmlLoader = new FXMLLoader(guiScene.getClass().getResource(guiScene.fxmlPath));
                    guiScene.root = guiScene.fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(guiScene == LOADING) {
                try {
                    nextLoadingRoot = new FXMLLoader(LOADING.getClass().getResource(LOADING.fxmlPath)).load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showLoadingScene() {
        scene.setRoot(nextLoadingRoot);
        try {
            nextLoadingRoot = new FXMLLoader(LOADING.getClass().getResource(LOADING.fxmlPath)).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
