package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.view.observer.player.PlayerLeadersObserver;

public class PlayerLeaders {
    /* OBSERVER */
    private PlayerLeadersObserver playerLeadersObserver;

    private LeadCard[] leaders;
    private CardState[] states;

    public PlayerLeaders() {
        this.leaders = new LeadCard[2];
        this.states = new CardState[2];
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
        if(playerLeadersObserver != null) playerLeadersObserver.onLeadersChange();
    }

    /**
     * Set the states of the leaders of the player.
     */
    public synchronized void setStates(CardState[] states) {
        this.states = states;
        if(playerLeadersObserver != null) playerLeadersObserver.onLeadersStatesChange();
    }

    /**
     * Set the observer of the leaders of the player.
     * @param playerLeadersObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(PlayerLeadersObserver playerLeadersObserver) {
        this.playerLeadersObserver = playerLeadersObserver;
        playerLeadersObserver.onLeadersChange();
        playerLeadersObserver.onLeadersStatesChange();
    }

}
