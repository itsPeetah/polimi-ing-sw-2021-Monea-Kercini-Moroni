package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.player.VPObserver;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIEndGame implements Initializable, VPObserver, GUIObserverScene {

    public ListView playerList;
    public ListView scoreList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @Override
    public void onVPChange() {

        Platform.runLater(() -> {

            System.out.println("GUIEndGame.onVPChange");

            for (int i = 0; i < GameApplication.getInstance().getRoomPlayers().size(); i++) {

                System.out.println("GUIEndGame.onVPChange inside");

                playerList.getItems().add(i, GameApplication.getInstance().getRoomPlayers().get(i));
                scoreList.getItems().add(i, GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getRoomPlayers().get(i)).getVP());
            }

        });

    }


    @Override
    public void startObserver() {

        //setting observer of VP
        System.out.println("GUIEndGame.initialize" + GameApplication.getInstance().gameExists());
        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).setObserver(this);

    }
}
