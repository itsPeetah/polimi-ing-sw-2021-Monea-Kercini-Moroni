package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.SystemMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class GUIMPSelection implements PacketListener, Initializable {
    public Button joinButt;
    public Button createButt;
    public Button backButt;
    public Button rejoinButt;
    public TextField userTextField;
    public TextField roomTextField;
    public ChoiceBox<Integer> numberChoiceBox;
    private final AtomicReference<Timer> startRoomTimer = new AtomicReference<>(new Timer());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberChoiceBox.getItems().addAll(Arrays.asList(1, 2, 3, 4));
        numberChoiceBox.setValue(4);
    }

    public void onCreateClick(ActionEvent actionEvent) {
        System.out.println("create pressed ");
        if(roomTextField.getText().length() > 0 && userTextField.getText().length() > 0) performSelection(SystemMessage.CREATE_ROOM);
    }

    public void onJoinClick(ActionEvent actionEvent) {
        System.out.println("join pressed ");
        if(userTextField.getText().length() > 0) performSelection(SystemMessage.JOIN_ROOM);
    }

    public void onReJoinClick(ActionEvent actionEvent) {
        System.out.println("rejoin pressed ");
        performRejoin();
    }

    @FXML
    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }

    private void performSelection(SystemMessage gameLobbyMessage) {
        String username = userTextField.getText();
        String room = roomTextField.getText();
        Integer playersNumber = (Integer)numberChoiceBox.getValue();
        System.out.println("GUIMPSelection.performSelection: " + playersNumber);

        GameApplication.getInstance().setRoomName(username);
        GameApplication.getInstance().setUserNickname(room);

        setButtonsDisabled(true);
        GUIScene.showLoadingScene();

        GUIUtility.executorService.submit(() -> {
            GameApplication.getInstance().out("Processing request, please wait.");
            String messageContent = gameLobbyMessage.addBody(room + " " + username + (gameLobbyMessage == SystemMessage.CREATE_ROOM ? " " + playersNumber : ""));
            NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
            GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
            GameApplication.getInstance().sendNetworkPacket(np);

        });
    }

    private void performRejoin() {
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, SystemMessage.REJOIN_ROOM.addBody(GameApplication.getInstance().getUserId()));
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }

    @Override
    public void onSystemMessage(SystemMessage type, String additionalContent) {
        System.out.println("GUIMPSelection.onSystemMessage: " + type);
        Platform.runLater(() -> {
            setButtonsDisabled(false);
            GUIScene.showLoadingScene();
            switch(type) {
                case CANT_JOIN:
                    GUIScene.MP_SELECTION.load();
                    break;
                case IN_GAME:
                    startRoomTimer.get().cancel();
                    startRoomTimer.set(new Timer());
                    GUIUtility.runSceneWithDelay(GUIScene.MAIN_GAME);
                    break;
                case START_ROOM:
                    startRoomTimer.get().cancel();
                    startRoomTimer.set(new Timer());
                    GUIUtility.runSceneWithDelay(GUIScene.PRE_GAME);
                    break;
                case IN_ROOM:
                    startRoomTimer.get().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            GUIUtility.runSceneWithDelay(GUIScene.MP_ROOM);
                        }
                    }, 1000);
                    break;
                case QUIT:
                    GUIUtility.handleServerQuit();
            }
        });
    }

    private void setButtonsDisabled(boolean disabled) {
        joinButt.setDisable(disabled);
        createButt.setDisable(disabled);
        backButt.setDisable(disabled);
        rejoinButt.setDisable(disabled);
    }
}
