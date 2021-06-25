package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.view.data.player.PlayerLeaders;

/**
 * ASCII Player Leaders drawing class
 */
public class ASCIIPlayerLeaders {

    /**
     * Draw the leader cards owned by a player
     */
    public static void draw(String player){
        PlayerLeaders pls = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getPlayerLeaders();

        System.out.println(player + "'s productions:");
        for(int i = 0; i < 2; i++){
            if(pls.getStates()[i] != null){

                if(player.equals(GameApplication.getInstance().getUserNickname())){
                    System.out.println("LEADER #" + i + ", STATE: " + pls.getStates()[i]);
                    ASCIILeadCard.draw(pls.getLeaders()[i]);
                }
                else if(pls.getStates()[i] == CardState.PLAYED){
                    ASCIILeadCard.draw(pls.getLeaders()[i]);
                }

            }
        }
        System.out.println("____________________");
    }
}
