package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIConnSettings implements Initializable, PacketListener {
    public Button connButton;
    public Button backButton;
    private String address;
    private int port;
    public TextField addressTextField;
    public TextField portTextField;

    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.SETTINGS.load();
    }

    public void onConnectClick(ActionEvent actionEvent) {
        connButton.setDisable(true);
        backButton.setDisable(true);
        address = addressTextField.getText();
        port = Integer.parseInt(portTextField.getText());
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
        Platform.runLater(() -> {
            connButton.setDisable(false);
            backButton.setDisable(false);
            if(GameApplication.getInstance().isOnNetwork()) GUIScene.MAIN_MENU.load();
        });
    }
}
