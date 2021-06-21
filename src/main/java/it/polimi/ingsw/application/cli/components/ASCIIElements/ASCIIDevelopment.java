package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.playerboard.ProductionPowers;
import it.polimi.ingsw.view.data.GameData;

public class ASCIIDevelopment {
    public static void draw(String player){

        DevCard[] dcs = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getDevCards().getDevCards();

        System.out.println(player + "'s productions:");
        System.out.print("Default production: ");
        ASCIIProduction.draw(ProductionPowers.getBasicProduction());
        for(int i = 0; i < dcs.length; i++){
            System.out.print("Stack #" + (i+1) + ": ");
            if(dcs[i] != null) ASCIIProduction.draw(dcs[i].getProduction());
            else System.out.print("empty\n");
        }
        System.out.println("____________________");

    }
}
