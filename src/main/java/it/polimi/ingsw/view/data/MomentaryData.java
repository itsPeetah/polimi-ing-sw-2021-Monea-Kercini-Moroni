package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.player.LeadersToChooseFrom;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;


public class MomentaryData {

    private final LeadersToChooseFrom leaders;
    private final ResourcesToPut res;

    public MomentaryData() {
        leaders = new LeadersToChooseFrom();
        res = new ResourcesToPut();

    }

    /**
     * Get the resources to put in the warehouse (or to discard).
     */
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
