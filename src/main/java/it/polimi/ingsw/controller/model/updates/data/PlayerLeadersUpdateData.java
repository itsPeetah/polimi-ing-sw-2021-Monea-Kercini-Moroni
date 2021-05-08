package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.model.playerleaders.PlayerLeaders;

public class PlayerLeadersUpdateData {
    PlayerLeaders pl;

    public PlayerLeadersUpdateData(PlayerLeaders pl) {
        this.pl = pl;
    }

    public PlayerLeaders getPlayerLeaders() {
        return pl;
    }
}
