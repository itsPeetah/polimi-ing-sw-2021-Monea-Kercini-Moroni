package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.view.observer.common.MarketTrayObserver;
import it.polimi.ingsw.model.game.ResourceMarble;

import java.util.concurrent.atomic.AtomicReference;


public class MarketTray {
    /* OBSERVER */
    private AtomicReference<MarketTrayObserver> marketTrayObserver;

    private ResourceMarble[][] available;
    private ResourceMarble[] waiting;

    public MarketTray(){
        available = new ResourceMarble[3][4];
        waiting = new ResourceMarble[1];
        marketTrayObserver = new AtomicReference<>();
    }

    /**
     * Get the available marbles.
     */
    public synchronized ResourceMarble[][] getAvailable() {
        return available;
    }

    /**
     * Get the waiting marble.
     */
    public synchronized ResourceMarble[] getWaiting() {
        return waiting;
    }

    /**
     * Set the available marbles.
     */
    public synchronized void setAvailable(ResourceMarble[][] available) {
        this.available = available;
        if(marketTrayObserver.get() != null) marketTrayObserver.get().onMarketTrayChange();
    }

    /**
     * Set the waiting marble.
     */
    public synchronized void setWaiting(ResourceMarble[] waiting) {
        this.waiting = waiting;
        if(marketTrayObserver.get() != null) marketTrayObserver.get().onMarketTrayChange();
    }

    /**
     * Set the observer of the market tray.
     * @param marketTrayObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(MarketTrayObserver marketTrayObserver) {
        this.marketTrayObserver.set(marketTrayObserver);
        marketTrayObserver.onMarketTrayChange();
    }
}
