package it.polimi.ingsw.network.server.metapackets.events;

public class EventPacket {
    Action action;
    String data;

    /**
     * Constructor for GSON
     */
    public EventPacket() {}

    public EventPacket(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    /**
     * Get action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Get json formatted string of data
     */
    public String getData() {
        return data;
    }
}
