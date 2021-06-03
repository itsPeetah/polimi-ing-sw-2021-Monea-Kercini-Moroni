package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.player.VPObserver;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIEndGame implements Initializable, VPObserver, GUIObserverScene, PacketListener {

    public ListView playerList;
    public ListView scoreList;
    public Label endText;

    private static boolean win;

    public static void setWin(boolean win) {
        GUIEndGame.win = win;
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

            if(!win){
                endText.setText("Defeat");
            }

        });

    }


    @Override
    public void startObserver() {

        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).setObserver(this);

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onSystemMessage(String message) {

    }
}
