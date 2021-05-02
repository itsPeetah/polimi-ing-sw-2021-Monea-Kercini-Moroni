package it.polimi.ingsw.network.server.metapackets.events;

import com.google.gson.Gson;
import it.polimi.ingsw.network.server.metapackets.events.data.*;

import java.lang.reflect.Type;

/**
 * Enum of actions to be requested to the player.
 */
public enum Action {
    NONE(NoneEventData.class),
    CHOOSE_RESOURCE(ChooseResourceEventData.class),
    PUT_RESOURCES(PutResourcesEventData.class),
    CHOOSE_2_LEADERS(Choose2LeadersEventData.class),
    CHOOSE_ACTION(ChooseActionEventData.class),
    RESOURCE_MARKET(ResourceMarketEventData.class),
    DEV_CARD(DevCardEventData.class),
    PRODUCE(ProduceEventData.class),
    CHOOSE_LEADER(ChooseLeaderEventData.class);

    private final Class<?> classOfData;

    Action(Class<?> classOfData) {
        this.classOfData = classOfData;
    }

    /**
     * Parse a json string representing a DataEvent to the proper DataEvent object.
     * @param jsonData json formatted string representing a DataEvent
     * @return EventData object representing the correct DataEvent extension object of a certain Action
     */
    public EventData parseData(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, (Type)classOfData);
    }
}
