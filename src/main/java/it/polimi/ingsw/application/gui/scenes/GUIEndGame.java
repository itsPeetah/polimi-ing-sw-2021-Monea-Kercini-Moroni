package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.view.observer.PlayerDataObserver;
import it.polimi.ingsw.view.observer.player.VPObserver;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIEndGame implements Initializable, VPObserver {

    public ListView playerList;
    public ListView scoreList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @Override
    public void onVPChange() {

        for (int i = 0; i < GameApplication.getInstance().getRoomPlayers().size(); i++) {
            playerList.getItems().add(i, GameApplication.getInstance().getRoomPlayers().get(i));
        }

    }
}
