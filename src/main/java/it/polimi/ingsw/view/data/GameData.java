package it.polimi.ingsw.view.data;

import java.util.HashMap;
import java.util.List;

public class GameData {

    CommonData common;
    HashMap<String, PlayerData> playerTable = new HashMap<>();

    public CommonData getCommon() {
        return common;
    }

    /**
     * Constructor
     */

    public GameData() {

        common = new CommonData();
    }


    public void addPlayer(String name){
        playerTable.put(name, new PlayerData());
    }

    public void getPlayerData(String name){
        playerTable.get(name);
    }

}
