package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.view.observer.common.DevCardMarketObserver;
import it.polimi.ingsw.model.cards.DevCard;

public class DevCardMarket {
    /* OBSERVER */
    DevCardMarketObserver devCardMarketObserver;

    private DevCard availableCards[][];

    public synchronized void setAvailableCards(DevCard[][] availableCards) {
        this.availableCards = availableCards;
        if(devCardMarketObserver != null) devCardMarketObserver.onDevCardMarketChange();
    }

    public DevCardMarket() {
        this.availableCards = new DevCard[4][3];
    }

    public void setObserver(DevCardMarketObserver devCardMarketObserver) {
        this.devCardMarketObserver = devCardMarketObserver;
        devCardMarketObserver.onDevCardMarketChange();
    }
}
