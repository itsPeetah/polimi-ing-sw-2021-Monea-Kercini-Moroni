package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.general.ResourceType;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Game factory wrapper class.
 */
public class GameFactory {

    /**
     * Create a game with the default settings.
     */
    public static Game CreateGame(){
        return GameFactory.CreateGame(GameCustomizationSettings.getDefaultSettings());
    }

    /**
     * Create a game using custom settings.
     */
    public static Game CreateGame(GameCustomizationSettings settings){
        MarketTray mt = MarketTrayFactory.BuildMarketTray(settings.getMarketTrayLevel());
        DevCardMarket dcm = DevCardMarketFactory.BuildDevCardMarket(settings.getDevCardMarketLevel());
        return new Game(mt, dcm);
    }


}

