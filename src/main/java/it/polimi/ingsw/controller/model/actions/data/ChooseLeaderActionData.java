package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.controller.model.actions.ActionData;

public class ChooseLeaderActionData extends ActionData {
    private LeadCard chosenLeader;

    /**
     * Constructor for GSON
     */
    public ChooseLeaderActionData() {}

    /**
     * Create a new choose leader action data containing the chosen leader.
     * @param chosenLeader chosen leader.
     */
    public ChooseLeaderActionData(LeadCard chosenLeader) {
        this.chosenLeader = chosenLeader;
    }

    /**
     * Get the chosen leader.
     */
    public LeadCard getChosenLeader() {
        return chosenLeader;
    }
}
