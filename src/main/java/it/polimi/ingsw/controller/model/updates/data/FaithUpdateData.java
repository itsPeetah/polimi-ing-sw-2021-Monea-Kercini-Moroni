package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

//FaithUpdateData keeps track of all the players faith points
//Because everytime the faithUpdate is called, any player could have gotten faith points

public class FaithUpdateData implements UpdateData {

    int fp[];

    //returns the faith points of the player in that position
    public int getFaithPoints(int p) {
        return fp[p];
    }

    public FaithUpdateData(int[] fp) {
        this.fp = fp;
    }
}
