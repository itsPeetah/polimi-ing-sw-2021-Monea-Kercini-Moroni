package it.polimi.ingsw.model.events.data;

import it.polimi.ingsw.model.events.EventData;

public class ResourceMarketEventData extends EventData {
    private boolean row;
    private int index;

    /**
     * Constructor for GSON
     */
    public ResourceMarketEventData() {}

    public ResourceMarketEventData(boolean row, int index) {
        this.row = row;
        this.index = index;
    }

    public boolean isRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }
}
