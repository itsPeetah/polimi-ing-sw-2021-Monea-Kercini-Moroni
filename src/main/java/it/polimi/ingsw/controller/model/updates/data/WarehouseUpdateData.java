package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerboard.Strongbox;
import it.polimi.ingsw.model.playerboard.Warehouse;

public class WarehouseUpdateData implements UpdateData {

    private final Warehouse wh;
    private final Strongbox sb;
    private final String p; //Player whose warehouse has been updated

    /**
     * Create a warehouse update data.
     * @param wh updated warehouse.
     * @param sb updated strongbox.
     * @param p nickname of the player.
     */
    public WarehouseUpdateData(Warehouse wh, Strongbox sb, String p) {
        this.wh = wh;
        this.p = p;
        this.sb = sb;
    }

    /**
     * Get the warehouse.
     * @return updated warehouse.
     */
    public Warehouse getWarehouse() {
        return wh;
    }

    /**
     * Get the strongbox.
     * @return updated strongbox.
     */
    public Strongbox getStrongbox() {
        return sb;
    }

    /**
     * Get the player.
     * @return nickname of the player.
     */
    public String getPlayer() {
        return p;
    }
}
