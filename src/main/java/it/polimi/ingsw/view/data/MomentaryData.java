package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.player.LeadersToChooseFrom;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;


public class MomentaryData {
    private final ResourcesToPut res;

    public MomentaryData() {
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
                "resources=" + res.toString() +
                '}';
    }
}
