package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.cards.LeadCardAbility;
import it.polimi.ingsw.model.cards.LeadCardRequirements;
import it.polimi.ingsw.model.general.*;

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
                System.out.println("\t\tA "+ devLevels.get(c).toString() + " tier " + c.toString() + " card");
            }
        }
        if(devColors != null && devColors.size() > 0){
            System.out.println("\tDev card colors:");
            for(Color c : devColors.keySet()){
                System.out.println("\t\t" + devColors.get(c).toString() + "x " + c.toString() + " card(s)");
            }
        }
        System.out.println("Ability:");
        LeadCardAbility ability = card.getAbility();
        Resources extraSpace = ability.getExtraWarehouseSpace();
        Resources marketDiscount =  ability.getResourceDiscount();
        ResourceType replacement = ability.getWhiteMarbleReplacement();
        Production production = ability.getProduction();
        if(extraSpace != null && extraSpace.getTotalAmount() > 0){
            System.out.print("\tExtra warehouse space: ");
            ASCIIResources.draw(extraSpace);
            System.out.print("\n");
        }
        if(marketDiscount != null && marketDiscount.getTotalAmount() > 0){
            System.out.print("\tResource discount on market: -1x");
            ASCIIResources.draw(marketDiscount);
            System.out.print("\n");
        }
        if(replacement!=ResourceType.BLANK){

            String color = replacement==ResourceType.STONES ? ANSIColor.WHITE_BACKGROUND :
                    replacement == ResourceType.SHIELDS.SHIELDS ? ANSIColor.BLUE :
                    replacement == ResourceType.SERVANTS ? ANSIColor.PURPLE :
                    replacement == ResourceType.COINS ? ANSIColor.YELLOW : ANSIColor.RESET;

            System.out.println("\tGrey marble grants you extra " + color + replacement.toString() + ANSIColor.RESET);
        } if(production != null && production.getInput() != null && production.getOutput() != null && production.getInput().getTotalAmount() > 0 && production.getOutput().getTotalAmount() > 0)
        {
            System.out.print("\tProduction: ");
            ASCIIProduction.draw(production);
            System.out.print("\n");
        }
        System.out.println("=================");
    }
}
