package it.polimi.ingsw.network.server.metapackets.actions.data;

import it.polimi.ingsw.network.server.metapackets.actions.ActionData;
import it.polimi.ingsw.model.general.Production;

public class ProduceActionData extends ActionData {
    Production chosenProd;

    /**
     * Constructor for GSON
     */
    public ProduceActionData() {}

    public ProduceActionData(Production chosenProd) {
        this.chosenProd = chosenProd;
    }

    public Production getChosenProd() {
        return chosenProd;
    }
}
