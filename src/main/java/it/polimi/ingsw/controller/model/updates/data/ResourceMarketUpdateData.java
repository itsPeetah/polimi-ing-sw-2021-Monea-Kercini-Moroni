package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.MarketTray;

public class ResourceMarketUpdateData implements UpdateData {

    private final MarketTray MT;

    /**
     * Create a resource market update data.
     * @param MT updated resource market.
     */
    public ResourceMarketUpdateData(MarketTray MT) {
        this.MT = MT;
    }

    /**
     * Get the resource market of the update.
     * @return updated resource market.
     */
    public MarketTray getMT() {
        return MT;
    }
}
