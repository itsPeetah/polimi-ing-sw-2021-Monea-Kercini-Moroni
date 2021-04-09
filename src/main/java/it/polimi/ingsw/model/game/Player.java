package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerleaders.PlayerLeaders;

/**
 * Player rep class.
 */
public class Player {

    private String nickname;

    private PlayerBoard board;
    private PlayerLeaders leaders;

    /**
     * Class constructor.
     * @param nickname The player's nickname.
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.board = new PlayerBoard();
        this.leaders = new PlayerLeaders(2); //For the moment we leave leader max at 2, as per default in game
    }

    /**
     * Nickname getter.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Board getter.
     */
    public PlayerBoard getBoard() {
        return board;
    }

    /**
     * Leaders getter.
     */
    public PlayerLeaders getLeaders(){
        return leaders;
    }

    /**
     * Get the player's victory points.
     */
    public int getVictoryPoints(){
        int vp = 0;
        vp += board.getBoardVictoryPoints();
        // vp += leaders.getPlayedCardVictoryPoints(); // TODO fix vp getter for lead cards
        return  vp;
    }

}
