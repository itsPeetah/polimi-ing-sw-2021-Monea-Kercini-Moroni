package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.model.game.ResourceMarble;
import java.util.Observable;

import static it.polimi.ingsw.application.gui.scenes.GUIPreGame.updateGUIMarketTrayWaiting;

public class MarketTray extends Observable {

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
        setChanged();
        notifyObservers(available);
    }

    public synchronized void setWaiting(ResourceMarble[] waiting) {
        this.waiting = waiting;
        setChanged();
        notifyObservers(waiting);
        updateGUIMarketTrayWaiting();
    }

    public MarketTray(){
        available = new ResourceMarble[3][4];
        waiting = new ResourceMarble[1];
    }
}
