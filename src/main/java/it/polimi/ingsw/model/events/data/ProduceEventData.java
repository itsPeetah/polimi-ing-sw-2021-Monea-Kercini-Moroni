package it.polimi.ingsw.model.events.data;

import it.polimi.ingsw.model.events.EventData;
import it.polimi.ingsw.model.general.Production;

public class ProduceEventData extends EventData {
    Production chosenProd;

    public ProduceEventData(Production chosenProd) {
        this.chosenProd = chosenProd;
    }

    public Production getChosenProd() {
        return chosenProd;
    }
}
