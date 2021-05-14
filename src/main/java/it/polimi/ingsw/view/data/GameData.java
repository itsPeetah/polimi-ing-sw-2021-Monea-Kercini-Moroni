package it.polimi.ingsw.view.data;

import java.util.HashMap;

public class GameData {

    CommonData common;
    HashMap<String, PlayerData> playerTable;

    int turn;

    public CommonData getCommon() {
        return common;
    }

    /**
     * Constructor
     */

    public GameData() {

        common = new CommonData();
        playerTable = new HashMap<>();
        turn = 0;
    }


    public void addPlayer(String name){
        playerTable.put(name, new PlayerData());
    }

    public PlayerData getPlayerData(String name){
        return playerTable.get(name);
    }

    public void turnIncrement(){
        turn++;
    }

}
