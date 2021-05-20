package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.cards.LeadCard;

import java.util.ArrayList;
import java.util.List;

public class DisposableLeadersUpdateData implements UpdateData {
    
    List<LeadCard> leaders;
    String p;

    public DisposableLeadersUpdateData(List<LeadCard> leaders, String p) {
        this.leaders = leaders;
        this.p = p;
    }

    public List<LeadCard> getLeaders() {
        return leaders;
    }

    public String getP() {
        return p;
    }
}
