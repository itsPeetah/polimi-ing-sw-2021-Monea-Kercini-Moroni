package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.general.Resources;

public class ResourcesToPutUpdateData implements UpdateData {
    private final Resources res;

    /**
     * Create a resources to put update data.
     * @param res resources that a player must place in their warehouse.
     */
    public ResourcesToPutUpdateData(Resources res) {
        this.res = res;
    }

    /**
     * Get the resources of the update.
     * @return resources that a player must place in their warehouse.
     */
    public Resources getRes() {
        return res;
    }
}
