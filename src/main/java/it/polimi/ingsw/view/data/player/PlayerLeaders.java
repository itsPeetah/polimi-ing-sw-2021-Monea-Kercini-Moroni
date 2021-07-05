package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.view.observer.player.PlayerLeadersObserver;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerLeaders {
    /* OBSERVER */
    private final AtomicReference<PlayerLeadersObserver> playerLeadersObserver;

    private LeadCard[] leaders;
    private CardState[] states;

    public PlayerLeaders() {
        this.leaders = new LeadCard[2];
        this.states = new CardState[2];
        this.playerLeadersObserver = new AtomicReference<>();
    }

    /**
     * Get the lead cards of the player.
     */
    public synchronized LeadCard[] getLeaders() {
        return leaders;
    }

    /**
     * Get the states of the leaders of the player.
     */
    public synchronized CardState[] getStates() {
        return states;
    }

    /**
     * Set the lead cards of the player.
     */
    public synchronized void setLeaders(LeadCard[] leaders) {
        this.leaders = leaders;
        if(playerLeadersObserver.get() != null) playerLeadersObserver.get().onLeadersChange();
    }

    /**
     * Set the states of the leaders of the player.
     */
    public synchronized void setStates(CardState[] states) {
        this.states = states;
        if(playerLeadersObserver.get() != null) playerLeadersObserver.get().onLeadersStatesChange();
    }

    /**
     * Set the observer of the leaders of the player.
     * @param playerLeadersObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(PlayerLeadersObserver playerLeadersObserver) {
        this.playerLeadersObserver.set(playerLeadersObserver);
        playerLeadersObserver.onLeadersChange();
        playerLeadersObserver.onLeadersStatesChange();
    }

}
