package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.network.server.metapackets.events.EventData;

public class DevCardEventData extends EventData {
    DevCard chosenCard;
    int position; //The position (pile) the player has chosen to put this dev card

    /**
     * Constructor for GSON
     */
    public DevCardEventData() {}

    public DevCardEventData(DevCard chosenCard) {
        this.chosenCard = chosenCard;
    }

    public DevCardEventData(int position) {
        this.position = position;
    }

    public DevCard getChooenCard() {
        return chosenCard;
    }

    public int getPosition() {
        return position;
    }
}
