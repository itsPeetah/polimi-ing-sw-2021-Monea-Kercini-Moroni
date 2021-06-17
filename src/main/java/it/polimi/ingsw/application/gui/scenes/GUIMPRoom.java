package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIChat;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.SystemMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GUIMPRoom implements PacketListener, GUIObserverScene {

    public static ObservableList<String> observablePlayersList = FXCollections.observableArrayList();

    public Label room_name;
    public Button startButt;
    public Button leaveButton;

    @FXML
    private ListView<String> playersListView;

    @FXML
    private TextField textField;

    @FXML
    private ListView<String> chatListView;

    public void onStartClick() {
        setButtonsDisabled(false);
        String messageContent = SystemMessage.START_ROOM.addBody(GameApplication.getInstance().getRoomName() + " " + GameApplication.getInstance().getUserNickname());
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
        GameApplication.getInstance().sendNetworkPacket(np);
    }

    public void onLeaveClick(ActionEvent actionEvent) {
    }

    public void sendMessage(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            String message = textField.getText();
            textField.clear();
            GUIChat.sendMessage(message);
        }
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onSystemMessage(SystemMessage type, String additionalContent) {
        GameApplicationState gameApplicationState = GameApplication.getInstance().getApplicationState();
        switch(gameApplicationState) {
            case INGAME:
                setButtonsDisabled(false);
                GUIUtility.runSceneWithDelay(GUIScene.PRE_GAME);
                break;
        }
    }

    private void setButtonsDisabled(boolean disabled) {
        startButt.setDisable(disabled);
    }

    @Override
    public void startObserver() {
        System.out.println("GUIMPRoom.startObserver");
        playersListView.setItems(observablePlayersList);
        GUIChat.bindChat(chatListView);
        room_name.setText(GameApplication.getInstance().getRoomName());
    }
}
