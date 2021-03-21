package it.polimi.ingsw.module.cards;

import it.polimi.ingsw.module.general.Resources;

public abstract class Card {
    private Integer victoryPoints;
    private String cardId;

    public Card(Integer victoryPoints, String cardId) {
        this.victoryPoints = victoryPoints;
        this.cardId = cardId;
    }

    public Integer getVictoryPoints() {
        return victoryPoints;
    }

    public String getCardId() {
        return cardId;
    }

    public abstract Boolean affordable(Resources playerResources);

    public abstract void play();
}
