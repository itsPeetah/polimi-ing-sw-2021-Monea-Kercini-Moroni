package it.polimi.ingsw.model.PlayerBoard;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Production;

import java.util.ArrayList;

public class ProductionPowers {
    private Production basicProduction;
    private DevCard[][] cardPile;  //The first symbolizes the pile and each can have 3 cards maximum


    //It must search all three card piles and return the card on top of each
    public ArrayList<Production> getAvailableProductions(){

        ArrayList<Production> AvailableProductions = new ArrayList<Production>();

        for (int i = 0; i < 3; i++) {
            //If the card exists I add the production of the top card
            if (cardPile[i][2] != null){
                AvailableProductions.add(cardPile[i][2].getProduction());
            }else if (cardPile[i][1] != null){
                AvailableProductions.add(cardPile[i][1].getProduction());
            }else if (cardPile[i][0] != null) {
                AvailableProductions.add(cardPile[i][0].getProduction());
            }
        }


        AvailableProductions.add(basicProduction);

        //Maybe add LeaderProduction?

        return AvailableProductions;
    }

    public void addDevCard(DevCard devCard, int position){

        cardPile[position][devCard.getLevel()-1] = devCard; //adds devcard sul selected pile at the correct position
    }

    public ProductionPowers() {
        this.basicProduction = new Production(); //QUA DA AGGIORNARE !!!
        this.cardPile = new DevCard[3][3];
    }
}
