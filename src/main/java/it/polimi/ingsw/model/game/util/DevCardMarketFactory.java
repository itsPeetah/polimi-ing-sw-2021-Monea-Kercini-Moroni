package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.DevCardMarket;

public class DevCardMarketFactory {

    public static DevCardMarket BuildDevCardMarket(GameSettingsLevel cardAmount){

        DevCard[] cards = new DevCard[0];
        DevCardMarket dcm = new DevCardMarket(cards);
        return dcm;
    }

}
