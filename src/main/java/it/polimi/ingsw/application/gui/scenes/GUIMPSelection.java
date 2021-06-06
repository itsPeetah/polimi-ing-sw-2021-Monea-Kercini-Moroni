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
import javafx.scene.control.*;

import java.util.Timer;
import java.util.TimerTask;

public class GUIMPSelection implements PacketListener {
    public Button joinButt;
    public Button createButt;
    public Button backButt;
    public Button rejoinButt;
    public TextField userTextField;
    public TextField roomTextField;
    private TimerTask timeoutTask;

    public void onCreateClick(ActionEvent actionEvent) {
        System.out.println("create pressed ");
        GameApplication.getInstance().setRoomName(roomTextField.getText());
        GameApplication.getInstance().setUserNickname(userTextField.getText());
        performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.CREATE_ROOM);
    }

    public void onJoinClick(ActionEvent actionEvent) {
        System.out.println("join pressed ");
        GameApplication.getInstance().setRoomName(roomTextField.getText());
        GameApplication.getInstance().setUserNickname(userTextField.getText());
        performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.JOIN_ROOM);
    }

    public void onReJoinClick(ActionEvent actionEvent) {
        System.out.println("rejoin pressed ");
        performRejoin();
    }

    @FXML
    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }

    private void performSelection(String username, String room, GameLobbyMessage gameLobbyMessage) {
        setButtonsDisabled(true);
        GUIScene.showLoadingScene();

        Timer timer = new Timer();

        timeoutTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    System.out.println("GUIMPSelection: no room found");
                    setButtonsDisabled(false);
                    Platform.runLater(GUIScene.MP_SELECTION::load);
                    // TODO improve by removing this task and add listener for network messages
                });
            }
        };
        timer.schedule(timeoutTask, GUIConnSettings.TIMEOUT_TIME);

        GUIUtility.executorService.submit(() -> {
            GameApplication.getInstance().out("Processing request, please wait.");
            String messageContent = gameLobbyMessage.addBody(room + " " + username);
            NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
            GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
            GameApplication.getInstance().sendNetworkPacket(np);

        });
    }

    private void performRejoin() {
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, GameLobbyMessage.REJOIN_ROOM.addBody(GameApplication.getInstance().getUserId()));
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
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
                if(timeoutTask != null) timeoutTask.cancel();
                GUIUtility.runSceneWithDelay(GUIScene.MP_ROOM, 1000);
            }
            if (newState == GameApplicationState.INGAME) {
                if(timeoutTask != null) timeoutTask.cancel();
                GUIScene.MAIN_GAME.load();
            }
        });
    }

    private void setButtonsDisabled(boolean disabled) {
        joinButt.setDisable(disabled);
        createButt.setDisable(disabled);
        backButt.setDisable(disabled);
    }


}
