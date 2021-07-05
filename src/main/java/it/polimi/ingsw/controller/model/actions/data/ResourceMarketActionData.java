package it.polimi.ingsw.controller.model.actions.data;

import it.polimi.ingsw.controller.model.actions.ActionData;

public class ResourceMarketActionData extends ActionData {
    private boolean row;
    private int index;

    /**
     * Constructor for GSON
     */
    public ResourceMarketActionData() {}

    /**
     * Create a resources market action data.
     * @param row whether the chosen line is a row or a column.
     * @param index index of the row / column.
     */
    public ResourceMarketActionData(boolean row, int index) {
        this.row = row;
        this.index = index;
    }

    /**
     * Check if the chosen line is a row or a column
     * @return true if the line is a row, false otherwise.
     */
    public boolean isRow() {
        return row;
    }

    /**
     * Get the index of the chosen row / column.
     * @return index of the chosen row / column in the market tray.
     */
    public int getIndex() {
        return index;
    }
}
