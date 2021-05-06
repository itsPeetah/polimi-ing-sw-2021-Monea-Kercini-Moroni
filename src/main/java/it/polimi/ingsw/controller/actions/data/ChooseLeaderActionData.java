package it.polimi.ingsw.controller.actions.data;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.controller.actions.ActionData;

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
