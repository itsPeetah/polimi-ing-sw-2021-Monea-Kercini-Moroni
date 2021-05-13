package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.model.game.ResourceMarble;
import java.util.Observable;

public class MarketTray extends Observable {

    private ResourceMarble[][] available;
    private ResourceMarble[] waiting;

    public void setAvailable(ResourceMarble[][] available) {
        this.available = available;
        setChanged();
        notifyObservers(available);
    }

    public void setWaiting(ResourceMarble[] waiting) {
        this.waiting = waiting;
        setChanged();
        notifyObservers(waiting);
    }

    public void MarketTray(){
        available = new ResourceMarble[3][4];
        waiting = new ResourceMarble[1];
    }
}
