package it.polimi.ingsw.view.observer.player;

public interface WarehouseObserver {

    /**
     * On a warehouse content change.
     */
    void onWarehouseContentChange();

    /**
     * On a warehouse extra change.
     */
    void onWarehouseExtraChange();

}
