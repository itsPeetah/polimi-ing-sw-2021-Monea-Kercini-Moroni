package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIApplication;
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
    public Button quickJoinButt;
    public TextField userTextField;
    public TextField roomTextField;
    public ChoiceBox<Integer> numberChoiceBox;
    private final AtomicReference<Timer> startRoomTimer = new AtomicReference<>(new Timer());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberChoiceBox.getItems().addAll(Arrays.asList(1, 2, 3, 4));
        numberChoiceBox.setValue(4);
    }

    /**
     * On create button click.
     * @param actionEvent
     */
    public void onCreateClick(ActionEvent actionEvent) {
        if(roomTextField.getText().length() > 0 && userTextField.getText().length() > 0) performSelection(SystemMessage.CREATE_ROOM);
    }

    /**
     * On join button click.
     * @param actionEvent
     */
    public void onJoinClick(ActionEvent actionEvent) {
        if(userTextField.getText().length() > 0) performSelection(SystemMessage.JOIN_ROOM);
    }

    /**
     * On re join button click.
     * @param actionEvent
     */
    public void onReJoinClick(ActionEvent actionEvent) {
        performRejoin();
    }

    /**
     * On quick join button click.
     * @param actionEvent
     */
    public void onQuickJoinClick(ActionEvent actionEvent) {
        if(userTextField.getText().length() > 0) performQuickJoin();
    }

    /**
     * On back button click.
     * @param actionEvent
     */
    @FXML
    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }

    /**
     * Handle join and create actions.
     * @param gameLobbyMessage
     */
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

    /**
     * Handle rejoin action.
     */
    private void performRejoin() {
        setButtonsDisabled(true);
        GUIScene.showLoadingScene();

        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, SystemMessage.REJOIN_ROOM.addBody(GameApplication.getInstance().getUserId()));
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }

    /**
     * Handle quick join action.
     */
    private void performQuickJoin() {
        setButtonsDisabled(true);
        GUIScene.showLoadingScene();

        String username = userTextField.getText();

        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, SystemMessage.QUICK_START.addBody(username));
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }

    @Override
    public void onSystemMessage(SystemMessage type, String additionalContent) {
        Platform.runLater(() -> {
            setButtonsDisabled(false);
            switch(type) {
                case CANT_JOIN:
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                GUIScene.MP_SELECTION.load();
                                GUIApplication.showDialog("It is not possible to join the room.\nTry to use another nickname or connect to another room.");
                            });
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(timerTask, 500);
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
                    }, 2000);
                    break;
                case QUIT:
                    GUIUtility.handleServerQuit();
            }
        });
    }

    /**
     /**
     * Set the disable effect of the buttons.
     * @param disabled if true the buttons will be disabled, otherwise they would be clickable.
     */
    private void setButtonsDisabled(boolean disabled) {
        joinButt.setDisable(disabled);
        createButt.setDisable(disabled);
        backButt.setDisable(disabled);
        rejoinButt.setDisable(disabled);
        quickJoinButt.setDisable(disabled);
    }
}
