package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.scenes.GUIMainGame;
import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class GUIApplication extends Application {
    private static Alert oldDialog;
    public static final String ICON_PATH = "src/main/resources/images/other/calamaio.png";
    private static final String BLACK_LADDER_FONT_PATH = "fonts/BITCBLKAD.ttf";
    private static final String HIGH_TOWER_FONT_PATH = "fonts/HTOWERT.TTF";

    @Override
    public void start(Stage stage) throws Exception {
        // Load font
        Font.loadFont(getClass().getClassLoader().getResourceAsStream(BLACK_LADDER_FONT_PATH), 16);
        Font.loadFont(getClass().getClassLoader().getResourceAsStream(HIGH_TOWER_FONT_PATH), 16);
        CardManager.loadImages();
        Materials.init();
        SoloActionTokens.init();
        GUIMainGame.init();
        GUIScene.init();
        stage.setTitle("Masters of Renaissance");
        stage.setScene(GUIScene.getScene());
        GUIScene.MAIN_MENU.load();
        stage.setResizable(false);
        File file = new File(ICON_PATH);
        Image iconImage = new Image(file.toURI().toString());
        stage.getIcons().add(iconImage);
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
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
            File file = new File(ICON_PATH);
            Image iconImage = new Image(file.toURI().toString());
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(iconImage);
            alert.show();
            oldDialog = alert;
        });
    }
}
