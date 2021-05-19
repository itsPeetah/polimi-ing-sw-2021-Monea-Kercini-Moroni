package it.polimi.ingsw.model.game;

import it.polimi.ingsw.application.gui.Materials;
import it.polimi.ingsw.application.gui.MaterialsEnum;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

/**
 * Class representing the game's market's resource marbles.
 */
public class ResourceMarble{

    private Resources value;

    /**
     * Initialize the Marble with a single resource type.
     * @param type Resource type.
     * @param amount Resource amount.
     */
    public ResourceMarble(ResourceType type, int amount){
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

    /**
     *
     * @return the color of the Marble depending on it's resource as Materials Enum
     */
    public MaterialsEnum getMarbleColor(){

        for (ResourceType t: ResourceType.values()){

            //If resource has that value, it is that corresponding color

            if (this.getValue().getAmountOf(t)>0){

                switch (t){
                    case FAITH:
                        return MaterialsEnum.RED;
                    case SHIELDS:
                        return MaterialsEnum.BLUE;
                    case COINS:
                        return MaterialsEnum.YELLOW;
                    case STONES:
                        return MaterialsEnum.GRAY;
                    case SERVANTS:
                        return MaterialsEnum.PURPLE;

                }
            }
        }
        //If no resource was there it means it was a white marble
        return MaterialsEnum.WHITE;
    }
}
