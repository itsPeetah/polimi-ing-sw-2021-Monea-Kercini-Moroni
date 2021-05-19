package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.application.common.observer.MarketTrayObserver;
import it.polimi.ingsw.model.game.ResourceMarble;


public class MarketTray {
    /* OBSERVER */
    MarketTrayObserver marketTrayObserver;

    private ResourceMarble[][] available;
    private ResourceMarble[] waiting;

    public synchronized ResourceMarble[][] getAvailable() {
        return available;
    }

    public synchronized ResourceMarble[] getWaiting() {
        return waiting;
    }

    public synchronized void setAvailable(ResourceMarble[][] available) {
        this.available = available;
        if(marketTrayObserver != null) marketTrayObserver.onMarketTrayChange();
    }

    public synchronized void setWaiting(ResourceMarble[] waiting) {
        this.waiting = waiting;
        if(marketTrayObserver != null) marketTrayObserver.onMarketTrayChange();
    }

    public MarketTray(){
        available = new ResourceMarble[3][4];
        waiting = new ResourceMarble[1];
    }

    public void setObserver(MarketTrayObserver marketTrayObserver) {
        this.marketTrayObserver = marketTrayObserver;
        marketTrayObserver.onMarketTrayChange();
    }
}
