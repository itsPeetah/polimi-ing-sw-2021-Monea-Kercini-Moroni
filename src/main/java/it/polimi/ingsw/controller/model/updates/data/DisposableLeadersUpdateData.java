package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.cards.LeadCard;

import java.util.ArrayList;
import java.util.List;

public class DisposableLeadersUpdateData implements UpdateData {
    
    List<LeadCard> leaders;

    public DisposableLeadersUpdateData(ArrayList<LeadCard> leaders) {
        this.leaders = leaders;
    }

    public List<LeadCard> getLeaders() {
        return leaders;
    }
}
