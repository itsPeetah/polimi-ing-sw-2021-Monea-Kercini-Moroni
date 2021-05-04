package it.polimi.ingsw.network.server.metapackets.actions;

/**
 * Abstract class defining a EventData object. For each possible Action, a new an extension of EventData must be defined.
 */
public abstract class ActionData {
    private String player;

    public String getPlayer() {
        return player;
    }
}
