package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.momentary.LeadersToChooseFrom;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;


public class MomentaryData {

    private LeadersToChooseFrom leaders;
    private ResourcesToPut res;

    public MomentaryData() {
        leaders = new LeadersToChooseFrom();
        res = new ResourcesToPut();

    }

    public LeadersToChooseFrom getLeaders() {
        return leaders;
    }

    public ResourcesToPut getResourcesToPut() {
        return res;
    }

    @Override
    public String toString() {
        return "Momentary Data{" +
                "leaders=" + leaders.toString() +
                ", resources=" + res.toString() +
                '}';
    }
}
