
package it.polimi.ingsw.network.server.metapackets.events.data;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.events.EventData;

public class Choose2LeadersEventData extends EventData{
    private LeadCard[] leaders;

    /**
     * Constructor for GSON
     */
    public Choose2LeadersEventData() {}

    public LeadCard[] getLeaders() {
        return leaders;
    }

    public Choose2LeadersEventData(LeadCard[] leaders) {
        this.leaders = leaders;
    }
}
