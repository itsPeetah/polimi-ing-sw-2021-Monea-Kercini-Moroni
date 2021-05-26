package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.controller.model.actions.ActionData;

public class DevCardActionData extends ActionData {
    DevCard chosenCard;

    int position; //The position (pile) the player has chosen to put this dev card

    /**
     * Constructor for GSON
     */

    public DevCardActionData(DevCard chosenCard, int position) {
        this.chosenCard = chosenCard;
        this.position = position;
    }


    public DevCard getChosenCard() {
        return chosenCard;
    }

    public int getPosition() {
        return position;
    }
}
