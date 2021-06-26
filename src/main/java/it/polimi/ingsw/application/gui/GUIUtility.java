package it.polimi.ingsw.application.gui;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.messages.Message;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.application.gui.GUIApplication.ICON_PATH;

public class GUIUtility {
    public static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final int DELAY = 1000;
    private static Glow glow;
    private static ColorAdjust blackColorAdjust;
    private static Alert oldDialog;

    /**
     * Open a new organize warehouse window.
     * @param owner owner of window.
     */
    public static void launchOrganizeWarehouseWindow(Window owner) {
        Stage stage = prepareStage(false, owner);
        stage.setTitle("Reorganize your warehouse");
        stage.setScene(GUIScene.WAREHOUSE.produceScene());
        stage.show();
    }

    /**
     * Open a new pick resource window.
     * @param owner owner of the window.
     */
    public static void launchPickResourceWindow(Window owner) {
        Stage stage = prepareStage(false, owner);
        stage.setTitle("Choose the resources");
        stage.setScene(GUIScene.getChooseResourcesScene());
        stage.show();
    }

    /**
     * Open a new chat window.
     */
    public static void launchChat() {
        Stage stage = prepareStage(true, null);
        stage.setTitle("Chat");
        stage.setScene(GUIScene.GAME_CHAT.produceScene());
        GUIScene.GAME_CHAT.startCallbacks();
        GUIChat.setChatStage(stage);
        stage.show();
    }

    /**
     * Handle a leave game action from the GUI point of view.
     */
    public static void handleLeaveGame() {
        GUIScene.showLoadingScene();

        GUIScene.getChooseResourcesController().setMessage(Message.CHOOSE_RESOURCE);
        GUIScene.removeActiveScene();

        GameApplication.getInstance().leaveGame();
        GUIUtility.runSceneWithDelay(GUIScene.MAIN_MENU);
    }

    /**
     * Handle a server quit action from the GUI point of view.
     */
    public static void handleServerQuit() {
        GUIScene.showLoadingScene();

        GUIScene.getChooseResourcesController().setMessage(Message.CHOOSE_RESOURCE);
        GUIScene.removeActiveScene();

        GUIUtility.runSceneWithDelay(GUIScene.MAIN_MENU);
    }

    /**
     * Create a new stage.
     * @param canBeClosed true if the stage can be closed from the X button, false otherwise.
     * @param owner owner of the stage, if the stage cannot be closed.
     * @return
     */
    private static Stage prepareStage(boolean canBeClosed, Window owner) {
        Stage stage = new Stage();
        InputStream is = GUIUtility.class.getClassLoader().getResourceAsStream(ICON_PATH);
        Image iconImage = new Image(is);
        stage.getIcons().add(iconImage);
        stage.setResizable(false);
        if(!canBeClosed) {
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setOnCloseRequest(Event::consume);
        }
        return stage;
    }

    /**
     * Set a scene with a fixed delay.
     * The delay is necessary to load all the views before the scene is visible.
     * @param guiScene
     */
    public static void runSceneWithDelay(GUIScene guiScene) {
        GUIUtility.executorService.submit(() -> {
            // Start the listeners and observers so the scene can show the correct data
            Timer timer = new Timer();
            TimerTask startCallbacksTask = new TimerTask() {
                @Override
                public void run() {
                    guiScene.startCallbacks();
                }
            };
            TimerTask switchTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> GUIScene.getScene().setRoot(guiScene.getRoot()));
                }
            };
            timer.schedule(startCallbacksTask, DELAY/2);
            timer.schedule(switchTask, DELAY);
        });
    }

    /**
     * Get a glow effect.
     * @return glow effect.
     */
    public static Glow getGlow() {
        if(glow == null) {
            glow = new Glow();
            glow.setLevel(0.6);
        }
        return glow;
    }

    /**
     * Get a black color adjustment effect.
     * @return black color adjustment effect.
     */
    public static ColorAdjust getBlackEffect() {
        if(blackColorAdjust == null) {
            blackColorAdjust = new ColorAdjust();
            blackColorAdjust.setSaturation(-0.7);
        }
        return blackColorAdjust;

    }

    /**
     * Show a dialog with a certain message.
     * @param message message to notify to the user.
     */
    public static void showDialog(String message) {
        Platform.runLater(() -> {
            if(oldDialog != null) {
                oldDialog.close();
            }
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(message);
            InputStream is = GUIApplication.class.getClassLoader().getResourceAsStream(ICON_PATH);
            assert is != null;
            Image iconImage = new Image(is);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(iconImage);
            alert.show();
            oldDialog = alert;
        });
    }
}
