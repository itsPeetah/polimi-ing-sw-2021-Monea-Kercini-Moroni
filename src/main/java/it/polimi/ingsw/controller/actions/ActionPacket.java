package it.polimi.ingsw.controller.actions;

public class ActionPacket {
    Action action;
    String data;

    /**
     * Constructor for GSON
     */
    public ActionPacket() {}

    public ActionPacket(Action action, String data) {
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
