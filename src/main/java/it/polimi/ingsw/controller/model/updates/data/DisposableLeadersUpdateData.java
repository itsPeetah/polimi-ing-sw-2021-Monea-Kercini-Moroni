package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.cards.LeadCard;

import java.util.ArrayList;
import java.util.List;

public class DisposableLeadersUpdateData implements UpdateData {
    
    private final List<LeadCard> leaders;
    private final String p;

    /**
     * Create a disposable leaders update.
     * @param leaders leaders of a player.
     * @param p nickname of the player.
     */
    public DisposableLeadersUpdateData(List<LeadCard> leaders, String p) {
        this.leaders = leaders;
        this.p = p;
    }

    /**
     * Get the leaders.
     */
    public List<LeadCard> getLeaders() {
        return leaders;
    }

    /**
     * Get the player.
     * @return nickname of the player.
     */
    public String getP() {
        return p;
    }
}
