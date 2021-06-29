
package it.polimi.ingsw.controller.model.actions.data;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.controller.model.actions.ActionData;

public class Choose2LeadersActionData extends ActionData {
    private LeadCard[] leaders;

    /**
     * Constructor for GSON
     */
    public Choose2LeadersActionData() {}

    /**
     * Get leaders of the action.
     */
    public LeadCard[] getLeaders() {
        return leaders;
    }

    /**
     * Set leaders of the action.
     * @param leaders leaders to set.
     */
    public void setLeaders(LeadCard[] leaders) {
        this.leaders = leaders;
    }
}
