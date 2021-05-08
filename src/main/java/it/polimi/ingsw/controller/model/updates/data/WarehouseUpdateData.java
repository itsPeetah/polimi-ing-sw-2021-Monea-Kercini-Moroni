package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.playerboard.Warehouse;

public class WarehouseUpdateData implements UpdateData {
    Warehouse wh;

    public WarehouseUpdateData(Warehouse wh) {
        this.wh = wh;
    }

    public Warehouse getWh() {
        return wh;
    }
}
