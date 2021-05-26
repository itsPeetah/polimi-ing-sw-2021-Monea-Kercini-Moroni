package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.player.WarehouseObserver;

public class Warehouse {
    /* OBSERVER */
    WarehouseObserver warehouseObserver;

    private Resources content[];
    private Resources extra[];
    private LeadCard activatedLeaders[];

    public synchronized void setContent(Resources[] content) {
        this.content = content;
        if(warehouseObserver != null) warehouseObserver.onWarehouseContentChange();
    }

    public synchronized Resources[] getContent() {
        return content;
    }

    public synchronized void setExtra(Resources extra[]) {
        this.extra = extra;
        if(warehouseObserver != null) warehouseObserver.onWarehouseExtraChange();
    }

    public synchronized Resources[] getExtra() {
        return extra;
    }

    public Warehouse() {
        this.content = new Resources[3];
        this.extra = new Resources[2];
        this.activatedLeaders = new LeadCard[2];
    }

    public LeadCard[] getActivatedLeaders() {
        return activatedLeaders;
    }

    public void setActivatedLeaders(LeadCard[] activatedLeaders) {
        this.activatedLeaders = activatedLeaders;
    }

    public void setObserver(WarehouseObserver warehouseObserver) {
        this.warehouseObserver = warehouseObserver;
        warehouseObserver.onWarehouseContentChange();
        warehouseObserver.onWarehouseExtraChange();
    }
}
