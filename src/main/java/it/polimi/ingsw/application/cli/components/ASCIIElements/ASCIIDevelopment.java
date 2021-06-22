package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.playerboard.ProductionPowers;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.player.PlayerLeaders;

public class ASCIIDevelopment {
    public static void draw(String player){

        GameData gd = GameApplication.getInstance().getGameController().getGameData();
        DevCard[] dcs = gd.getPlayerData(player).getDevCards().getDevCards();
        PlayerLeaders pls = gd.getPlayerData(player).getPlayerLeaders();

        System.out.println(player + "'s productions:");
        /*for(DevCard dc : dcs){
            if(dc==null) continue;
            String color = dc.getColor() == Color.BLUE ? ANSIColor.BLUE :
                    dc.getColor() == Color.GREEN ? ANSIColor.GREEN :
                            dc.getColor() == Color.YELLOW ? ANSIColor.YELLOW :
                                    dc.getColor() == Color.PURPLE ? ANSIColor.PURPLE : ANSIColor.RESET;
            System.out.println("> " + color + dc.getColor().toString() + " " + dc.getLevel().toString() +ANSIColor.RESET + " (VP: " + dc.getVictoryPoints() +")");
        }*/
        System.out.println("Productions:");
        System.out.print("(0) Default production: ");
        ASCIIProduction.draw(ProductionPowers.getBasicProduction());
        for(int i = 0; i < dcs.length; i++){
            System.out.print("("+ (i+1) +") "+"Stack #" + (i+1));
            if(dcs[i] != null) {
                String color = dcs[i].getColor() == Color.BLUE ? ANSIColor.BLUE :
                        dcs[i].getColor() == Color.GREEN ? ANSIColor.GREEN :
                                dcs[i].getColor() == Color.YELLOW ? ANSIColor.YELLOW :
                                        dcs[i].getColor() == Color.PURPLE ? ANSIColor.PURPLE : ANSIColor.RESET;
                System.out.print(" " + color + dcs[i].getColor().toString() + " " + dcs[i].getLevel().toString() +ANSIColor.RESET + " (VP: " + dcs[i].getVictoryPoints() +"): ");
                ASCIIProduction.draw(dcs[i].getProduction());
            }
            else System.out.print(": empty\n");
        }
        for(int i = 0; i < 2; i++){
            if(pls.getStates()[i] == CardState.PLAYED && pls.getLeaders()[i].getAbility().getProduction() != null) {
                System.out.print("("+(i+4)+") "+"Leader extra #" + (i + 1) + ": ");
                ASCIIProduction.draw(pls.getLeaders()[i].getAbility().getProduction());
            }
        }
        System.out.println("____________________");


    }
}
