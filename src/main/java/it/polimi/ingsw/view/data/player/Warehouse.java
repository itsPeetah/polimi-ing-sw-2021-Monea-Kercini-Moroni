package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.player.WarehouseObserver;

public class Warehouse {
    /* OBSERVER */
    WarehouseObserver warehouseObserver;

    private Resources content[];
    private Resources extra[];

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
    }

    public void setObserver(WarehouseObserver warehouseObserver) {
        this.warehouseObserver = warehouseObserver;
        warehouseObserver.onWarehouseContentChange();
        warehouseObserver.onWarehouseExtraChange();
    }
}
