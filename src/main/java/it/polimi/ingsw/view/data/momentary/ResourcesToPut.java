package it.polimi.ingsw.view.data.momentary;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.momentary.ResourceToPutObserver;

public class ResourcesToPut {
    /* OBSERVER */
    ResourceToPutObserver resourceToPutObserver;

    private Resources res;

    public ResourcesToPut() {
        this.res = new Resources();
    }

    public synchronized void setRes(Resources res) {
        this.res = res;
        if(resourceToPutObserver != null) resourceToPutObserver.onResourceToPutChange();
    }

    public synchronized Resources getRes() {
        return res;
    }

    public void setObserver(ResourceToPutObserver resourceToPutObserver) {
        this.resourceToPutObserver = resourceToPutObserver;
        resourceToPutObserver.onResourceToPutChange();
    }
}
