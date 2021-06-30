package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
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
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.ProductionPowers;
import it.polimi.ingsw.model.playerboard.Strongbox;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerleaders.PlayerLeaders;
import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.momentary.LeadersToChooseFrom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    // Test correct SP game controller creation
    @Test
    public void testSPGameControllerTest() {
        String nickname = GameApplication.getInstance().getUserNickname();
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData, nickname);

        assertTrue(gameController.isSinglePlayer());
        // Test set single player
        gameController.setSinglePlayer(false);
        assertFalse(gameController.isSinglePlayer());
        // Undo single player change
        gameController.setSinglePlayer(true);
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

        // Test OK message with game not started
        gameController.reactToMessage(Message.OK);
        assertSame(GameState.IDLE, gameController.getCurrentState());

        // Test game started message
        gameController.reactToMessage(Message.GAME_HAS_STARTED);
        assertSame(GameState.IDLE, gameController.getCurrentState());

        // Test OK message with game started
        gameController.reactToMessage(Message.OK);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());

        // Test choose leaders message
        gameController.reactToMessage(Message.CHOOSE_LEADERS);
        assertSame(GameState.CHOOSE_LEADERS, gameController.getCurrentState());

        // Test choose resources message
        gameController.reactToMessage(Message.CHOOSE_RESOURCE);
        assertSame(GameState.PICK_RESOURCES, gameController.getCurrentState());

        // Test choose replacement message
        gameController.reactToMessage(Message.CHOOSE_REPLACEMENT);
        assertSame(GameState.PICK_RESOURCES, gameController.getCurrentState());

        // Test incorrect replacement message
        gameController.reactToMessage(Message.INCORRECT_REPLACEMENT);
        assertSame(GameState.PICK_RESOURCES, gameController.getCurrentState());

        // Test start turn message
        gameController.reactToMessage(Message.START_TURN);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());

        // Test error messages
        gameController.reactToMessage(Message.NOT_ENOUGH_RESOURCES);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());
        gameController.reactToMessage(Message.ILLEGAL_CARD_PLACE);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());
        gameController.reactToMessage(Message.REQUIREMENTS_NOT_MET);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());
        gameController.reactToMessage(Message.ALREADY_USED_PRIMARY_ACTION);
        assertSame(GameState.TURN_CHOICE, gameController.getCurrentState());

        // Test warehouse unorganized message
        gameController.reactToMessage(Message.WAREHOUSE_UNORGANIZED);
        assertSame(GameState.ORGANIZE_WAREHOUSE, gameController.getCurrentState());

        // Test winner message
        gameController.reactToMessage(Message.WINNER);
        GameState newState = gameController.getCurrentState();
        assertTrue(newState == GameState.GAME_WON || newState == GameState.ENDGAME);

        // Test loser message
        gameController.reactToMessage(Message.LOSER);
        newState = gameController.getCurrentState();
        assertTrue(newState == GameState.GAME_LOST || newState == GameState.ENDGAME);

        // Test loser multiplayer message
        gameController.reactToMessage(Message.LOSER_MULTIPLAYER);
        newState = gameController.getCurrentState();
        assertSame(GameState.ENDGAME, newState);

        // Test select input message
        gameController.reactToMessage(Message.SELECT_INPUT);
        assertSame(GameState.PICK_RESOURCES, gameController.getCurrentState());

        // Test select output message
        gameController.reactToMessage(Message.SELECT_OUTPUT);
        assertSame(GameState.PICK_RESOURCES, gameController.getCurrentState());
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

        // Test warehouse update
        Warehouse warehouse = new Warehouse();
        warehouse.deposit(new Resources().add(ResourceType.COINS, 3), 0);
        warehouse.deposit(new Resources().add(ResourceType.SHIELDS, 2), 1);
        warehouse.deposit(new Resources().add(ResourceType.STONES, 1), 2);
        Strongbox strongbox = new Strongbox();
        strongbox.deposit(new Resources().add(ResourceType.SERVANTS, 5));
        WarehouseUpdateData warehouseUpdateData = new WarehouseUpdateData(warehouse, strongbox, players[2]);
        gameController.reactToUpdate(Update.WAREHOUSE, Update.WAREHOUSE.parseData(warehouseUpdateData));
        assertSame(3, gameData.getPlayerData(players[2]).getWarehouse().getContent()[0].getAmountOf(ResourceType.COINS));
        assertSame(2, gameData.getPlayerData(players[2]).getWarehouse().getContent()[1].getAmountOf(ResourceType.SHIELDS));
        assertSame(1, gameData.getPlayerData(players[2]).getWarehouse().getContent()[2].getAmountOf(ResourceType.STONES));
        assertSame(5, gameData.getPlayerData(players[2]).getStrongbox().getContent().getAmountOf(ResourceType.SERVANTS));

        // Test VP update
        int[] vp = new int[3];
        Arrays.fill(vp, 2);
        VPUpdateData vpUpdateData = new VPUpdateData(vp, players);
        gameController.reactToUpdate(Update.VP, Update.VP.parseData(vpUpdateData));
        assertSame(2, gameData.getPlayerData("0").getVP());

        // Test Faith update
        int[] fp = new int[3];
        Boolean[][] reports = new Boolean[3][4];
        Arrays.fill(reports[0], true);
        Arrays.fill(reports[1], true);
        Arrays.fill(reports[2], false);
        FaithUpdateData faithUpdateData = new FaithUpdateData(fp, players, reports);
        gameController.reactToUpdate(Update.FAITH, Update.FAITH.parseData(faithUpdateData));
        for(int i = 0; i < 3; i++) {
            assertArrayEquals(reports[i], gameData.getPlayerData(players[i]).getFaithTrack().getReportsAttended());
            assertSame(fp[i], gameData.getPlayerData(players[i]).getFaithTrack().getFaith());
        }

        // Test Solo Action update
        int blackCross = 10;
        SoloActionTokens soloActionToken = SoloActionTokens.DISCARD_2_BLUE;
        ActionTokenUpdateData actionTokenUpdateData = new ActionTokenUpdateData(soloActionToken, blackCross);
        gameController.reactToUpdate(Update.SOLO_ACTION, Update.SOLO_ACTION.parseData(actionTokenUpdateData));
        assertSame(blackCross, gameData.getCommon().getLorenzo().getBlackCross());
        assertSame(soloActionToken, gameData.getCommon().getLorenzo().getLastToken());

        // Test leaders to choose from
        List<LeadCard> leadCards1 = CardManager.loadLeadCardsFromJson().stream().limit(4).collect(Collectors.toList());
        DisposableLeadersUpdateData disposableLeadersUpdateData = new DisposableLeadersUpdateData(leadCards1, players[0]);
        gameController.reactToUpdate(Update.LEADERS_TO_CHOOSE_FROM, Update.LEADERS_TO_CHOOSE_FROM.parseData(disposableLeadersUpdateData));
        assertTrue(gameData.getPlayerData(players[0]).getLeadersToChooseFrom().getLeaders().stream().allMatch(leadCard -> leadCards1.stream().anyMatch(leadCard1 -> leadCard.getCardId().equals(leadCard1.getCardId()))));
        assertSame(4, gameData.getPlayerData(players[0]).getLeadersToChooseFrom().getLeaders().size());

        // Test resources to put
        int coinsAmount = 2;
        int shieldsAmount = 1;
        Resources resources = new Resources().add(ResourceType.COINS, coinsAmount).add(ResourceType.SHIELDS, shieldsAmount);
        ResourcesToPutUpdateData resourcesToPutUpdateData = new ResourcesToPutUpdateData(resources);
        gameController.reactToUpdate(Update.RESOURCES_TO_PUT, Update.RESOURCES_TO_PUT.parseData(resourcesToPutUpdateData));
        assertSame(coinsAmount, gameData.getMomentary().getResourcesToPut().getRes().getAmountOf(ResourceType.COINS));
        assertSame(shieldsAmount, gameData.getMomentary().getResourcesToPut().getRes().getAmountOf(ResourceType.SHIELDS));

        // Test current player
        CurrentPlayerUpdateData currentPlayerUpdateData = new CurrentPlayerUpdateData(players[1]);
        gameController.reactToUpdate(Update.CURRENT_PLAYER, Update.CURRENT_PLAYER.parseData(currentPlayerUpdateData));
        assertEquals(players[1], gameData.getCommon().getCurrentPlayer());
    }

    @Test
    public void testReactToAction() {
        GameData gameData = new GameData();
        GameController gameController = new GameController(gameData);
        String[] players = new String[3];
        players[0] = "0";
        players[1] = "1";
        players[2] = "2";

        ActionPacket actionPacket = new ActionPacket(Action.END_TURN, null);
        gameController.reactToAction(actionPacket);
        assertSame(GameState.IDLE, gameController.getCurrentState());
    }
}
