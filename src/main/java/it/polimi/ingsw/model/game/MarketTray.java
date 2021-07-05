package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.general.Resources;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


// add leader choice logic -> actually in controller

/**
 * Class representing the game's market tray.
 */
public class MarketTray {

    private final int rows;
    private final int columns;

    private final ResourceMarble[][] available;
    private final ResourceMarble[] waiting;

    // Initialization ---------------------------------------------------

    /**
     * Initialize the Market Tray with an available resources matrix and a waiting line for extra ones.
     * @param rows Matrix rows.
     * @param columns Matrix columns.
     * @param resourceMarbles Resources to be distributed in the tray.
     * @throws MarketTrayException if not enough marbles are provided to initialize the full tray.
     */
    public MarketTray(int rows, int columns, ArrayList<ResourceMarble> resourceMarbles) throws MarketTrayException {

        if(resourceMarbles.size() <= rows * columns)
            throw new MarketTrayException("Not enough resource marbles to correctly initialize the market tray");

        this.rows = rows;
        this.columns = columns;
        // Initialize the available marble matrix and the waiting line with the remaining size
        this.available = new ResourceMarble[rows][columns];
        this.waiting = new ResourceMarble[resourceMarbles.size() - rows * columns];

        // Randomize the picking order of the marbles
        Collections.shuffle(resourceMarbles);

        // Assign market tray elements by picking from the shuffled available marbles
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                available[i][j] = resourceMarbles.remove(0);
            }
        }
        // Put all the remaining marbles in the waiting queue
        for(int i = 0; i < waiting.length; i++) {
            waiting[i] = resourceMarbles.remove(0);
        }
    }

    @Override
    public String toString() {
        return "MarketTray{" +
                ", Currently available resources: " + Arrays.toString(available) +
                ", Queued resources: " + Arrays.toString(waiting) +
                '}';
    }

    // Getters ---------------------------------------------------

    /**
     * Market tray rows getter.
     */
    public int getRows() {return rows;}
    /**
     * Market tray columns getter.
     */
    public int getColumns() {return columns;}

    /**
     * Available resources getter.
     */
    public ResourceMarble[][] getAvailable() {return available;}
    /**
     * Waiting resources getter.
     */
    public ResourceMarble[] getWaiting() {return waiting;}

    // Interaction ---------------------------------------------------

    /**
     * Pick resources from a row in the market and let it slide.
     * @param index What row to pick resources from.
     * @return The total resources obtained from the market row.
     * @exception MarketTrayException if the index is out of bounds.
     */
    public Resources pickRow(int index) throws MarketTrayException {
        if(index >= rows) throw new MarketTrayException("The index was out of bounds.");

        // Create result and add resources from the selected row
        Resources result = new Resources();
        for(int i = 0; i < columns; i++){
            result.add(available[index][i].getValue());
        }
        // Update the market tray
        slideRow(index);
        // Return the obtained resources
        return result;
    }

    /**
     * Pick resources from a column in the market and let it slide.
     * @param index What column to pick resources from.
     * @return The total resources obtained from the market column.
     * @exception MarketTrayException if the index is out of bounds.
     */
    public Resources pickColumn(int index) throws MarketTrayException {
        if(index >= columns) throw new MarketTrayException("The index was out of bounds.");

        // Create result and add resources from the selected column
        Resources result = new Resources();
        for(int i = 0; i < rows; i++){
            result.add(available[i][index].getValue());
        }
        // Update the market tray
        slideColumn(index);
        // Return obtained resources
        return result;
    }

    /**
     * Slide a row and the waiting line.
     * @param index Row to slide.
     */
    private void slideRow(int index){
        // Get first element out
        ResourceMarble firstInRow = available[index][0];
        // Slide row
        for(int i = 1; i < columns; i++)
            available[index][i-1] = available[index][i];
        // Put first element waiting in
        available[index][columns-1] = waiting[0];
        // Slide waiting line
        for(int i = 1; i < waiting.length; i++)
            waiting[i-1] = waiting[i];
        // Put first element back in waiting line
        waiting[waiting.length-1] = firstInRow;
    }

    /**
     * Slide a column and the waiting line.
     * @param index Column to slide.
     */
    private void slideColumn(int index){
        // Get first element out
        ResourceMarble firstInColumn = available[0][index];
        // Slide column
        for(int i = 1; i < rows; i++)
            available[i-1][index] = available[i][index];
        // Put first element waiting in
        available[rows-1][index] = waiting[0];
        // Slide waiting line
        for(int i = 1; i < waiting.length; i++)
            waiting[i-1] = waiting[i];
        // Put first element back in waiting line
        waiting[waiting.length-1] = firstInColumn;
    }


}