package it.polimi.ingsw.controller.actions.data;

import it.polimi.ingsw.controller.TurnChoice;
import it.polimi.ingsw.controller.actions.ActionData;

public class ChooseActionActionData extends ActionData {
    private TurnChoice choice;

    /**
     * Constructor for GSON
     */
    public ChooseActionActionData() {}

    public ChooseActionActionData(TurnChoice choice) {
        this.choice = choice;
    }

    public TurnChoice getChoice() {
        return choice;
    }
}
