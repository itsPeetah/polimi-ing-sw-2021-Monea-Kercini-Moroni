package it.polimi.ingsw.view.data;


import it.polimi.ingsw.view.observer.GameDataObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class includes all view data for all players
 */

public class GameData {

    CommonData common;
    HashMap<String, PlayerData> playerTable;
    MomentaryData momentary;

    GameDataObserver gameDataObserver;


    /**
     * Constructor
     */

    public GameData() {

        common = new CommonData();
        playerTable = new HashMap<>();
        momentary = new MomentaryData();
    }

    public synchronized CommonData getCommon() {
        return common;
    }

    public synchronized void addPlayer(String name){
        playerTable.put(name, new PlayerData());
        if(gameDataObserver != null) gameDataObserver.onPlayerTableChange();
    }

    public synchronized List<String> getPlayersList() {
        return new ArrayList<>(playerTable.keySet());
    }

    public synchronized PlayerData getPlayerData(String name){
        return playerTable.get(name);
    }

    public MomentaryData getMomentary() {
        return momentary;
    }

    public void setObserver(GameDataObserver gameDataObserver) {
        this.gameDataObserver = gameDataObserver;
        gameDataObserver.onPlayerTableChange();
    }

}
