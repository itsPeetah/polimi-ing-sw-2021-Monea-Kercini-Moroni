package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.player.WarehouseObserver;

public class Warehouse {
    /* OBSERVER */
    private WarehouseObserver warehouseObserver;

    private Resources[] content;
    private Resources[] extra;
    private LeadCard[] activatedLeaders;

    public Warehouse() {
        this.content = new Resources[3];
        this.extra = new Resources[2];
        this.activatedLeaders = new LeadCard[2];
    }

    /**
     * Set the content of the warehouse.
     */
    public synchronized void setContent(Resources[] content) {
        this.content = content;
        if(warehouseObserver != null) warehouseObserver.onWarehouseContentChange();
    }

    /**
     * Get the content of the warehouse.
     */
    public synchronized Resources[] getContent() {
        return content;
    }

    /**
     * Set the extra space of the warehouse.
     */
    public synchronized void setExtra(Resources[] extra) {
        this.extra = extra;
        if(warehouseObserver != null) warehouseObserver.onWarehouseExtraChange();
    }

    /**
     * Get the extra space of the warehouse.
     */
    public synchronized Resources[] getExtra() {
        return extra;
    }

    /**
     * Get the activated leaders.
     */
    public synchronized LeadCard[] getActivatedLeaders() {
        return activatedLeaders;
    }

    /**
     * Set the activated leaders.
     */
    public synchronized void setActivatedLeaders(LeadCard[] activatedLeaders) {
        this.activatedLeaders = activatedLeaders;
    }

    /**
     * Set the observer of the warehouse of the player.
     * @param warehouseObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(WarehouseObserver warehouseObserver) {
        this.warehouseObserver = warehouseObserver;
        warehouseObserver.onWarehouseContentChange();
        warehouseObserver.onWarehouseExtraChange();
    }
}
