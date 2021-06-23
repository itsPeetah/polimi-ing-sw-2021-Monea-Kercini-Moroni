package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.player.VPObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GUIEndGame implements VPObserver, GUIObserverScene {

    public ListView<String> playerList;
    public ListView<Integer> scoreList;

    @FXML
    public Label endText;

    private static final AtomicBoolean win = new AtomicBoolean();

    /**
     * Set the text with Victory or Defeat message
     * @param win true if the user is the winner, false otherwise.
     */
    public void setWin(boolean win) {
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
        new Thread(() -> {
            GameData gameData = GameApplication.getInstance().getGameController().getGameData();
            boolean isSinglePlayer = GameApplication.getInstance().getGameController().isSinglePlayer();

            if(!isSinglePlayer) {
                // MP DATA
                Map<String, Integer> unsortedPlayersVP = new HashMap<>();
                List<String> players = GameApplication.getInstance().getRoomPlayers();

                // Load the players scores
                for(String player: players) {
                    unsortedPlayersVP.put(player, gameData.getPlayerData(player).getVP());
                }

                Map<String, Integer> playersVP = new HashMap<>();

                // Fill sorted map
                unsortedPlayersVP.entrySet().stream()
                        .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                        .forEach(k -> playersVP.put(k.getKey(), k.getValue()));

                Platform.runLater(() -> {
                    for (Map.Entry<String, Integer> entry: playersVP.entrySet()) {
                        playerList.getItems().add(entry.getKey());
                        scoreList.getItems().add(entry.getValue());
                    }
                });
            } else {
                // SP DATA
                String singlePlayerNickname = GameApplication.getInstance().getUserNickname();
                int singlePlayerVP = gameData.getPlayerData(singlePlayerNickname).getVP();

                Platform.runLater(() -> {
                    playerList.getItems().add(singlePlayerNickname);
                    scoreList.getItems().add(singlePlayerVP);
                });
            }
        }).start();
    }




    @Override
    public void startObserver() {
        playerList.getItems().clear();
        scoreList.getItems().clear();
        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).setObserver(this);
    }

    /**
     * On exit button click.
     * @param actionEvent
     */
    public void onExitClick(ActionEvent actionEvent) {
        GUIUtility.handleLeaveGame();
    }
}
