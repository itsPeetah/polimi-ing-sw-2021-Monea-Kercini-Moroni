package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Game rep class.
 */
public class Game {
    // Static values
    public static final int MAX_PLAYERS = 4;
    public static final int MAX_VATICAN_REPORTS = 3;

    // Members
    private Integer turnCounter;
    private Integer lastVaticanReport;

    private MarketTray resourceMarket;
    private DevCardMarket devCardMarket;

    private ArrayList<Player> players;

    /**
     * Constructor for the class.
     */
    public Game(MarketTray resourceMarket, DevCardMarket devCardMarket)
    {
        this.turnCounter = 0;
        this.lastVaticanReport = 0;

        this.resourceMarket = resourceMarket;
        this.devCardMarket = devCardMarket;

        this.players = new ArrayList<Player>();
    }

    // Player handling ------------------------------------------------------------------

    /**
     * Add a new player to the game.
     *
     * @param nickname Nickname of the player.
     * @throws GameException if the player can't be added to the game.
     */
    public void addPlayer(String nickname) throws GameException {
        // Check if player limit has already been reached
        if (players.size() >= MAX_PLAYERS) throw new GameException("Too many players in the game. Unable to join.");
        // Create and register the new player
        Player newPlayer = new Player(nickname);
        players.add(newPlayer);
    }

    /**
     * Gets the array players in the game.
     */
    public Player[] getPlayers() {
        Player[] pls = new Player[players.size()];
        return players.toArray(pls);
    }

    /**
     * Gets the player whose turn it currently is.
     */
    public Player getCurrentPlayer() {
        int currentPlayer = turnCounter % players.size();
        return players.get(currentPlayer);
    }

    /**
     * Shuffles the player order
     */

    public void shufflePlayers(){
        Collections.shuffle(players);
    }

    // Component getters ------------------------------------------------------------------

    /**
     * Resource Market getter.
     */
    public MarketTray getResourceMarket() {
        return resourceMarket;
    }

    /**
     * Development Card Market getter.
     */
    public DevCardMarket getDevCardMarket() {
        return devCardMarket;
    }

    // Turn handling ------------------------------------------------------------------

    /**
     * Turn counter getter.
     */
    public Integer getTurnCounter() {
        return turnCounter;
    }

    /**
     * Advance the turn counter by 1.
     */
    public void increaseTurnCounter() {turnCounter++;}

    /**
     * Vatican report getter.
     */
    public Integer getLastVaticanReport() {
        return lastVaticanReport;
    }

    /**
     * Let all eligible players attend a vatican report.
     *
     * @throws GameException if the report can't be attended
     */
    public void doVaticanReport() throws GameException {
        if (lastVaticanReport >= MAX_VATICAN_REPORTS) throw new GameException("Max vatican records already performed.");

        int currentReport = lastVaticanReport + 1;

        // TODO Add customization?
        // Vatican report range?
        // for now hardcoded
        for (Player p : players) {
            boolean eligible;
            int playersFaithPoints = p.getBoard().getFaithPoints();
            // Check eligibility
            if (currentReport == 1 && playersFaithPoints >= 5 && playersFaithPoints <= 8)
                eligible = true;
            else if (currentReport == 2 && playersFaithPoints >= 12 && playersFaithPoints <= 16)
                eligible = true;
            else if (currentReport == 3 && playersFaithPoints >= 19 && playersFaithPoints <= 24)
                eligible = true;
            else eligible = false;

            if (eligible) p.getBoard().attendReport(currentReport);
        }
        // Increase VR counter
        lastVaticanReport++;
    }

}
