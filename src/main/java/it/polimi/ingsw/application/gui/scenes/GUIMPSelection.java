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
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GUIMPSelection implements PacketListener {
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
        GUIScene.showLoadingScene();
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
    public void onMessage(Message message) {

    }

    @Override
    public void onSystemMessage(String message) {
        Platform.runLater(() -> {
            setButtonsDisabled(false);
            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            System.out.println("GUIMPSelection onSystemMessage triggered, new state = " + newState);
            if (newState == GameApplicationState.PREGAME) {
                GUIUtility.runSceneWithDelay(GUIScene.MP_ROOM, 1000);
            }
        });
    }

    private void setButtonsDisabled(boolean disabled) {
        joinButt.setDisable(disabled);
        createButt.setDisable(disabled);
        backButt.setDisable(disabled);
    }
}
