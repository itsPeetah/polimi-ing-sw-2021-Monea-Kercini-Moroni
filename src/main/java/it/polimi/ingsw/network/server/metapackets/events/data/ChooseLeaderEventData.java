package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.events.EventData;

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
