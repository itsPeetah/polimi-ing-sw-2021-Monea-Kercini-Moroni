package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.model.game.DevCardMarket;

public class DevCardMarketUpdateData {
    DevCardMarket devCardMarket;

    public DevCardMarketUpdateData(DevCardMarket devCardMarket) {
        this.devCardMarket = devCardMarket;
    }

    public DevCardMarket getDevCardMarket() {
        return devCardMarket;
    }
}
