package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerleaders.PlayerLeaders;

public class PlayerLeadersUpdateData implements UpdateData {
    private final PlayerLeaders pl;
    private final String p;

    /**
     * Create a player leaders update.
     * @param pl leaders of the player.
     * @param p nickname of the player.
     */
    public PlayerLeadersUpdateData(PlayerLeaders pl, String p) {
        this.pl = pl;
        this.p = p;
    }

    /**
     * Get the leaders of the player.
     */
    public PlayerLeaders getPlayerLeaders() {
        return pl;
    }

    /**
     * Get the nickname of the player.
     */
    public String getP() {
        return p;
    }
}
