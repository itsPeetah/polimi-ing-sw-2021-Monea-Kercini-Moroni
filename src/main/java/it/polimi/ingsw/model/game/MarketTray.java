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
