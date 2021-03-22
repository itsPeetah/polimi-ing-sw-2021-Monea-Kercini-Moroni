package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

public class MarketTray {

    private final int rows;
    private final int columns;

    private ResourceMarble[][] available;
    private ResourceMarble waiting;

    public MarketTray(int rows, int columns, Resources trayResources) {
        this.rows = rows;
        this.columns = columns;
        this.available = new ResourceMarble[rows][columns];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < rows; j++){
                // Take one random resource per spot
                // Create the marble
                // Assign
            }
        }

        // Add available
    }

    public Resources pickRow(int index){
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

    public Resources pickColumn(int index){
        // Create result and add resources from the selected column
        Resources result = new Resources();
        for(int i = 0; i < rows; i++){
            result.add(available[i][index].getValue());
        }
        // Update the market tray
        slideColumn(index);

        return result;
    }

    private void slideRow(int index){

    }

    private void slideColumn(int index){

    }


}

class ResourceMarble{

    private Resources value;

    public ResourceMarble(ResourceType type, Integer amount){
        this.value = new Resources();
        this.value.add(type, amount);
    }

    public Resources getValue() {
        return value;
    }
}
