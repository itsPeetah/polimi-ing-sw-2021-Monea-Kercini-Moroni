package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

public class VPUpdateData implements UpdateData {
    private final int[] vp;
    private final String[] players;

    /**
     * Create a victory points update data.
     * @param vp victory points of the update.
     * @param players nickname of the players of the update.
     */
    public VPUpdateData(int vp[], String players[]) {
        this.vp = vp;
        this.players = players;
    }

    /**
     * Get the victory points.
     * @return updated victory points.
     */
    public int[] getVP() {
        return vp;
    }

    /**
     * Get the players.
     * @return nicknames of the players.
     */
    public String[] getPlayers() {
        return players;
    }
}
