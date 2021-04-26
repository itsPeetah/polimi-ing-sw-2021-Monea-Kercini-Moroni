package it.polimi.ingsw.model.events.data;

import it.polimi.ingsw.controller.TurnChoice;
import it.polimi.ingsw.model.events.EventData;
import it.polimi.ingsw.model.general.Resources;

public class ChooseActionEventData extends EventData {
    private TurnChoice choice;

    public ChooseActionEventData(TurnChoice choice) {
        this.choice = choice;
    }

    public TurnChoice getChoice() {
        return choice;
    }
}
