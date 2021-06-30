package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.view.observer.player.LeadersToChooseFromObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LeadersToChooseFrom {
    /* OBSERVER */
    private final AtomicReference<LeadersToChooseFromObserver> leadersToChooseFromObserver;

    private List<LeadCard> leaders;

    public LeadersToChooseFrom() {
        this.leaders = new ArrayList<>();
        this.leadersToChooseFromObserver = new AtomicReference<>();
    }

    /**
     * Get the leaders to choose from.
     */
    public List<LeadCard> getLeaders() {
        return leaders;
    }

    /**
     * Set the leaders to choose from.
     */
    public synchronized void setLeaders(List<LeadCard> leaders){
        this.leaders = leaders;
        if(leadersToChooseFromObserver.get() != null) leadersToChooseFromObserver.get().onLeadersToChooseFromChange();
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

    /**
     * Set the observer of the leaders the player must choose from.
     * @param leadersToChooseFromObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(LeadersToChooseFromObserver leadersToChooseFromObserver) {
        this.leadersToChooseFromObserver.set(leadersToChooseFromObserver);
        leadersToChooseFromObserver.onLeadersToChooseFromChange();
    }
}
