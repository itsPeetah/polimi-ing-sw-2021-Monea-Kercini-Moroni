package it.polimi.ingsw.application.gui;

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
    private static Stage primaryStage;
    private static Alert oldDialog;
    private static final String ICON_PATH = "src/main/resources/images/other/calamaio.png";

    @Override
    public void start(Stage stage) throws Exception {
        // TODO SOLVE FONT PROBLEM
        /*
        System.out.println(getClass().getResource("/fonts/MediciText.ttf"));
        System.out.println(getClass().getResource("/fonts/MediciText.ttf").toExternalForm());*/
        Font mediciFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("/fonts/MediciText.ttf"), 10);
        System.out.println("Medici font: " + mediciFont);
        primaryStage = stage;
        stage.setTitle("Masters of Renaissance");
        GUIScene.MAIN_MENU.load();
        stage.setResizable(false);
        File file = new File(ICON_PATH);
        Image iconImage = new Image(file.toURI().toString());
        stage.getIcons().add(iconImage);
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
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
