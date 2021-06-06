package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.player.VPObserver;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUIEndGame implements Initializable, VPObserver, GUIObserverScene {

    public ListView playerList;
    public ListView scoreList;
    public Label endText;

    private static final AtomicBoolean win = new AtomicBoolean();

    public static void setWin(boolean win) {
        GUIEndGame.win.set(win);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @Override
    public void onVPChange() {

        Platform.runLater(() -> {

            for (int i = 0; i < GameApplication.getInstance().getRoomPlayers().size(); i++) {

                playerList.getItems().add(i, GameApplication.getInstance().getRoomPlayers().get(i));
                scoreList.getItems().add(i, GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getRoomPlayers().get(i)).getVP());
            }

            if(!win.get()){
                endText.setText("Defeat");
            }
            //if single player get the victory points of player
            if(GameApplication.getInstance().getGameController().isSinglePlayer()){
                playerList.getItems().add(GameApplication.getInstance().getUserNickname());
                scoreList.getItems().add(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getVP());
            }

        });

    }


    @Override
    public void startObserver() {

        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).setObserver(this);

    }
}
