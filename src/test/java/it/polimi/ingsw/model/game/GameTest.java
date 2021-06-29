package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.game.util.GameCustomizationSettings;
import it.polimi.ingsw.model.game.util.GameFactory;
import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void testAddPlayer() {
        Game g = GameFactory.CreateGame(GameCustomizationSettings.getMinSettings());
        String playerNickname = "Player 1";
        // Try adding a single player
        try {
            g.addPlayer(playerNickname);
        } catch ( GameException ge) {ge.printStackTrace();}

        assertEquals(g.getPlayers().length, 1);
        assertEquals(g.getPlayers()[0].getNickname(), playerNickname);

        // Try adding more players until the limit is surpassed
        for(int i = 2; i <= 5; i++){
            String pName = "Player " + i;
            try{
                g.addPlayer(pName);
            } catch (GameException ge){
                assertEquals(i, Game.MAX_PLAYERS + 1);
            }
        }

        // Make sure there are not too many players
        assertEquals(g.getPlayers().length, Game.MAX_PLAYERS);
    }

    @Test
    public void testGetCurrentPlayer() {
        // Init game
        Game g = GameFactory.CreateGame(GameCustomizationSettings.getMinSettings());
        String[] pNames = new String[]{"P1", "P2", "P3"};
        int pCount = 3;
        // add players
        try{
            g.addPlayer(pNames[0]);
            g.addPlayer(pNames[1]);
            g.addPlayer(pNames[2]);
        } catch (GameException ge){ge.printStackTrace();}
        // Make sure that for every turn only the right player is gotten
        for(int i = 0; i < 20; i++) {
            assertEquals(g.getCurrentPlayer().getNickname(), pNames[i%pCount]);
            g.increaseTurnCounter();
        }
    }

    @Test
    public void testVaticanReports() {
        // init game
        Game g = GameFactory.CreateGame(GameCustomizationSettings.getMinSettings());
        try {
            g.addPlayer("P1");
            g.addPlayer("P2");
            g.addPlayer("P3");
        } catch (GameException ge) {
            ge.printStackTrace();
        }
        // Perform reports
        for (int i = 0; i <= Game.MAX_VATICAN_REPORTS; i++) {
            try {
                g.doVaticanReport();
                // Players have no points, therefore no reports attended
                assertTrue(Arrays.stream(g.getAllReportsAttended()).allMatch(booleans -> Arrays.stream(booleans).noneMatch(aBoolean -> aBoolean != null && aBoolean.equals(true))));
            } catch (GameException ge) {
                assertEquals(i, Game.MAX_VATICAN_REPORTS);
            }
        }
    }

    @Test
    public void testCheckVaticanReports() {
        // init game
        Game g = GameFactory.CreateGame(GameCustomizationSettings.getMinSettings());
        try {
            g.addPlayer("P1");
            g.addPlayer("P2");
            g.addPlayer("P3");
        } catch (GameException ge) {
            ge.printStackTrace();
        }

        for (int i = 0; i < g.getPlayers().length; i++) {
            // Set faith points
            g.getPlayers()[i].getBoard().incrementFaithPoints(10);
        }

        // Test method
        g.checkVaticanReport(0);

        // All players must have first vatican report
        assertTrue(Arrays.stream(g.getAllReportsAttended()).allMatch(booleans -> booleans[0]));
    }

    @Test
    public void testShufflePlayers() {
        // init game
        Game g = GameFactory.CreateGame(GameCustomizationSettings.getMinSettings());
        try {
            g.addPlayer("P1");
            g.addPlayer("P2");
            g.addPlayer("P3");
        } catch (GameException ge) {
            ge.printStackTrace();
        }

        // Get players
        Player[] preShufflePlayers = g.getPlayers();
        String[] preShuffleNicknames = g.getPlayerNames();

        // Shuffle
        g.shufflePlayers();

        // Get players again
        Player[] postShufflePlayers = g.getPlayers();
        String[] postShuffleNicknames = g.getPlayerNames();

        // Check that players are not disappeared
        assertTrue(Arrays.stream(postShufflePlayers).allMatch(player -> Arrays.asList(preShufflePlayers).contains(player)));
        assertTrue(Arrays.stream(postShuffleNicknames).allMatch(nickname -> Arrays.asList(preShuffleNicknames).contains(nickname)));

    }
}