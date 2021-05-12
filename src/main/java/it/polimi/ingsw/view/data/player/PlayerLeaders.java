package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.playerleaders.CardState;

import java.util.Observable;

public class PlayerLeaders extends Observable {
    private LeadCard[] leaders;
    private CardState[] states;

    public void setLeaders(LeadCard[] leaders) {
        this.leaders = leaders;
        setChanged();
        notifyObservers(leaders);
    }

    public void setStates(CardState[] states) {
        this.states = states;
        setChanged();
        notifyObservers(states);
    }

    public PlayerLeaders() {
        this.leaders = new LeadCard[2];
        this.states = new CardState[2];
    }

}
