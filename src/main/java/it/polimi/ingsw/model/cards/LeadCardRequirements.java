package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.Resources;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class LeadCardRequirements {
    private HashMap<Color, Integer> devCardColors;
    private HashMap<Level, Integer> devCardLevels;
    private Resources resourceAmounts;

    public LeadCardRequirements(HashMap<Color, Integer> devCardColors, HashMap<Level, Integer> devCardLevels, Resources resourceAmounts) {
        this.devCardColors = devCardColors;
        this.devCardLevels = devCardLevels;
        this.resourceAmounts = resourceAmounts;
    }
    
    public Boolean check(Player player) {
        ArrayList<DevCard> playerCards = player.getBoard().getOwnedDevCards();

        /* Check resource amounts */
        if (!player.getBoard().getResourcesAvailable().isGreaterThan(resourceAmounts)) return false;

        /* Check dev card colors requirements */
        for(Color colReq: devCardColors.keySet()) {
            if(devCardColors.get(colReq) > playerCards.parallelStream().filter(dc -> dc.getColor() == colReq).count()) return false;
        }

        /* Check dev card levels requirements */
        for(Level levReq: devCardLevels.keySet()) {
            if(devCardColors.get(levReq) > playerCards.parallelStream().filter(dc -> dc.getLevel() == levReq).count()) return false;
        }

        /* All checks have been passed */
        return true;
    }
}
