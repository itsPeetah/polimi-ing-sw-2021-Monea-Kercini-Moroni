package it.polimi.ingsw.controller.model.updates;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.controller.model.updates.data.*;
import it.polimi.ingsw.util.JSONUtility;

import java.lang.reflect.Type;

public enum Update {
    EMPTY(EmptyUpdateData.class),
    CURRENT_PLAYER(CurrentPlayerUpdateData.class),
    RESOURCE_MARKET(ResourceMarketUpdateData.class),
    WAREHOUSE(WarehouseUpdateData.class),
    FAITH(FaithUpdateData.class),
    DEVCARD_MARKET(DevCardMarketUpdateData.class),
    PRODUCTION_POWERS(ProductionPowersUpdateData.class),
    LEADERS(PlayerLeadersUpdateData.class),
    VP(VPUpdateData.class),
    SOLO_ACTION(ActionTokenUpdateData.class),
    LEADERS_TO_CHOOSE_FROM(DisposableLeadersUpdateData.class),
    RESOURCES_TO_PUT(ResourcesToPutUpdateData.class);

    private final Class<?> classOfData;

    Update(Class<?> classOfData) {
        this.classOfData = classOfData;
    }
    
    /**
     * Parse a UpdateData object to a json string representing the correct subclass of UpdateData.
     * @return json formatted string representing an UpdateData object.
     */
    public String parseData(UpdateData data) {
        return JSONUtility.toJson(data, classOfData);
    }

    /**
     * Parse a UpdateData json formatted string to an object of the correct subclass of UpdateData.
     * @return object of the correct subclass of UpdateData.
     */
    public <T extends UpdateData> T getUpdateData(String updateDataString) {
        return JSONUtility.fromJson(updateDataString, (Type)classOfData);
    }
}
