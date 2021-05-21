package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GUIMPSelection implements Initializable, PacketListener {
    public Button joinButt;
    public Button createButt;
    public Button backButt;
    @FXML private TextField userTextField;
    @FXML private TextField roomTextField;

    @FXML
    private void onCreateClick(ActionEvent actionEvent) {
        System.out.println("create pressed ");
        GameApplication.getInstance().setRoomName(roomTextField.getText());
        GameApplication.getInstance().setUserNickname(userTextField.getText());
        performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.CREATE_ROOM);
    }

    @FXML
    private void onJoinClick(ActionEvent actionEvent) {
        System.out.println("join pressed ");
        GameApplication.getInstance().setRoomName(roomTextField.getText());
        GameApplication.getInstance().setUserNickname(userTextField.getText());
        performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.JOIN_ROOM);
    }

    @FXML
    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }

    private void performSelection(String username, String room, GameLobbyMessage gameLobbyMessage) {
        setButtonsDisabled(true);
        new Thread(() -> {
            GameApplication.getInstance().setUserNickname(username);
            GameApplication.getInstance().setRoomName(room);
            GameApplication.getInstance().out("Processing request, please wait.");
            String messageContent = gameLobbyMessage.addBody(room + " " + username);
            NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
            GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
            GameApplication.getInstance().sendNetworkPacket(np);
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Connect // TODO REMOVE next line
        //GameApplication.getInstance().connect("localhost", 42069);
        // When no connection
        System.out.println("connected: " + GameApplication.getInstance().isOnNetwork());
        Alert connectionAlert = new Alert(Alert.AlertType.CONFIRMATION);
        connectionAlert.setTitle("Connection is down");
        connectionAlert.setContentText("You are not connected to the server.\nTo start a MP game, you need to be connected.\nDo you want to move to the settings to connect to a server?");
        connectionAlert.setHeaderText(null);

        Optional<ButtonType> result = connectionAlert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Platform.runLater(GUIScene.CONN_SETTINGS::load);
        } else {
            Platform.runLater(GUIScene.GAME_MODE_SELECTION::load);
        }
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onSystemMessage(String message) {
        GameApplicationState newState = GameApplication.getInstance().getApplicationState();
        System.out.println("GUIMPSelection onSystemMessage triggered, new state = " + newState);
        if (newState == GameApplicationState.PREGAME) {
            Platform.runLater(() -> {
                GUIScene.MP_ROOM.load();
                setButtonsDisabled(false);
            });
        }
    }

    private void setButtonsDisabled(boolean disabled) {
        joinButt.setDisable(disabled);
        createButt.setDisable(disabled);
        backButt.setDisable(disabled);
    }
}
