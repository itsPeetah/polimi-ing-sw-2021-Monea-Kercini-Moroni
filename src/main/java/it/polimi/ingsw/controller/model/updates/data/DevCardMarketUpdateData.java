package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.game.DevCardMarket;

public class DevCardMarketUpdateData implements UpdateData {
    private DevCardMarket devCardMarket;

    /**
     * Create a dev card market update.
     * @param devCardMarket updated dev card market.
     */
    public DevCardMarketUpdateData(DevCardMarket devCardMarket) {
        this.devCardMarket = devCardMarket;
    }

    /**
     * Get the updated dev card market.
     */
    public DevCardMarket getDevCardMarket() {
        return devCardMarket;
    }
}
