package it.polimi.ingsw.view.data;

import java.util.HashMap;

/**
 * This class includes all view data for all players
 */

public class GameData {

    CommonData common;
    HashMap<String, PlayerData> playerTable;
    MomentaryData momentary;

    int turn;


    /**
     * Constructor
     */

    public GameData() {

        common = new CommonData();
        playerTable = new HashMap<>();
        momentary = new MomentaryData();
        turn = 0;
    }

    public synchronized CommonData getCommon() {
        return common;
    }

    public synchronized void addPlayer(String name){
        playerTable.put(name, new PlayerData());
    }

    public synchronized PlayerData getPlayerData(String name){
        System.out.println("Io qua ti do null");
        return playerTable.get(name);
        //System.out.println("E poi tu non vedi niente");
    }

    public MomentaryData getMomentary() {
        return momentary;
    }

    public void turnIncrement(){
        turn++;
    }

}
