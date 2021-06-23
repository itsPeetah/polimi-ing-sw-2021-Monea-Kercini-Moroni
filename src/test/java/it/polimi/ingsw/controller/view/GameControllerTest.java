package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.data.VPUpdateData;
import it.polimi.ingsw.view.data.GameData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    // Test correct SP game controller creation
    @Test
    public void SPGameControllerTest() {
        String nickname = GameApplication.getInstance().getUserNickname();
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData, nickname);

        assertTrue(gameController.isSinglePlayer());
        assertSame(gameController.getCurrentState(), GameState.IDLE);
        assertNotNull(gameController.getGameData().getPlayerData(nickname));
        assertNotNull(gameController.getGameControllerIOHandler());
    }

    // Test correct MP game controller creation
    @Test
    public void MPGameControllerTest() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);

        assertFalse(gameController.isSinglePlayer());
        assertSame(GameState.IDLE, gameController.getCurrentState());
        assertNotNull(gameController.getGameControllerIOHandler());
    }

    @Test
    public void moveToStateTest() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);
        gameController.moveToState(GameState.ENDGAME);

        assertSame(GameState.ENDGAME, gameController.getCurrentState());
    }

    @Test
    public void reactToMessageTest() {
        GameApplication.setOutputMode(GameApplicationMode.GUI);
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);

        gameController.reactToMessage(Message.START_TURN);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());
    }

    @Test
    public void reactToUpdateTest() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);

        int[] vp = new int[3];
        String[] players = new String[3];
        players[0] = "0";
        players[1] = "1";
        players[2] = "2";
        Arrays.fill(vp, 2);
        VPUpdateData vpUpdateData = new VPUpdateData(vp, players);
        gameController.reactToUpdate(Update.VP, Update.VP.parseData(vpUpdateData));
        assertSame(2, gameData.getPlayerData("0").getVP());
    }
}
