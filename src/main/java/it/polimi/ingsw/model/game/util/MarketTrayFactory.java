package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.game.MarketTray;
import it.polimi.ingsw.model.game.MarketTrayException;
import it.polimi.ingsw.model.game.ResourceMarble;
import it.polimi.ingsw.model.general.ResourceType;

import java.util.ArrayList;

/**
 * MarketTray factory method wrapper class.
 */
public class MarketTrayFactory {
    /**
     * Build a market tray based on the settings level.
     */
    public static MarketTray BuildMarketTray(GameSettingsLevel resourceAmount){
        // Market tray parameters
        ArrayList<ResourceMarble> marketTrayResources;
        int marketTrayRows, marketTrayColumns;
        // Initialize parameters based on the settings
        switch (resourceAmount){
            case LOW:
                marketTrayRows = 2; marketTrayColumns = 3;
                marketTrayResources = GetMarketResources(1,1,1,1,1,2);
                break;
            case MEDIUM:
                marketTrayRows = 3; marketTrayColumns = 4;
                marketTrayResources = GetMarketResources(2,2,2,2,1,4);
                break;
            default:
                marketTrayRows = 4; marketTrayColumns = 5;
                marketTrayResources = GetMarketResources(4,4,4,4,2,6);
                break;
        }
        // Instantiate and return market tray
        MarketTray mt = null;
        try { mt = new MarketTray(marketTrayRows, marketTrayColumns, marketTrayResources); }
        catch(MarketTrayException mte) { mte.printStackTrace(); }
        return mt;
    }
    /**
     * Returns an array list containing the specified amount of marbles per resource.
     */
    private static ArrayList<ResourceMarble> GetMarketResources(int stone, int coins, int servants, int shields, int faith, int blank){
        ArrayList<ResourceMarble> marbles = new ArrayList<>();
        // Add marbles for each resource type
        for(int i = 0; i < stone; i++)
            marbles.add(new ResourceMarble(ResourceType.STONES, 1));
        for(int i = 0; i < coins; i++)
            marbles.add(new ResourceMarble(ResourceType.COINS, 1));
        for(int i = 0; i < servants; i++)
            marbles.add(new ResourceMarble(ResourceType.SERVANTS, 1));
        for(int i = 0; i < shields; i++)
            marbles.add(new ResourceMarble(ResourceType.SHIELDS, 1));
        for(int i = 0; i < faith; i++)
            marbles.add(new ResourceMarble(ResourceType.FAITH, 1));
        for(int i = 0; i < blank; i++)
            marbles.add(new ResourceMarble(ResourceType.BLANK, 1));
        // Return
        return  marbles;
    }
}
