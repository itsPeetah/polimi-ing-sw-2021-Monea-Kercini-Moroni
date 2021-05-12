package it.polimi.ingsw.controller.model.updates;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.updates.data.*;

public enum Update {
    EMPTY(EmptyUpdateData.class),
    RESOURCE_MARKET(ResourceMarketUpdateData.class),
    WAREHOUSE(WarehouseUpdateData.class),
    FAITH(FaithUpdateData.class),
    DEVCARD_MARKET(DevCardMarketUpdateData.class),
    PRODUCTION_POWERS(ProductionPowersUpdateData.class),
    LEADERS(PlayerLeadersUpdateData.class),
    VP(VPUpdateData.class),
    SOLO_ACTION(ActionTokenUpdateData.class);

    private final Class<?> classOfData;

    Update(Class<?> classOfData) {
        this.classOfData = classOfData;
    }
    
    /**
     * Parse a UpdateData object to a json string representing the correct subclass of UpdateData.
     * @return json formatted string representing an UpdateData object.
     */
    public String parseData(UpdateData data) {
        Gson gson = new Gson();
        return gson.toJson(data, classOfData);
    }
}
