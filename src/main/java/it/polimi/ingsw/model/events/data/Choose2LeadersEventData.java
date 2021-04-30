
package it.polimi.ingsw.model.events.data;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.events.EventData;
import it.polimi.ingsw.model.general.Resources;

import java.util.ArrayList;

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
