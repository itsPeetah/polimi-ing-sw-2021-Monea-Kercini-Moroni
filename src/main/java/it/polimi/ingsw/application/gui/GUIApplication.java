package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.gui.scenes.GUIMainGame;
import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.InputStream;


public class GUIApplication extends Application {
    private static Alert oldDialog;
    public static final String ICON_PATH = "images/other/calamaio.png";
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

        //trying with input stream
        InputStream is = GUIApplication.class.getClassLoader().getResourceAsStream(ICON_PATH);

        Image iconImage = new Image(is);
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
            alert.show();
            oldDialog = alert;
        });
    }
}
