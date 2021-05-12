package it.polimi.ingsw.view.data.player;

import java.util.Observable;

public class FaithTrack extends Observable {
    private int faith;
    private boolean[] reportsAttended;

    public void setFaith(int faith) {
        this.faith = faith;
        setChanged();
        notifyObservers(faith);
    }

    public void setReportsAttended(boolean[] reportsAttended) {
        this.reportsAttended = reportsAttended;
        setChanged();
        notifyObservers(reportsAttended);
    }

    public FaithTrack() {
        this.faith = 0;
        this.reportsAttended = new boolean[3];
    }
}
