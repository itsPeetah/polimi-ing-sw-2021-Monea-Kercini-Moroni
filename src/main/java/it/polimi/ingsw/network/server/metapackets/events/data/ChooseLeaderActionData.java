package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.events.ActionData;

public class ChooseLeaderActionData extends ActionData {
    LeadCard chosenLeader;

    /**
     * Constructor for GSON
     */
    public ChooseLeaderActionData() {}

    public ChooseLeaderActionData(LeadCard chosenLeader) {
        this.chosenLeader = chosenLeader;
    }

    public LeadCard getChosenLeader() {
        return chosenLeader;
    }
}
