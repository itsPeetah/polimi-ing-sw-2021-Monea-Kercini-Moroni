package it.polimi.ingsw.view.data.momentary;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.momentary.ResourceToPutObserver;

import java.util.concurrent.atomic.AtomicReference;

public class ResourcesToPut {
    /* OBSERVER */
    private final AtomicReference<ResourceToPutObserver> resourceToPutObserver;

    private Resources res;

    public ResourcesToPut() {
        this.res = new Resources();
        this.resourceToPutObserver = new AtomicReference<>();
    }

    /**
     * Set the resources to put.
     */
    public synchronized void setRes(Resources res) {
        this.res = res;
        if(resourceToPutObserver.get() != null) resourceToPutObserver.get().onResourcesToPutChange();
    }

    /**
     * Get the resources to put.
     */
    public synchronized Resources getRes() {
        return res;
    }

    /**
     * Set the observer of the resources to put.
     * @param resourceToPutObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(ResourceToPutObserver resourceToPutObserver) {
        this.resourceToPutObserver.set(resourceToPutObserver);
        resourceToPutObserver.onResourcesToPutChange();
    }
}
