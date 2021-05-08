package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.playerleaders.PlayerLeaders;

public class PlayerLeadersUpdateData implements UpdateData {
    PlayerLeaders pl;

    public PlayerLeadersUpdateData(PlayerLeaders pl) {
        this.pl = pl;
    }

    public PlayerLeaders getPlayerLeaders() {
        return pl;
    }
}
