package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.network.server.metapackets.events.ActionData;

public class ResourceMarketActionData extends ActionData {
    private boolean row;
    private int index;

    /**
     * Constructor for GSON
     */
    public ResourceMarketActionData() {}

    public ResourceMarketActionData(boolean row, int index) {
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
