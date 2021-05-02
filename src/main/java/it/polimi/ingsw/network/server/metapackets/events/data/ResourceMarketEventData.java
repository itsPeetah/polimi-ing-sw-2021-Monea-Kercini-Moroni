package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.network.server.metapackets.events.EventData;

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
