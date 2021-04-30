package it.polimi.ingsw.model.events;

import com.google.gson.Gson;
import it.polimi.ingsw.model.events.data.*;

/**
 * Enum of actions to be requested to the player.
 */
public enum Action {
    NONE,
    CHOOSE_RESOURCE,
    PUT_RESOURCES,
    CHOOSE_2_LEADERS,
    CHOOSE_ACTION,
    RESOURCE_MARKET,
    DEV_CARD,
    PRODUCE,
    CHOOSE_LEADER;

    /**
     * Parse a json string representing a DataEvent to the proper DataEvent object.
     * @param jsonData json formatted string representing a DataEvent
     * @return EventData object representing the correct DataEvent extension object of a certain Action
     */
    public EventData parseData(String jsonData) {
        Gson gson = new Gson();
        EventData eventData;
        switch(this) {
            case CHOOSE_RESOURCE:
                eventData = gson.fromJson(jsonData, ChooseResourceEventData.class);
                break;

            case PUT_RESOURCES:
                eventData = gson.fromJson(jsonData, PutResourcesEventData.class);
                break;

            case CHOOSE_2_LEADERS:
                eventData = gson.fromJson(jsonData, Choose2LeadersEventData.class);
                break;

            case CHOOSE_ACTION:
                eventData = gson.fromJson(jsonData, ChooseActionEventData.class);
                break;

            case RESOURCE_MARKET:
                eventData = gson.fromJson(jsonData, ResourceMarketEventData.class);
                break;

            case PRODUCE:
                eventData = gson.fromJson(jsonData, ProduceEventData.class);
                break;

            case CHOOSE_LEADER:
                eventData = gson.fromJson(jsonData, ChooseLeaderEventData.class);
                break;

            default: eventData = new NoneEventData();
        }
        return eventData;
    }
}
