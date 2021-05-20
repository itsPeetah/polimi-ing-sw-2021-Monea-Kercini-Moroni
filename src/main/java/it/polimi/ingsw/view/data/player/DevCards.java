package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.view.observer.player.DevCardsObserver;

public class DevCards {
    /* OBSERVER */
    DevCardsObserver devCardsObserver;

    //keeping only the necessary 3 cards that are in view

    private DevCard[] devCards;

    public synchronized void setDevCards(DevCard[] devCards) {
        this.devCards = devCards;
        if(devCardsObserver != null) devCardsObserver.onDevCardsChange();
    }

    public DevCards() {
        this.devCards = new DevCard[3];
    }

    public void setObserver(DevCardsObserver devCardsObserver) {
        this.devCardsObserver = devCardsObserver;
        devCardsObserver.onDevCardsChange();
    }
}
