package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.general.Resources;

import java.util.Observable;

public class Warehouse extends Observable {
    private Resources content[];

    public void setContent(Resources[] content) {
        this.content = content;
        setChanged();
        notifyObservers(content);
    }

    public Warehouse() {
        this.content = new Resources[3];
    }
}
