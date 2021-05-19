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

    public List<LeadCard> getLeaders() {
        return leaders;
    }

    @Override
    public String toString() {

        String result = "";

        LeadCard lc;
        for(int i = 0; i < leaders.size(); i++){
            lc = leaders.get(i);
            result += ("#" + (i+1) + ": " + lc.getCardId() + "\n");
            result += ("Victory Points: " + lc.getVictoryPoints() + "\n");
            result += ("Ability: " + lc.getAbility().toString() + "\n");
            result += ("Requirements: " + lc.getRequirements().toString() + "\n");
        }

        return result;
    }
}
