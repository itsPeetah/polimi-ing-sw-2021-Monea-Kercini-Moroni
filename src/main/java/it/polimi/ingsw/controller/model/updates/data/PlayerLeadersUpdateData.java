package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerleaders.PlayerLeaders;

public class PlayerLeadersUpdateData implements UpdateData {
    PlayerLeaders pl;
    String p;

    public String getP() {
        return p;
    }

    public PlayerLeadersUpdateData(PlayerLeaders pl, String p) {
        this.pl = pl;
        this.p = p;
    }

    public PlayerLeaders getPlayerLeaders() {
        return pl;
    }
}
