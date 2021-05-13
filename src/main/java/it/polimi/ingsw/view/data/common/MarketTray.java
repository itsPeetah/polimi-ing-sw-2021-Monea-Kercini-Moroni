package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.model.game.ResourceMarble;
import java.util.Observable;

public class MarketTray extends Observable {

    private ResourceMarble[][] available;
    private ResourceMarble[] waiting;

    public synchronized void setAvailable(ResourceMarble[][] available) {
        this.available = available;
        setChanged();
        notifyObservers(available);
    }

    public synchronized void setWaiting(ResourceMarble[] waiting) {
        this.waiting = waiting;
        setChanged();
        notifyObservers(waiting);
    }

    public MarketTray(){
        available = new ResourceMarble[3][4];
        waiting = new ResourceMarble[1];
    }
}
