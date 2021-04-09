package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.DevCardMarket;

import java.util.ArrayList;

public class DevCardMarketFactory {

    public static DevCardMarket BuildDevCardMarket(GameSettingsLevel cardAmount){

        ArrayList<DevCard> cards = CardManager.loadDevCardsFromJson();
        DevCardMarket dcm = new DevCardMarket(cards);
        return dcm;
    }

}
