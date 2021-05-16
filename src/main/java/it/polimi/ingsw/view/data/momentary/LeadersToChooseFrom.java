package it.polimi.ingsw.view.data.momentary;

import it.polimi.ingsw.model.cards.LeadCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class LeadersToChooseFrom extends Observable {

    private List<LeadCard> leaders;

    public synchronized void setLeaders(List<LeadCard> leaders){
        this.leaders = leaders;
        setChanged();
        notifyObservers(leaders);
    }

    public LeadersToChooseFrom() {
        this.leaders = new ArrayList<>();
    }

}
