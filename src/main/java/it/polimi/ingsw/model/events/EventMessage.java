package it.polimi.ingsw.model.events;

public class EventMessage {
    Action action;
    String data;

    /**
     * Constructor for GSON
     */
    public EventMessage() {}

    public EventMessage(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}
