package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.network.server.metapackets.events.EventData;
import it.polimi.ingsw.model.general.Resources;

public class ChooseResourceEventData extends EventData {

    private Resources res;

    /**
     * Constructor for GSON
     */
    public ChooseResourceEventData() {}

    public ChooseResourceEventData(Resources res) {
        this.res = res;
    }

    public Resources getResources(){
        return res;
    }
}
