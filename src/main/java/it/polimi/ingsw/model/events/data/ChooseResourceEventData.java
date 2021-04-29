package it.polimi.ingsw.model.events.data;

import it.polimi.ingsw.model.events.EventData;
import it.polimi.ingsw.model.general.Resources;

public class ChooseResourceEventData extends EventData {

    private Resources res;

    public ChooseResourceEventData(Resources res) {
        this.res = res;
    }

    public Resources getResources(){
        return res;
    }
}
