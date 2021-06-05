package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

//FaithUpdateData keeps track of all the players faith points
//Because everytime the faithUpdate is called, any player could have gotten faith points

public class FaithUpdateData implements UpdateData {

    String[] players;
    int[] fp;
    Boolean[][] reportsAttended; //The first one id for the player, the second for the reports he has attended


    public FaithUpdateData(int[] fp, String[] players, Boolean[][] reports) {
        this.fp = fp;
        this.players = players;
        this.reportsAttended = reports;
    }

    public int[] getFp() {
        return fp;
    }

    public String[] getPlayers() {
        return players;
    }

    public Boolean[][] getReportsAttended() {
        return reportsAttended;
    }
}
