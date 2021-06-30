package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.view.observer.player.FaithTrackObserver;

import java.util.concurrent.atomic.AtomicInteger;

public class FaithTrack {
    /* OBSERVER */
    private FaithTrackObserver faithTrackObserver;

    private AtomicInteger faith;
    private Boolean[] reportsAttended;

    public FaithTrack() {
        this.faith = new AtomicInteger(0);
        this.reportsAttended = new Boolean[3];
    }

    /**
     * Get the faith of the player.
     * @return position in the faith track.
     */
    public int getFaith() {
        return faith.get();
    }

    /**
     * Get the reports attended.
     */
    public synchronized Boolean[] getReportsAttended() {
        return reportsAttended;
    }

    /**
     * Set the faith of the player.
     * @param faith position in the faith track.
     */
    public void setFaith(int faith) {
        this.faith.set(faith);
        if(faithTrackObserver != null) faithTrackObserver.onFaithChange();
    }

    /**
     * Set the reports attended.
     */
    public synchronized void setReportsAttended(Boolean[] reportsAttended) {
        this.reportsAttended = reportsAttended;
        if(faithTrackObserver != null) faithTrackObserver.onReportsAttendedChange();
    }

    /**
     * Set the observer of the faith track of the player.
     * @param faithTrackObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(FaithTrackObserver faithTrackObserver) {
        this.faithTrackObserver = faithTrackObserver;
        faithTrackObserver.onFaithChange();
        faithTrackObserver.onReportsAttendedChange();
    }
}
