package it.polimi.ingsw.network.server.metapackets.events.data;

import it.polimi.ingsw.network.server.metapackets.events.ActionData;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.Warehouse;

public class PutResourcesActionData extends ActionData {

    private Resources res;
    private Warehouse wh;

    /*Maybe a good idea for this class would be to transmit the warehouse as an array of resources from player
     and then easily build a new warehouse. It should be paid attention to the extra space leadcards could provide
     */

    /**
     * Constructor for GSON
     */
    public PutResourcesActionData() {}


    public Warehouse getWarehouse(){
        return wh;
    }
}
