package it.polimi.ingsw.application.gui;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.messages.Message;
import javafx.application.Platform;
import javafx.event.Event;
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

    public static void launchOrganizeWarehouseWindow(Window owner) {
        Stage stage = prepareStage(false, owner);
        stage.setTitle("Reorganize your warehouse");
        stage.setScene(GUIScene.WAREHOUSE.produceScene());
        stage.show();
    }

    public static void launchPickResourceWindow(Window owner) {
        Stage stage = prepareStage(false, owner);
        stage.setTitle("Choose the resources");
        stage.setScene(GUIScene.getChooseResourcesScene());
        stage.show();
    }

    public static void launchChat() {
        Stage stage = prepareStage(true, null);
        stage.setTitle("Chat");
        stage.setScene(GUIScene.GAME_CHAT.produceScene());
        GUIScene.GAME_CHAT.startCallbacks();
        GUIChat.setChatStage(stage);
        stage.show();
    }

    public static void handleLeaveGame() {
        System.out.println("GUIUtility.handleLeaveGame");

        GUIScene.showLoadingScene();

        GUIScene.getChooseResourcesController().setMessage(Message.CHOOSE_RESOURCE);
        GUIScene.removeActiveScene();

        GameApplication.getInstance().leaveGame();
        GUIUtility.runSceneWithDelay(GUIScene.MAIN_MENU);
    }

    public static void handleServerQuit() {
        System.out.println("GUIUtility.handleServerQuit");

        GUIScene.showLoadingScene();

        GUIScene.getChooseResourcesController().setMessage(Message.CHOOSE_RESOURCE);
        GUIScene.removeActiveScene();

        GUIUtility.runSceneWithDelay(GUIScene.MAIN_MENU);
    }

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

    public static Glow getGlow() {
        if(glow == null) {
            glow = new Glow();
            glow.setLevel(0.6);
        }
        return glow;
    }

    public static ColorAdjust getBlackEffect() {
        if(blackColorAdjust == null) {
            blackColorAdjust = new ColorAdjust();
            blackColorAdjust.setSaturation(-0.7);
        }
        return blackColorAdjust;

    }
}
