package it.polimi.ingsw.model.singleplayer;

import java.util.ArrayList;
import java.util.Collections;

public final class SoloAction {

    private ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<SoloActionTokens>();
    int difficulty;

    /**
     * Prepares Lorenzo solo action tokens for single player
     * difficulty - in case we want to make different difficulty levels
     */
    private ArrayList<SoloActionTokens> createSoloActionDeck(){
        switch (difficulty){
            default:
                soloActionTokens.add(SoloActionTokens.DISCARD_2_BLUE);
                soloActionTokens.add(SoloActionTokens.DISCARD_2_GREEN);
                soloActionTokens.add(SoloActionTokens.DISCARD_2_YELLOW);
                soloActionTokens.add(SoloActionTokens.DISCARD_2_PURPLE);
                soloActionTokens.add(SoloActionTokens.MOVE_2);
                soloActionTokens.add(SoloActionTokens.MOVE_1_SHUFFLE);
                // I think one of the tokens was 2 times?
                //todo confirm the number of tokens
        }
        //Shuffle tokens
        Collections.shuffle(soloActionTokens);

        return soloActionTokens;
    }

    public void setSoloGameDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
