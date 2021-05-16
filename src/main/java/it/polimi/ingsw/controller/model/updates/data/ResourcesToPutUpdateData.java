package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.general.Resources;

public class ResourcesToPutUpdateData implements UpdateData {
    Resources res;

    public ResourcesToPutUpdateData(Resources res) {
        this.res = res;
    }

    public Resources getRes() {
        return res;
    }
}
