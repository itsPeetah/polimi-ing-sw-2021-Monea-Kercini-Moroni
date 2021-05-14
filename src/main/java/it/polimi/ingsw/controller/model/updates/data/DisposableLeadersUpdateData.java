package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.cards.LeadCard;

import java.util.ArrayList;

public class DisposableLeadersUpdateData implements UpdateData {
    
    ArrayList<LeadCard> leaders;

    public ArrayList<LeadCard> getLeaders() {
        return leaders;
    }

    public void setLeaders(ArrayList<LeadCard> leaders) {
        this.leaders = leaders;
    }
}
