package it.polimi.ingsw.application.gui.scenes;

import com.sun.javafx.collections.ObservableListWrapper;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GUIMPRoom implements Initializable {
    public static ObservableList<String> observablePlayersList = FXCollections.observableArrayList();

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<String> playersListView;

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playersListView.setItems(observablePlayersList);
        File file = new File("src/main/resources/images/MPRoomImage.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
}
