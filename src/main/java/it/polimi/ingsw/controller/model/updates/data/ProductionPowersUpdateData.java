package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerboard.ProductionPowers;

public class ProductionPowersUpdateData implements UpdateData {

    ProductionPowers pp;
    String p;

    /**
     * Create a production powers update data.
     * @param pp new production powers.
     * @param p nickname of the player.
     */
    public ProductionPowersUpdateData(ProductionPowers pp, String p) {
        this.pp = pp;
        this.p = p;
    }

    /**
     * Get the player of the update.
     * @return nickname of the player.
     */
    public String getPlayer() {
        return p;
    }

    /**
     * Get the updated production powers.
     */
    public ProductionPowers getProductionPowers() {
        return pp;
    }
}
