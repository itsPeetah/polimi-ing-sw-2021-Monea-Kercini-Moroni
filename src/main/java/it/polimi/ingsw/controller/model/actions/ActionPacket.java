package it.polimi.ingsw.controller.model.actions;

public class ActionPacket {
    Action action;
    String data;

    /**
     * Constructor for GSON
     */
    public ActionPacket() {}

    /**
     * Create a new action packet.
     * @param action type of action.
     * @param data JSON string representing the corresponding action data.
     */
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
