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

        StringBuilder sb = new StringBuilder();
        /*System.out.println(leaders.size());*/

        LeadCard lc;
        for(int i = 0; i < leaders.size(); i++){
            lc = leaders.get(i);
            sb.append("#" + (i+1) + ": " + lc.getCardId() + "\n");
            sb.append("Victory Points: " + lc.getVictoryPoints() + "\n");
            sb.append("Ability: " + lc.getAbility().toString() + "\n");
            sb.append("Requirements: " + lc.getRequirements().toString() + "\n");
        }

        return sb.toString();
    }
}
