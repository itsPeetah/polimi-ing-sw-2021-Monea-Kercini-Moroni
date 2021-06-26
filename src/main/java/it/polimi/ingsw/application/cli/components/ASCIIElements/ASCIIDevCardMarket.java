package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.view.data.common.DevCardMarket;
/**
 * ASCII Dev Card Market drawing class
 */
public class ASCIIDevCardMarket {

    /**
     * Draw a DevCardMarket to the screen
     */
    public static void draw(DevCardMarket dcm){

        System.out.println("******* DEVELOPMENT CARD MARKET *******");
        for(int i = 0; i < 4; i++){
            for(int j = 2; j >= 0; j--) {
                if(dcm.getAvailableCards()[i][j] != null) {
                    ASCIIDevCard.draw(dcm.getAvailableCards()[i][j]);
                }
            }
        }

    }
}
