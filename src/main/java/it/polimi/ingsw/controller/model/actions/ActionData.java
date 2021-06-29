package it.polimi.ingsw.controller.model.actions;

/**
 * Abstract class defining a EventData object. For each possible Action, a new an extension of EventData must be defined.
 */
public abstract class ActionData {
    protected String player;

    /**
     * Get the player that has performed the action.
     * @return nickname of the player.
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Set the player that has performed the action.
     * @param player nickname of the player.
     */
    public void setPlayer(String player) {
        this.player = player;
    }
}
