package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.model.playerboard.Warehouse;

public class WarehouseUpdateData {
    Warehouse wh;

    public WarehouseUpdateData(Warehouse wh) {
        this.wh = wh;
    }

    public Warehouse getWh() {
        return wh;
    }
}
