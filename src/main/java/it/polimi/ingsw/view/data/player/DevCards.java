package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.cards.DevCard;
import java.util.Observable;

public class DevCards extends Observable {

    //keeping only the necessary 3 cards that are in view

    private DevCards[] devCards;

    public void setDevCards(DevCards[] devCards) {
        this.devCards = devCards;
        setChanged();
        notifyObservers(devCards);
    }

    public DevCards() {
        this.devCards = new DevCards[3];
    }
}
