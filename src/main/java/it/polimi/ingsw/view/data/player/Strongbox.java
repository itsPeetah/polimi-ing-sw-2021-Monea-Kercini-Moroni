package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.general.Resources;

import java.util.Observable;

public class Strongbox extends Observable {

    private Resources content;

    public synchronized void setContent(Resources content) {
        this.content = content;
        setChanged();
        notifyObservers(content);
    }

    public Strongbox() {
        this.content = new Resources();
    }
}
