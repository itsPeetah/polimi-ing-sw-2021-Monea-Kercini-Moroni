package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.controller.model.actions.ActionData;

public class DevCardActionData extends ActionData {

    private final DevCard chosenCard;
    private final int position; //The position (pile) the player has chosen to put this dev card

    /**
     * Constructor for GSON
     */
    public DevCardActionData(DevCard chosenCard, int position) {
        this.chosenCard = chosenCard;
        this.position = position;
    }

    /**
     * Get chosen dev card of the action data.
     * @return chosen dev card.
     */
    public DevCard getChosenCard() {
        return chosenCard;
    }

    /**
     * Get the position of the chosen dev card.
     * @return pile the player has chosen to add the chosen dev card to.
     */
    public int getPosition() {
        return position;
    }
}
