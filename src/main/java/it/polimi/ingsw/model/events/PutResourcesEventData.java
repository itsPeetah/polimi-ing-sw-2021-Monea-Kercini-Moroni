package it.polimi.ingsw.model.events;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.Warehouse;

public class PutResourcesEventData extends EventData{

    private Resources res;
    private Warehouse wh;

    /*Maybe a good idea for this class would be to transmit the warehouse as an array of resources from player
     and then easily build a new warehouse. It should be paid attention to the extra space leadcards could provide
     */


    public Warehouse getWarehouse(){
        return wh;
    }
}
