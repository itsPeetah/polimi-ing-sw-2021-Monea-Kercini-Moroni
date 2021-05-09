package it.polimi.ingsw.model.singleplayer;

public class BlackCross {

    private int blackFaith = 0;

    /**
     * Increments black cross faith track
     * @param amount to increment
     * @return true if Lorenzo has won the game
     */

    public boolean incrementBlackFaith(int amount){
        blackFaith += amount;

        //This means that Lorenzo has won the game
        if(blackFaith>=20){
            return true;
        }

        return false;
    }
}
