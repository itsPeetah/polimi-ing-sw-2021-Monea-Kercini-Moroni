package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.player.VPObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUIEndGame implements VPObserver, GUIObserverScene {

    public ListView<String> playerList;
    public ListView<Integer> scoreList;

    @FXML
    public static Label endText;

    private static final AtomicBoolean win = new AtomicBoolean();

    public static void setWin(boolean win) {
        GUIEndGame.win.set(win);
        if(win) {
            endText.setText("Victory");
        }
        else {
            endText.setText("Defeat");
        }
    }

    @Override
    public void onVPChange() {
        GUIUtility.executorService.submit(() -> {
            GameData gameData = GameApplication.getInstance().getGameController().getGameData();

            // MP DATA
            List<String> players = GameApplication.getInstance().getRoomPlayers();
            List<Integer> playersVP = new ArrayList<>();

            // SP DATA
            String singlePlayerNickname = GameApplication.getInstance().getUserNickname();

            boolean isSinglePlayer = GameApplication.getInstance().getGameController().isSinglePlayer();

            int tempSinglePlayerVP = 0;
            if(!isSinglePlayer) {
                // Load the players scores
                for(String player: players) {
                    playersVP.add(gameData.getPlayerData(player).getVP());
                }
            } else {
                tempSinglePlayerVP = gameData.getPlayerData(singlePlayerNickname).getVP();
            }

            final int singlePlayerVP = tempSinglePlayerVP;

            Platform.runLater(() -> {
                if(!isSinglePlayer) {
                    for (int i = 0; i < GameApplication.getInstance().getRoomPlayers().size(); i++) {
                        playerList.getItems().add(i, players.get(i));
                        scoreList.getItems().add(i, playersVP.get(i));
                    }
                }
                else {
                    playerList.getItems().add(singlePlayerNickname);
                    scoreList.getItems().add(singlePlayerVP);
                }
            });
        });



    }


    @Override
    public void startObserver() {

        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).setObserver(this);

    }
}
