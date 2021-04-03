package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

/**
 * Class representing the game's market's resource marbles.
 */
class ResourceMarble{

    private Resources value;

    /**
     * Initialize the Marble with a single resource type.
     * @param type Resource type.
     * @param amount Resource amount.
     */
    public ResourceMarble(ResourceType type, Integer amount){
        this.value = new Resources();
        this.value.add(type, amount);
    }

    /**
     * Initialize a "complex" marble by passing the resources.
     * @param resources Resources associated with the marble.
     */
    public ResourceMarble(Resources resources){
        this.value = resources;
    }

    /**
     * @return the resources associated with this marble.
     */
    public Resources getValue() {
        return value;
    }
}
