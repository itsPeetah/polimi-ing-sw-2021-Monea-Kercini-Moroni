package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.view.observer.player.FaithTrackObserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FaithTrack {
    /* OBSERVER */
    private final AtomicReference<FaithTrackObserver> faithTrackObserver;

    private final AtomicInteger faith;
    private Boolean[] reportsAttended;

    public FaithTrack() {
        this.faith = new AtomicInteger(0);
        this.reportsAttended = new Boolean[3];
        this.faithTrackObserver = new AtomicReference<>();
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
        if(faithTrackObserver.get() != null) faithTrackObserver.get().onFaithChange();
    }

    /**
     * Set the reports attended.
     */
    public synchronized void setReportsAttended(Boolean[] reportsAttended) {
        this.reportsAttended = reportsAttended;
        if(faithTrackObserver.get() != null) faithTrackObserver.get().onReportsAttendedChange();
    }

    /**
     * Set the observer of the faith track of the player.
     * @param faithTrackObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(FaithTrackObserver faithTrackObserver) {
        this.faithTrackObserver.set(faithTrackObserver);
        faithTrackObserver.onFaithChange();
        faithTrackObserver.onReportsAttendedChange();
    }
}
