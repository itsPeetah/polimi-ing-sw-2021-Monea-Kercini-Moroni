package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIApplication;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GUIMPSelection implements Initializable {
    @FXML private TextField userTextField;
    @FXML private TextField roomTextField;

    @FXML
    private void onCreateClick(ActionEvent actionEvent) {
        System.out.println("create pressed ");
        performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.CREATE_ROOM);
        GameApplication.getInstance().setRoomName(roomTextField.getText());
        GameApplication.getInstance().setUserNickname(userTextField.getText());
    }

    @FXML
    private void onJoinClick(ActionEvent actionEvent) {
        System.out.println("join pressed ");
        performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.JOIN_ROOM);
        GameApplication.getInstance().setRoomName(roomTextField.getText());
        GameApplication.getInstance().setUserNickname(userTextField.getText());
    }

    private static void performSelection(String username, String room, GameLobbyMessage gameLobbyMessage) {
        new Thread(() -> {
            GameApplication.getInstance().setUserNickname(username);
            GameApplication.getInstance().setRoomName(room);
            GameApplication.getInstance().out("Processing request, please wait.");
            String messageContent = gameLobbyMessage.addBody(room + " " + username);
            NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
            GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
            GameApplication.getInstance().sendNetworkPacket(np);
            while (GameApplication.getInstance().getApplicationState() == GameApplicationState.CONNECTING_TO_ROOM) {}
            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            if (newState == GameApplicationState.PREGAME) {
                Platform.runLater(GUIScene.MP_ROOM::load);
            }}).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Connect
        GameApplication.getInstance().connect("localhost", 42069);
        // When no connection
        System.out.println("connected: " + GameApplication.getInstance().isOnNetwork());
        while(!GameApplication.getInstance().isOnNetwork()) {
            Alert connectionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            connectionAlert.setTitle("Connection is down");
            connectionAlert.setContentText("You are not connected to the server.\nTo start a MP game, you need to be connected.\nDo you want to retry to open a connection?");
            connectionAlert.setHeaderText(null);

            Optional<ButtonType> result = connectionAlert.showAndWait();
            if(result.get() == ButtonType.OK) {
                GameApplication.getInstance().connect("localhost", 42069);
                if (GameApplication.getInstance().isOnNetwork()) {
                    break;
                }
                else {
                    Alert connectionErrorAlert = new Alert(Alert.AlertType.ERROR);
                    connectionErrorAlert.setTitle("Connection error");
                    connectionErrorAlert.setHeaderText(null);
                    connectionErrorAlert.setContentText("Ooops,\nit is not possible to open a connection with the server!\nTry again.");
                    connectionErrorAlert.showAndWait();
                }
            } else {
                Platform.runLater(GUIScene.GAME_MODE_SELECTION::load);
                break;
            }
        }
    }
}
