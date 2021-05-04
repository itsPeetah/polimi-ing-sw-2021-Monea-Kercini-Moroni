
package it.polimi.ingsw.network.server.metapackets.actions.data;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.actions.ActionData;

public class Choose2LeadersActionData extends ActionData {
    private LeadCard[] leaders;

    /**
     * Constructor for GSON
     */
    public Choose2LeadersActionData() {}

    public LeadCard[] getLeaders() {
        return leaders;
    }

    public void setLeaders(LeadCard[] leaders) {
        this.leaders = leaders;
    }

    public Choose2LeadersActionData(LeadCard[] leaders) {
        this.leaders = leaders;
    }
}