package it.polimi.ingsw.view.data;


import it.polimi.ingsw.view.observer.GameDataObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class includes all view data for all players
 */

public class GameData {

    private final CommonData common;
    private final HashMap<String, PlayerData> playerTable;
    private final MomentaryData momentary;
    private AtomicReference<GameDataObserver> gameDataObserver;


    /**
     * Constructor
     */
    public GameData() {

        common = new CommonData();
        playerTable = new HashMap<>();
        momentary = new MomentaryData();
        gameDataObserver = new AtomicReference<>(null);
    }

    /**
     * Get the common data.
     * @return common data of the game.
     */
    public CommonData getCommon() {
        return common;
    }

    /**
     * Add a player to the common data.
     * @param name nickname of the player.
     */
    public synchronized void addPlayer(String name){
        playerTable.put(name, new PlayerData());
        if(gameDataObserver != null) gameDataObserver.get().onPlayerTableChange();
    }

    /**
     * Get the players' nicknames.
     * @return list containing the nicknames of the players.
     */
    public synchronized List<String> getPlayersList() {
        return new ArrayList<>(playerTable.keySet());
    }

    /**
     * Get the data of the player.
     * @param name nickname of the player.
     * @return data of the player.
     */
    public synchronized PlayerData getPlayerData(String name){
        return playerTable.get(name);
    }

    /**
     * Get the momentary data of the game.
     * @return momentary data.
     */
    public MomentaryData getMomentary() {
        return momentary;
    }

    /**
     * Set the observer of the game data.
     * @param gameDataObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(GameDataObserver gameDataObserver) {
        this.gameDataObserver.set(gameDataObserver);
        gameDataObserver.onPlayerTableChange();
    }

}
