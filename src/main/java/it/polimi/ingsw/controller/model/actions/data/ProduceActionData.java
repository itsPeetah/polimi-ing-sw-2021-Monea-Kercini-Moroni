package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.model.general.Production;

import java.util.ArrayList;

public class ProduceActionData extends ActionData {
    private ArrayList<Production> chosenProductions;

    /**
     * Constructor for GSON
     */
    public ProduceActionData() {}

    /**
     * Create a new produce action data with the chosen productions.
     * @param chosenProductions chosen productions.
     */
    public ProduceActionData(ArrayList<Production> chosenProductions) {
        this.chosenProductions = chosenProductions;
    }

    /**
     * Get the chosen productions of the action data.
     */
    public ArrayList<Production> getChosenProd() {
        return chosenProductions;
    }
}
