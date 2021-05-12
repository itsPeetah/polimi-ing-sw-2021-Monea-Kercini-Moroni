package it.polimi.ingsw.view.data;

import java.util.List;

public class GameData {

    CommonData common;
    List<PlayerData> players;

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
