package it.polimi.ingsw.model.events;

/**
 * Abstract class defining a EventData object. For each possible Action, a new an extension of EventData must be defined.
 */
public abstract class EventData {
    private String player;

    public String getPlayer() {
        return player;
    }
}
