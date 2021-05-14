package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIMPRoom implements Initializable {
    @FXML
    private void onStartClick() {
        String messageContent = GameLobbyMessage.START_ROOM.addBody(GameApplication.getInstance().getRoomName() + " " + GameApplication.getInstance().getUserNickname());
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
        GameApplication.getInstance().sendNetworkPacket(np);
        // Send start packet
        new Thread(() -> {
            while (GameApplication.getInstance().getApplicationState() == GameApplicationState.PREGAME) {}
            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            System.out.println(newState);
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
