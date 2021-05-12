package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerboard.Warehouse;

public class WarehouseUpdateData implements UpdateData {

    Warehouse wh;
    String p; //Player whose warehouse has been updated

    public WarehouseUpdateData(Warehouse wh, String p) {
        this.wh = wh;
        this.p = p;
    }

    public Warehouse getWarehouse() {
        return wh;
    }

    public String getPlayer() {
        return p;
    }
}
