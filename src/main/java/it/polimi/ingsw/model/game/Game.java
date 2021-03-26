package it.polimi.ingsw.model.game;

import java.util.ArrayList;

public class Game {
    private static final int MAX_PLAYERS = 4;
    private static final int MAX_VATICAN_REPORTS = 3;

    private Integer turnCounter;
    private Integer lastVaticanReport;

    private MarketTray resourceMarket;
    private DevCardMarket devCardMarket;

    private ArrayList<Player> players;

    /**
     * Gets the array players in the game.
     * @return The players.
     */
    public Player[] getPlayers(){
        Player[] pls = new Player[players.size()];
        return players.toArray(pls);
    }

    /**
     * Gets the player whose turn it currently is.
     * @return The current player.
     */
    public Player getCurrentPlayer(){
        int currentPlayer = turnCounter%players.size();
        return  players.get(currentPlayer);
    }
}
