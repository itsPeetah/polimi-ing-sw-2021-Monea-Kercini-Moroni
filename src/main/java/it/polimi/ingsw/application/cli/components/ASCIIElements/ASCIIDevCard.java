package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.ResourceType;

public class ASCIIDevCard {
    public static void draw(DevCard devCard) {

        if(devCard == null){
            System.out.println("=== NULL CARD ===");
            System.out.println("|   O _____ O  |");
            System.out.println("=================");
        }

        String color = devCard.getColor() == Color.BLUE ? ANSIColor.BLUE :
                devCard.getColor() == Color.GREEN ? ANSIColor.GREEN :
                        devCard.getColor() == Color.YELLOW ? ANSIColor.YELLOW :
                                devCard.getColor() == Color.PURPLE ? ANSIColor.PURPLE : ANSIColor.RESET;
        System.out.println(color + "=== DEVELOPMENT CARD ===");
        System.out.println("ID: " + ANSIColor.RESET + devCard.getCardId());
        System.out.println(color + "VP: "+ ANSIColor.RESET + devCard.getVictoryPoints());
        System.out.println(color + "TIER: " + ANSIColor.RESET + devCard.getLevel());
        System.out.print(color + "COST: " + ANSIColor.RESET);
        ASCIIResources.draw(devCard.getCost());
        System.out.print(color + "\nPRODUCTION: " + ANSIColor.RESET);
        ASCIIProduction.draw(devCard.getProduction());
        System.out.println(color+"========================"+ANSIColor.RESET);
    }
}
