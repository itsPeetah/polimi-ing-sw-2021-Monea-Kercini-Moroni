package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.messages.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GUIConnSettings implements Initializable, PacketListener {
    public static final int TIMEOUT_TIME = 5000;
    public Button connButton;
    public Button backButton;
    public ImageView loadingFlask;
    private String address;
    private int port;
    public TextField addressTextField;
    public TextField portTextField;

    private TimerTask timeoutTask;

    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.SETTINGS.load();
    }

    public void onConnectClick(ActionEvent actionEvent) {
        if(GameApplication.getInstance().isOnNetwork()) return;
        disableButtons(true);
        address = addressTextField.getText();
        String portString = portTextField.getText().equals("") ? "0" : portTextField.getText();
        port = Integer.parseInt(portString);

        // Setup animation
        Timer timer = new Timer();

        timeoutTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    disableButtons(false);
                    System.out.println("GUIConnSettings.run: Oops, make sure the server is online and the address and port are correct!");
                    GameApplication.getInstance().out("Oops, make sure the server is online and the address and port are correct!");
                    Platform.runLater(GUIScene.CONN_SETTINGS::load);
                });
            }
        };
        timer.schedule(timeoutTask, TIMEOUT_TIME);

        GUIScene.showLoadingScene();

        // Start the connection on a new thread
        new Thread(() -> GameApplication.getInstance().connect(address, port)).start();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Make the port text field change only with numbers
        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // If a non number has been added, remove all the non numbers with empty strings
            if (!newValue.matches("\\d*")) {
                portTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onSystemMessage(String message) {
        if(GameApplication.getInstance().isOnNetwork()) {
            System.out.println(GameApplication.getInstance().isOnNetwork());
            Platform.runLater(() -> {
                timeoutTask.cancel();
                disableButtons(false);
                GUIScene.removeActiveScene();
                GUIUtility.runSceneWithDelay(GUIScene.MAIN_MENU, 1000);
            });
        }
    }

    private void disableButtons(boolean disabled) {
        connButton.setDisable(disabled);
        backButton.setDisable(disabled);
    }
}
