package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.network.server.metapackets.events.EventData;
import it.polimi.ingsw.model.general.Production;

public class ProduceEventData extends EventData {
    Production chosenProd;

    /**
     * Constructor for GSON
     */
    public ProduceEventData() {}

    public ProduceEventData(Production chosenProd) {
        this.chosenProd = chosenProd;
    }

    public Production getChosenProd() {
        return chosenProd;
    }
}
