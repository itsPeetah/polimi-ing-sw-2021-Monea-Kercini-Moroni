package it.polimi.ingsw.view.data.momentary;

import it.polimi.ingsw.model.general.Resources;

import java.util.Observable;

public class ResourcesToPut extends Observable {
    private Resources res;

    public ResourcesToPut() {
        this.res = new Resources();
    }

    public synchronized void setRes(Resources res) {
        this.res = res;
        setChanged();
        notifyObservers(res);
    }
}
