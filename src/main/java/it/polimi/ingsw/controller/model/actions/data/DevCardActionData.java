package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.controller.model.actions.ActionData;

public class DevCardActionData extends ActionData {
    DevCard chosenCard;
    int position; //The position (pile) the player has chosen to put this dev card

    /**
     * Constructor for GSON
     */
    public DevCardActionData() {}

    public DevCardActionData(DevCard chosenCard) {
        this.chosenCard = chosenCard;
    }

    public DevCardActionData(int position) {
        this.position = position;
    }

    public DevCard getChosenCard() {
        return chosenCard;
    }

    public int getPosition() {
        return position;
    }
}
