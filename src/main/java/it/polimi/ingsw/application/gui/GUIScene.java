package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.scenes.GUIChooseResource;
import it.polimi.ingsw.application.gui.scenes.GUIEndGame;
import it.polimi.ingsw.application.gui.scenes.GUIGameSettings;
import it.polimi.ingsw.application.gui.scenes.GUIMainGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public enum GUIScene {
    MAIN_MENU("GUIMainMenu.fxml", true),
    GAME_MODE_SELECTION("GUIGameModeSelection.fxml", true),
    MP_SELECTION("GUIMPSelection.fxml", true),
    MP_ROOM("GUIMPRoom.fxml", true),
    PRE_GAME("GUIPreGame.fxml", true),
    MAIN_GAME("GUIMainGame.fxml", true),
    END_GAME("GUIEndGame.fxml", true),
    CONN_SETTINGS("GUIConnSettings.fxml", true),
    CHOOSE_RESOURCE("GUIChooseResource.fxml", true),
    WAREHOUSE("GUIWarehouse.fxml", false),
    LOADING("GUILoading.fxml", false),
    GAME_CHAT("GUIGameChat.fxml", false),
    GAME_SETTINGS("GUIGameSettings.fxml", true);

    /* FXML ATTRIBUTES */
    private static final String FXML_DIRECTORY = "/scenes/";
    private final String fxmlPath;

    /* SCENE ATTRIBUTES */
    private FXMLLoader fxmlLoader = null;
    private Parent root = null;
    private final boolean loadOnStarting;

    /* ACTIVE SCENE */
    private static final AtomicReference<PacketListener> activeScene = new AtomicReference<>(null);
    private static Parent nextLoadingRoot;

    /* STATIC SCENES */
    private static Scene scene;
    private static Scene chooseResourcesScene;

    /* STATIC CONTROLLERS */
    private static GUIChooseResource guiChooseResourceController;
    private static GUIEndGame guiEndGameController;

    /**
     * Create a new GUIScene.
     * @param fxmlPath name of the fxml file.
     * @param loadOnStarting whether the scene can be loaded on the application starting (for example, for GUI scenes that are not strongly related with the game itself, to speed up their loading.
     */
    GUIScene(String fxmlPath, boolean loadOnStarting) {
        this.fxmlPath = FXML_DIRECTORY + fxmlPath;
        this.loadOnStarting = loadOnStarting;
    }

    /**
     * Load this scene from the FXML file.
     */
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

    /**
     * Get this scene.
     * @return scene of this enum element.
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * Get the choose resources scene.
     * @return choose resources scene.
     */
    public static Scene getChooseResourcesScene() {
        return chooseResourcesScene;
    }

    /**
     * Get the choose resources scene controller.
     * @return GUI controller of the choose resources scene.
     */
    public static GUIChooseResource getChooseResourcesController() {
        return guiChooseResourceController;
    }

    /**
     * Get the end game scene controller.
     * @return GUI controller of the end game scene.
     */
    public static GUIEndGame getGuiEndGameController() {
        return guiEndGameController;
    }

    /**
     * Get the root of the scene.
     * @return root of the scene.
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * Set the scene as active scene and start the observers.
     * If necessary, this method will also refresh some views of the scene in order to clean the environment.
     */
    public void startCallbacks() {
        if(fxmlLoader.getController() instanceof PacketListener) {
            activeScene.set(fxmlLoader.getController());
        }
        if(fxmlLoader.getController() instanceof GUIObserverScene) {
            System.out.println("GUIScene.startCallbacks: starting observer");
            GUIUtility.executorService.submit(((GUIObserverScene)fxmlLoader.getController())::startObserver);
        }
    }

    /**
     * Produce a new scene.
     * @return new scene of the enum element.
     */
    public Scene produceScene() {
        Scene loadedScene = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            root = fxmlLoader.load();
            loadedScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedScene;
    }

    /**
     * Remove the current active scene, so that new messages will be not listened by anyone.
     */
    public synchronized static void removeActiveScene() {
        activeScene.set(null);
    }

    /**
     * Get the current active scene.
     * @return packet listener corresponding to the current active scene.
     */
    public synchronized static PacketListener getActiveScene() {
        return activeScene.get();
    }

    /**
     * Load the scenes from the fxml files.
     */
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

        // Initialize GUI Choose Resource scene
        chooseResourcesScene = new Scene(CHOOSE_RESOURCE.getRoot());
        guiChooseResourceController = CHOOSE_RESOURCE.fxmlLoader.getController();

        // Initialize GUI End Game controller
        guiEndGameController = END_GAME.fxmlLoader.getController();
    }

    /**
     * Show the loading scene.
     */
    public static void showLoadingScene() {
        scene.setRoot(nextLoadingRoot);
        try {
            nextLoadingRoot = new FXMLLoader(LOADING.getClass().getResource(LOADING.fxmlPath)).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
