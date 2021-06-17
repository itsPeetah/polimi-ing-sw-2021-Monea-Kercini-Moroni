package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.view.data.common.DevCardMarket;

public class ASCIIDevCardMarket {
    public static void draw(DevCardMarket dcm){

        System.out.println("******* DEVELOPMENT CARD MARKET *******");
        for(int i = 0; i < 4; i++){
            for(int j = 2; j >= 0; j--)
            ASCIIDevCard.draw(dcm.getAvailableCards()[i][j]);
        }

    }
}
