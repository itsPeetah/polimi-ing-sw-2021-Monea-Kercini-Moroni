package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.view.observer.player.DevCardsObserver;

public class DevCards {
    /* OBSERVER */
    private DevCardsObserver devCardsObserver;

    //keeping only the necessary 3 cards that are in view

    private DevCard[] devCards;

    public DevCards() {
        this.devCards = new DevCard[3];
    }

    /**
     * Set the dev cards of the player.
     * @param devCards dev cards of the player.
     */
    public synchronized void setDevCards(DevCard[] devCards) {
        this.devCards = devCards;
        if(devCardsObserver != null) devCardsObserver.onDevCardsChange();
    }

    /**
     * Get the dev cards of the player.
     * @return dev cards of the player.
     */
    public synchronized DevCard[] getDevCards() {
        return devCards;
    }

    /**
     * Set the observer of the dev cards of the player.
     * @param devCardsObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(DevCardsObserver devCardsObserver) {
        this.devCardsObserver = devCardsObserver;
        devCardsObserver.onDevCardsChange();
    }
}
