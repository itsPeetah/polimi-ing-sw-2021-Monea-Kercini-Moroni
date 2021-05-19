package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIMPRoom implements Initializable {

    public static ObservableList<String> observablePlayersList = FXCollections.observableArrayList();
    public static ObservableList<String> observableChatList = FXCollections.observableArrayList();

    @FXML
    public Label room_name;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<String> playersListView;

    @FXML
    private TextField textField;

    @FXML
    private ListView<String> chatListView;

    @FXML
    private void onStartClick() {
        String messageContent = GameLobbyMessage.START_ROOM.addBody(GameApplication.getInstance().getRoomName() + " " + GameApplication.getInstance().getUserNickname());
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
        GameApplication.getInstance().sendNetworkPacket(np);
        // Send start packet
        new Thread(() -> {
            GameApplication.getInstance().startMPGame();
            while (GameApplication.getInstance().getApplicationState() == GameApplicationState.PREGAME) {}

            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            System.out.println(newState);
        }).start();

        GUIScene.PRE_GAME.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playersListView.setItems(observablePlayersList);
        chatListView.setItems(observableChatList);
        room_name.setText(GameApplication.getInstance().getRoomName());
    }

    public void sendMessage(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            String message = textField.getText();
            textField.clear();
            if(message != null && message.length() > 0) GameApplicationIOHandler.getInstance().pushChatMessage(message);
        }
    }
}
