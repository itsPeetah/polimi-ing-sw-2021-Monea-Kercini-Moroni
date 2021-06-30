package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.Warehouse;

public class PutResourcesActionData extends ActionData {

    private Warehouse wh;

    /**
     * Constructor for GSON
     */
    public PutResourcesActionData() {}

    /**
     * Get the warehouse of the put resources action data.
     * @return new warehouse after the action is performed.
     */
    public Warehouse getWarehouse(){
        return wh;
    }

    /**
     * Set the new warehouse of the put resources action data.
     * @param wh new warehouse after the action is performed.
     */
    public void setWh(Warehouse wh) {
        this.wh = wh;
    }
}
