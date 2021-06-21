package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
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
        System.out.print("Default production: ");
        ASCIIProduction.draw(ProductionPowers.getBasicProduction());
        for(int i = 0; i < dcs.length; i++){
            System.out.print("Stack #" + (i+1) + ": ");
            if(dcs[i] != null) ASCIIProduction.draw(dcs[i].getProduction());
            else System.out.print("empty\n");
        }
        for(int i = 0; i < 2; i++){
            if(pls.getStates()[i] == CardState.PLAYED && pls.getLeaders()[i].getAbility().getProduction() != null) {
                System.out.print("Leader extra #" + (i + 1) + ": ");
                ASCIIProduction.draw(pls.getLeaders()[i].getAbility().getProduction());
            }
        }
        System.out.println("____________________");

    }
}
