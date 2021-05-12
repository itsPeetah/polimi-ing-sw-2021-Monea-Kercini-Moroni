package it.polimi.ingsw.view.data;

import java.util.List;

public class GameData {

    CommonData common;
    List<PlayerData> players;

    public CommonData getCommon() {
        return common;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

    /**
     * Constructor
     */

    public GameData() {

        common = new CommonData();
        for (PlayerData p : players) {
            p = new PlayerData();
        }
    }
}
