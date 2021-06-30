package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.model.general.Resources;

public class ChooseResourceActionData extends ActionData {

    private Resources res;

    /**
     * Constructor for GSON
     */
    public ChooseResourceActionData() {}

    /**
     * Get resources of the action data.
     * @return chosen resources.
     */
    public Resources getResources(){
        return res;
    }

    /**
     * Set resources of the action data.
     * @param res chosen resources.
     */
    public void setRes(Resources res) {
        this.res = res;
    }
}
