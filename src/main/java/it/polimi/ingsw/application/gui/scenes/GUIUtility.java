package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIApplication;
import it.polimi.ingsw.application.gui.GUIScene;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.application.gui.GUIApplication.ICON_PATH;

public class GUIUtility {

    public static void launchOrganizeWarehouseWindow(Window owner) {
        Stage stage = new Stage();
        File file = new File(ICON_PATH);
        Image iconImage = new Image(file.toURI().toString());
        stage.getIcons().add(iconImage);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        stage.setTitle("Reorganize your warehouse");
        stage.setScene(GUIScene.WAREHOUSE.produceScene());
        stage.show();
    }

    public static void launchPickResourceWindow(Window owner) {
        Stage stage = new Stage();
        File file = new File(ICON_PATH);
        Image iconImage = new Image(file.toURI().toString());
        stage.getIcons().add(iconImage);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        stage.setTitle("Choose the resources");
        stage.setScene(GUIScene.CHOOSE_RESOURCE.produceScene());
        stage.show();
    }

    public static void runSceneWithDelay(GUIScene guiScene, int delay) {
        new Thread(() -> {
            Scene scene = guiScene.loadWithoutSet();
            Timer timer = new Timer();
            TimerTask timeoutTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> GUIApplication.setScene(scene));
                }
            };
            timer.schedule(timeoutTask, delay);
        }).start();

    }
}
