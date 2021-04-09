package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.general.ResourceType;

import javax.swing.*;
import java.util.ArrayList;

public class GameFactory {

    /**
     * Custom game factory method.
     * @param resources Amount of resources in the market tray and market tray size.
     * @param devCards Amount of dev cards on the board.
     * @return The game instance
     */
    public static Game CreateGame(GameSettingsLevel resources, GameSettingsLevel devCards){
        MarketTray mt = MarketTrayFactory.BuildMarketTray(resources);
        DevCardMarket dcm = DevCardMarketFactory.BuildDevCardMarket(devCards);
        return new Game(mt,dcm);
    }


}

