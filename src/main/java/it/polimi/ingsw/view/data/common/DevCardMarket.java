package it.polimi.ingsw.view.data.common;

import it.polimi.ingsw.model.cards.DevCard;

import java.util.Observable;

public class DevCardMarket extends Observable {

    private DevCard availableCards[][];

    public synchronized void setAvailableCards(DevCard[][] availableCards) {
        this.availableCards = availableCards;
        setChanged();
        notifyObservers(availableCards);
    }

    public DevCardMarket() {
        this.availableCards = new DevCard[4][3];
    }
}
