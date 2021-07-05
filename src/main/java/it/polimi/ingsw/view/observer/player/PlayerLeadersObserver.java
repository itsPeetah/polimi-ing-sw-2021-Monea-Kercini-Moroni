package it.polimi.ingsw.view.observer.player;

public interface PlayerLeadersObserver {

    /**
     * On a leaders change.
     */
    void onLeadersChange();

    /**
     * On a leaders' states change.
     */
    void onLeadersStatesChange();

}
