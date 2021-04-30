package it.polimi.ingsw.model.events.data;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.events.EventData;

public class ChooseLeaderEventData extends EventData {
    LeadCard chosenLeader;

    /**
     * Constructor for GSON
     */
    public ChooseLeaderEventData() {}

    public ChooseLeaderEventData(LeadCard chosenLeader) {
        this.chosenLeader = chosenLeader;
    }

    public LeadCard getChosenLeader() {
        return chosenLeader;
    }
}
