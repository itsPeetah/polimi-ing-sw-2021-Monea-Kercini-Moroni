package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.network.server.metapackets.events.ActionData;
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
}
