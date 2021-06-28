package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.data.*;
import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.game.DevCardMarket;
import it.polimi.ingsw.model.game.MarketTray;
import it.polimi.ingsw.model.game.MarketTrayException;
import it.polimi.ingsw.model.game.ResourceMarble;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.playerboard.ProductionPowers;
import it.polimi.ingsw.model.playerleaders.PlayerLeaders;
import it.polimi.ingsw.view.data.GameData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    // Test correct SP game controller creation
    @Test
    public void testSPGameControllerTest() {
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
    public void testMPGameControllerTest() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);

        assertFalse(gameController.isSinglePlayer());
        assertSame(GameState.IDLE, gameController.getCurrentState());
        assertNotNull(gameController.getGameControllerIOHandler());
    }

    @Test
    public void testmoveToStateTest() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);
        gameController.moveToState(GameState.ENDGAME);

        assertSame(GameState.ENDGAME, gameController.getCurrentState());
    }

    @Test
    public void testReactToMessageTest() {
        GameApplication.setOutputMode(GameApplicationMode.GUI);
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);

        gameController.reactToMessage(Message.START_TURN);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());
    }

    @Test
    public void testReactToUpdateTest() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);
        String[] players = new String[3];
        players[0] = "0";
        players[1] = "1";
        players[2] = "2";

        // Test Resource Market update
        ResourceMarble[] resourceMarbles = new ResourceMarble[13];
        Arrays.fill(resourceMarbles, new ResourceMarble(ResourceType.COINS, 1));
        MarketTray MT = null;
        try {
            MT = new MarketTray(3, 4, new ArrayList<>(Arrays.asList(resourceMarbles)));
        } catch (MarketTrayException e) {
            e.printStackTrace();
            fail();
        }
        ResourceMarketUpdateData resourceMarketUpdateData = new ResourceMarketUpdateData(MT);
        gameController.reactToUpdate(Update.RESOURCE_MARKET, Update.RESOURCE_MARKET.parseData(resourceMarketUpdateData));
        assertSame(1, gameData.getCommon().getMarketTray().getWaiting()[0].getValue().getAmountOf(ResourceType.COINS));

        // Test Dev Card Market update
        ArrayList<DevCard> devCards = CardManager.loadDevCardsFromJson();
        DevCardMarket devCardMarket = new DevCardMarket(devCards, 4);
        DevCardMarketUpdateData devCardMarketUpdateData = new DevCardMarketUpdateData(devCardMarket);
        gameController.reactToUpdate(Update.DEVCARD_MARKET, Update.DEVCARD_MARKET.parseData(devCardMarketUpdateData));
        assertSame(12, gameData.getCommon().getDevCardMarket().getAvailableCards().length * gameData.getCommon().getDevCardMarket().getAvailableCards()[0].length);

        // Test Production Powers Update
        ProductionPowers productionPowers = new ProductionPowers(3);
        productionPowers.addDevCard(devCards.get(0), 0);
        ProductionPowersUpdateData productionPowersUpdateData = new ProductionPowersUpdateData(productionPowers, players[0]);
        gameController.reactToUpdate(Update.PRODUCTION_POWERS, Update.PRODUCTION_POWERS.parseData(productionPowersUpdateData));
        assertEquals(devCards.get(0).getCardId(), gameData.getPlayerData(players[0]).getDevCards().getDevCards()[0].getCardId());

        // Test Leaders update
        List<LeadCard> leadCards = CardManager.loadLeadCardsFromJson();
        PlayerLeaders playerLeaders = new PlayerLeaders(1);
        LeadCard[] leadArray = new LeadCard[1];
        leadArray[0] = leadCards.get(0);
        playerLeaders.setCards(leadArray);
        PlayerLeadersUpdateData playerLeadersUpdateData = new PlayerLeadersUpdateData(playerLeaders, players[0]);
        gameController.reactToUpdate(Update.LEADERS, Update.LEADERS.parseData(playerLeadersUpdateData));

        // Test VP update
        int[] vp = new int[3];
        Arrays.fill(vp, 2);
        VPUpdateData vpUpdateData = new VPUpdateData(vp, players);
        gameController.reactToUpdate(Update.VP, Update.VP.parseData(vpUpdateData));
        assertSame(2, gameData.getPlayerData("0").getVP());

        // Test
    }
}
