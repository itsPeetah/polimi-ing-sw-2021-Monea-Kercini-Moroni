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
     * Constructor for the class.
     */
    public Game(){
        turnCounter = 0;
        lastVaticanReport = 0;

        // TODO implement game settings
        // resourceMarket = new MarketTray(rows, cols, marbles);
        // devCardMarket = new DevCardMarket(cards);

        players = new ArrayList<Player>();
    }

    /**
     * Add a new player to the game.
     * @param nickname Nickname of the player.
     * @throws GameException if the player can't be added to the game.
     */
    public void addPlayer(String nickname) throws  GameException
    {
        // Check if player limit has already been reached
        if(players.size() >= MAX_PLAYERS) throw new GameException("Too many players in the game. Unable to join.");
        // Create and register the new player
        Player newPlayer = new Player(nickname);
        players.add(newPlayer);
    }
    /**
     * Gets the array players in the game.
     */
    public Player[] getPlayers(){
        Player[] pls = new Player[players.size()];
        return players.toArray(pls);
    }
    /**
     * Gets the player whose turn it currently is.
     */
    public Player getCurrentPlayer(){
        int currentPlayer = turnCounter%players.size();
        return  players.get(currentPlayer);
    }
}
