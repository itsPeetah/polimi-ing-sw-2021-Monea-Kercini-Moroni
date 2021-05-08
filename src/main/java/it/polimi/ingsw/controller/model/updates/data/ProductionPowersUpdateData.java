package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.playerboard.ProductionPowers;

public class ProductionPowersUpdateData implements UpdateData {

    ProductionPowers pp;

    public ProductionPowersUpdateData(ProductionPowers pp) {
        this.pp = pp;
    }

    public ProductionPowers getProductionPowers() {
        return pp;
    }
}
