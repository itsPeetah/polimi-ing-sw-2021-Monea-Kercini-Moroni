package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.MarketTray;

public class ResourceMarketUpdateData {

    MarketTray MT;

    public ResourceMarketUpdateData(MarketTray MT) {
        this.MT = MT;
    }

    public MarketTray getMT() {
        return MT;
    }
}
