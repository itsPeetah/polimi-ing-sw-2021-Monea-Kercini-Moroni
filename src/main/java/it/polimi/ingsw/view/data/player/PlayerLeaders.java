package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.view.observer.player.PlayerLeadersObserver;

public class PlayerLeaders {
    /* OBSERVER */
    PlayerLeadersObserver playerLeadersObserver;

    private LeadCard[] leaders;
    private CardState[] states;

    public synchronized void setLeaders(LeadCard[] leaders) {
        this.leaders = leaders;
        if(playerLeadersObserver != null) playerLeadersObserver.onLeadersChange();
    }

    public synchronized void setStates(CardState[] states) {
        this.states = states;
        if(playerLeadersObserver != null) playerLeadersObserver.onLeadersStatesChange();
    }

    public PlayerLeaders() {
        this.leaders = new LeadCard[2];
        this.states = new CardState[2];
    }

    public void setObserver(PlayerLeadersObserver playerLeadersObserver) {
        this.playerLeadersObserver = playerLeadersObserver;
        playerLeadersObserver.onLeadersChange();
        playerLeadersObserver.onLeadersStatesChange();
    }

}
