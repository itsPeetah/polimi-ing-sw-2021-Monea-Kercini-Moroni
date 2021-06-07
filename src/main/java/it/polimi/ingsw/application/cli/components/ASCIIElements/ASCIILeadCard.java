package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.cards.LeadCardRequirements;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.Resources;

import java.util.HashMap;
import java.util.Hashtable;

public class ASCIILeadCard {
    public static void draw(LeadCard card){
        System.out.println("=== LEAD CARD ===");
        System.out.println("ID: " + card.getCardId());
        System.out.println("VP: " + card.getVictoryPoints());
        System.out.println("Requirements:");
        LeadCardRequirements req = card.getRequirements();
        Resources resourcesAmounts = req.getResourceAmounts();;
        HashMap<Color, Level> devLevels = req.getDevCardLevels();
        HashMap<Color, Integer> devColors = req.getDevCardColors();
        if(resourcesAmounts != null && resourcesAmounts.getTotalAmount() > 0){
            System.out.println("\tOwned resources:");
            System.out.print("\t\t");
            ASCIIResources.draw(resourcesAmounts);
            System.out.print("\n");
        }
        if(devLevels != null && devLevels.size() > 0){
            System.out.println("\tDev card levels:");
            for(Color c : devLevels.keySet()){
                System.out.println("\t\tA "+ devLevels.get(c).toString() + " tier " + c.toString() + "card");
            }
        }
        if(devColors != null && devColors.size() > 0){
            System.out.println("\tDev card colors:");
            for(Color c : devColors.keySet()){
                System.out.println("\t\t" + devColors.get(c).toString() + "x " + c.toString() + " card(s)");
            }
        }
        System.out.println("=================");
    }
}
