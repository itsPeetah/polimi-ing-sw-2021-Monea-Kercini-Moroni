package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.view.observer.common.DevCardMarketObserver;
import it.polimi.ingsw.model.cards.DevCard;

import java.util.concurrent.atomic.AtomicReference;

public class DevCardMarket {
    /* OBSERVER */
    private final AtomicReference<DevCardMarketObserver> devCardMarketObserver;

    private DevCard[][] availableCards;

    public DevCardMarket() {
        this.availableCards = new DevCard[4][3];
        devCardMarketObserver = new AtomicReference<>();
    }

    /**
     * Get the available dev cards.
     */
    public synchronized DevCard[][] getAvailableCards() {
        return availableCards;
    }

    /**
     * Set the available dev cards.
     */
    public synchronized void setAvailableCards(DevCard[][] availableCards) {
        this.availableCards = availableCards;
        if(devCardMarketObserver.get() != null) devCardMarketObserver.get().onDevCardMarketChange();
    }

    /**
     * Set the observer of the dev card market.
     * @param devCardMarketObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(DevCardMarketObserver devCardMarketObserver) {
        this.devCardMarketObserver.set(devCardMarketObserver);
        devCardMarketObserver.onDevCardMarketChange();
    }
}
