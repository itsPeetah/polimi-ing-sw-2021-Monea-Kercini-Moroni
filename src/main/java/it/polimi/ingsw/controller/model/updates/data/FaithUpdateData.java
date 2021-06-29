package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

//FaithUpdateData keeps track of all the players faith points
//Because everytime the faithUpdate is called, any player could have gotten faith points

public class FaithUpdateData implements UpdateData {

    private final String[] players;
    private final int[] fp;
    private final Boolean[][] reportsAttended; //The first one id for the player, the second for the reports he has attended

    /**
     * Create a faith update data.
     * @param fp array of faith positions of the players.
     * @param players nicknames of the players.
     * @param reports report attended.
     */
    public FaithUpdateData(int[] fp, String[] players, Boolean[][] reports) {
        this.fp = fp;
        this.players = players;
        this.reportsAttended = reports;
    }

    /**
     * Get the faith points.
     */
    public int[] getFp() {
        return fp;
    }

    /**
     * Get the players.
     */
    public String[] getPlayers() {
        return players;
    }

    /**
     * Get the report attended.
     */
    public Boolean[][] getReportsAttended() {
        return reportsAttended;
    }
}
