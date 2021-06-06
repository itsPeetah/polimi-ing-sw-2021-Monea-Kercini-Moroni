package it.polimi.ingsw.controller.model.actions;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.data.*;

import java.lang.reflect.Type;

/**
 * Enum of actions to be requested to the player.
 */
public enum Action {
    DISCONNECTED(NoneActionData.class),
    NONE(NoneActionData.class),
    CHOOSE_RESOURCE(ChooseResourceActionData.class),
    PUT_RESOURCES(PutResourcesActionData.class),
    CHOOSE_2_LEADERS(Choose2LeadersActionData.class),
    RESOURCE_MARKET(ResourceMarketActionData.class),
    DEV_CARD(DevCardActionData.class),
    PRODUCE(ProduceActionData.class),
    PlAY_LEADER(ChooseLeaderActionData.class),
    DISCARD_LEADER(ChooseLeaderActionData.class),
    REARRANGE_WAREHOUSE(NoneActionData.class),
    END_TURN(NoneActionData.class);

    private final Class<?> classOfData;

    Action(Class<?> classOfData) {
        this.classOfData = classOfData;
    }

    public Class<?> getClassOfData() {
        return classOfData;
    }

    /**
     * Parse a json string representing a DataEvent to the proper DataEvent object.
     * @param jsonData json formatted string representing a DataEvent
     * @return EventData object representing the correct DataEvent extension object of a certain Action
     */
    public <T extends ActionData> T fromJsonToData(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, (Type)classOfData);
    }
}
