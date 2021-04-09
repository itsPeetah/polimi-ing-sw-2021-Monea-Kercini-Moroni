package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.game.util.GameFactory;
import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void testAddPlayer() {
        Game g = GameFactory.CreateGame(GameSettingsLevel.LOW, GameSettingsLevel.LOW);
        String playerNickname = "Player 1";
        // Try adding a single player
        try {
            g.addPlayer(playerNickname);
        } catch ( GameException ge) {ge.printStackTrace();}

        assertEquals(g.getPlayers().length, 1);
        assertEquals(g.getPlayers()[0].getNickname(), playerNickname);

        for(int i = 2; i <= 5; i++){
            String pName = "Player " + i;
            try{
                g.addPlayer(pName);
            } catch (GameException ge){
                assertEquals(i, Game.MAX_PLAYERS + 1);
            }
        }

        assertEquals(g.getPlayers().length, Game.MAX_PLAYERS);
    }

    @Test
    public void testGetCurrentPlayer() {
        Game g = GameFactory.CreateGame(GameSettingsLevel.LOW, GameSettingsLevel.LOW);
        String[] pNames = new String[]{"P1", "P2", "P3"};
        int pCount = 3;

        try{
            g.addPlayer(pNames[0]);
            g.addPlayer(pNames[1]);
            g.addPlayer(pNames[2]);
        } catch (GameException ge){ge.printStackTrace();}

        for(int i = 0; i < 20; i++) {
            assertEquals(g.getCurrentPlayer().getNickname(), pNames[i%pCount]);
            g.increaseTurnCounter();
        }

    }

    @Test
    public void testVaticanReports() {
    }
}