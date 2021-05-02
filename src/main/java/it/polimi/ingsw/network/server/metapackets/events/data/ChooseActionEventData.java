package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.controller.TurnChoice;
import it.polimi.ingsw.network.server.metapackets.events.EventData;

public class ChooseActionEventData extends EventData {
    private TurnChoice choice;

    /**
     * Constructor for GSON
     */
    public ChooseActionEventData() {}

    public ChooseActionEventData(TurnChoice choice) {
        this.choice = choice;
    }

    public TurnChoice getChoice() {
        return choice;
    }
}
