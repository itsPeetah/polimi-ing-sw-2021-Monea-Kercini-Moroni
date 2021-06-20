package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.view.data.GameData;

public class ASCIIDevelopment {
    public static void draw(String player){

        DevCard[] dcs = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getDevCards().getDevCards();

        System.out.println(player + "'s productions:");
        for(int i = 0; i < dcs.length; i++){
            System.out.print("Stack #" + i + ": ");
            if(dcs[i] != null) ASCIIProduction.draw(dcs[i].getProduction());
            else System.out.print("empty");
            System.out.print("\n");
        }
        System.out.println("____________________");

    }
}
