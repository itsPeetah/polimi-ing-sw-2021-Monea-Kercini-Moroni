package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.model.general.Resources;

public class ChooseResourceActionData extends ActionData {

    private Resources res;

    /**
     * Constructor for GSON
     */
    public ChooseResourceActionData() {}

    public ChooseResourceActionData(Resources res) {
        this.res = res;
    }

    public Resources getResources(){
        return res;
    }

    public void setRes(Resources res) {
        this.res = res;
    }
}
