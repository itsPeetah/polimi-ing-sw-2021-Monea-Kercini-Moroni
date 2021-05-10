package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerboard.ProductionPowers;

public class ProductionPowersUpdateData implements UpdateData {

    ProductionPowers pp;
    String p;

    public ProductionPowersUpdateData(ProductionPowers pp, String p) {
        this.pp = pp;
        this.p = p;
    }

    public String getPlayer() {
        return p;
    }

    public ProductionPowers getProductionPowers() {
        return pp;
    }
}
