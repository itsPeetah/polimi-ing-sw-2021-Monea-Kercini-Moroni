package it.polimi.ingsw.controller.model;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.data.Choose2LeadersActionData;
import it.polimi.ingsw.controller.model.actions.data.ChooseResourceActionData;
import it.polimi.ingsw.controller.model.actions.data.PutResourcesActionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



class GameManagerTest {
    CommunicationHandler communicationHandler;
    final int WAIT_TIME = 50;

    @BeforeEach
    void generateHandler() {
        communicationHandler = new CommunicationHandler();
    }

    /**
     * Testing if setup works correctly in a 2 player game
     *
     * NOTE: To make testing easier the shuffle players method has been deactivated
     * This is important also for testing the order of the players that have entered the game is correct
     */
    @Test
    void setup(){

        GameManager gm = new GameManager(communicationHandler);

        gm.addPlayer("Player 1");
        gm.addPlayer("Player 2");

        //Players have decided to start the game
        new Thread(gm::setupGame).start();


        //Preparing Leaders
        LeadCard[] myLeaders1 = new LeadCard[2];
        LeadCard[] myLeaders2 = new LeadCard[2];
        myLeaders1[0]  = CardManager.loadLeadCardsFromJson().get(0);
        myLeaders1[1]  = CardManager.loadLeadCardsFromJson().get(1);
        myLeaders2[0]  = CardManager.loadLeadCardsFromJson().get(2);
        myLeaders2[1]  = CardManager.loadLeadCardsFromJson().get(3);


        Choose2LeadersActionData TwoLeaders = new Choose2LeadersActionData();

        //Player 1 Leaders
        TwoLeaders.setLeaders(myLeaders1);
        TwoLeaders.setPlayer("Player 1");
        MockResponse MR1 = new MockResponse(communicationHandler, Action.CHOOSE_2_LEADERS, TwoLeaders);
        MR1.startSendingResponse();

        //Player 2 Leaders
        TwoLeaders.setLeaders(myLeaders2);
        TwoLeaders.setPlayer("Player 2");
        MockResponse MR2 = new MockResponse(communicationHandler, Action.CHOOSE_2_LEADERS, TwoLeaders);
        MR2.startSendingResponse();

        //Player 2 Chooses a resource
        Resources res = new Resources();
        res.add(ResourceType.STONES, 1);
        ChooseResourceActionData pickedRes = new ChooseResourceActionData();
        pickedRes.setRes(res);
        pickedRes.setPlayer("Player 2");
        MockResponse MR3 = new MockResponse(communicationHandler, Action.CHOOSE_RESOURCE, pickedRes);
        MR3.startSendingResponse();

        //Player 2 Puts the resource in his warehouse
        Warehouse wh = new Warehouse();
        wh.deposit(res, 1);
        PutResourcesActionData putres = new PutResourcesActionData();
        putres.setWh(wh);
        putres.setPlayer("Player 2");
        MockResponse MR4 = new MockResponse(communicationHandler, Action.PUT_RESOURCES, putres);
        MR4.startSendingResponse();

        //We wait a millisecond before turning off the player responses (the time is enough)

        try {
            TimeUnit.MILLISECONDS.sleep(WAIT_TIME);
        }catch (Exception e){
            e.printStackTrace();
        }
        MR1.stopSendingResponse();
        MR2.stopSendingResponse();
        MR3.stopSendingResponse();
        MR4.stopSendingResponse();

        //Testing if we have the player in game
        assertEquals("Player 1", gm.getGame().getPlayers()[0].getNickname() );

        //Testing if we added the correct card to that player
        assertEquals(myLeaders1[0].getCardId(), gm.getGame().getPlayers()[0].getLeaders().getPlayableCards().get(0).getCardId() );
        //Testing if we added the correct card to player 2
        assertEquals(myLeaders2[1].getCardId(), gm.getGame().getPlayers()[1].getLeaders().getPlayableCards().get(1).getCardId() );

        //At this point the game starts
        assertEquals(GamePhase.TURN, gm.gamePhase);

        //Testing if we can find the resource in the players 2 warehouse
        assertTrue(res.equals(gm.getGame().getPlayers()[1].getBoard().getWarehouse().getResourcesAvailable()));



        System.out.println("YAY");

    }

    @Test
    void checkWhite(){

        //Adding one player to the game
        GameManager gm = new GameManager(communicationHandler);
        gm.addPlayer("Player 1");
        Player p = gm.getGame().getPlayers()[0];

        //Testing by adding a resource with no particularities
        Resources res = new Resources();
        res.add(ResourceType.STONES, 2);
        res.add(ResourceType.SHIELDS, 1);

        Resources newRes = gm.checkWhite(p, res);

        //Nothing should have changed
        assertTrue(res.equals(newRes));

        //Now let's try adding some white to our resource
        res.add(ResourceType.BLANK, 2);

        newRes = gm.checkWhite(p, res);
        //The white should be removed

        Resources res2 = new Resources();
        res2.add(ResourceType.STONES, 2);
        res2.add(ResourceType.SHIELDS, 1);

        //Check if it is equal to res2, the one without the blank
        assertTrue(res2.equals(newRes));

        //Now trying to add a leader with the white replacement ability in the players hand and then play it
        //lead_9 gives us servants instead of blank

        LeadCard lead_9 = CardManager.loadLeadCardsFromJson().get(8);
        LeadCard[] leaders = new LeadCard[2];
        leaders[0] = lead_9;
        //Set leader in players hand
        p.getLeaders().setCards(leaders);
        //Play leader
        try {
            p.getLeaders().playCard(0);
        }catch (Exception e){
            e.printStackTrace();
        }

        res2.add(ResourceType.BLANK, 2);

        Resources res3 = new Resources();
        res3.add(ResourceType.SHIELDS, 1);
        res3.add(ResourceType.STONES, 2);
        res3.add(ResourceType.SERVANTS, 2);

        //After the check res 2 should be equal to res 3

        newRes = gm.checkWhite(p, res2);

        assertTrue(res3.equals(newRes));


        //Now adding the other leader
        //This time player will need to make a choice
        //lead_12 gives us coins instead of blank

        LeadCard lead_12 = CardManager.loadLeadCardsFromJson().get(11);
        leaders[1] = lead_12;
        //Set leader in players hand
        p.getLeaders().setCards(leaders);
        //Play both leaders
        try {
            p.getLeaders().playCard(0);
            p.getLeaders().playCard(1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Resources res4 = new Resources();
        res4.add(ResourceType.STONES, 2);
        res4.add(ResourceType.BLANK, 3);

        Resources res5 = new Resources();
        res5.add(ResourceType.COINS, 3);
        res5.add(ResourceType.STONES, 2);

        //After the check res 4 should be equal to res 5

        //We choose the second leader as our resource type (coin)
        Resources choice = new Resources();
        choice.add(ResourceType.COINS, 1);
        ChooseResourceActionData pickedRes = new ChooseResourceActionData();
        pickedRes.setRes(choice);
        pickedRes.setPlayer("Player 1");
        MockResponse MR1 = new MockResponse(communicationHandler, Action.CHOOSE_RESOURCE, pickedRes);
        MR1.startSendingResponse();

        newRes = gm.checkWhite(p, res4);

        assertTrue(res5.equals(newRes));

        MR1.stopSendingResponse();


    }

    @Test
    void endGame(){

        GameManager gm = new GameManager(communicationHandler);

        gm.addPlayer("Player 1");
        gm.addPlayer("Player 2");

        //Using faith points to give victory points since it's the easiest way

        //Player 1 gets 2 victory points
        gm.getGame().getPlayers()[0].getBoard().incrementFaithPoints(10);

        //Player 2 gets 12 victory points
        gm.getGame().getPlayers()[1].getBoard().incrementFaithPoints(20);

        //While it may sound stupid, it is possible to end game at any given point,
        //the GameManager will calculate points for the players as it is
        gm.endGame();

    }

    @Test
    void resourceMarketUpdate(){

        //Adding one player to the game
        GameManager gm = new GameManager(communicationHandler);
        gm.addPlayer("Player 1");
        Player p = gm.getGame().getPlayers()[0];

        //Player has chosen the first row

        //Saving first row for later
        Resources res = new Resources();
        //System.out.println(gm.getGame().getResourceMarket().getColumns());
        res.add(gm.getGame().getResourceMarket().getAvailable()[0][0].getValue());
        res.add(gm.getGame().getResourceMarket().getAvailable()[0][1].getValue());
        res.add(gm.getGame().getResourceMarket().getAvailable()[0][2].getValue());
        res.add(gm.getGame().getResourceMarket().getAvailable()[0][3].getValue());

        //removing faith points (player can't add those in his warehouse)

        int fp = 0;

        if (res.getAmountOf(ResourceType.FAITH) > 0) {
            //increase the faith points
           fp++;
            //remove the faith from resources
            try {
                res.remove(ResourceType.FAITH, res.getAmountOf(ResourceType.FAITH));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Remember without the blanks
        try{
            res.remove(ResourceType.BLANK, res.getAmountOf(ResourceType.BLANK));
        }catch (Exception e){
            e.printStackTrace();
        }

        //the player will be asked to update his warehouse

        Warehouse wh = new Warehouse();
        //Warning: It probably it's not possible to put all the resources the player has in a single warehouse slot
        //But for the sake of testing we will suppose so

        wh.deposit(res, 1);
        PutResourcesActionData putres = new PutResourcesActionData();
        putres.setWh(wh);
        putres.setPlayer("Player 1");
        MockResponse MR1 = new MockResponse(communicationHandler, Action.PUT_RESOURCES, putres);
        MR1.startSendingResponse();

        gm.resourceMarketUpdate(p, true, 0);

        try {
            TimeUnit.MILLISECONDS.sleep(WAIT_TIME);
        }catch (Exception e){
            e.printStackTrace();
        }
        MR1.stopSendingResponse();

        //Check that the faith points have been added correctly
        assertEquals(fp, p.getBoard().getFaithPoints());
        //System.out.println("Fp:");
        //System.out.println(fp);

        //Check that the resources have been added correctly


        //System.out.println(res.getTotalAmount());
        //System.out.println(gm.getGame().getPlayers()[0].getBoard().getWarehouse().getResourcesAvailable().getTotalAmount());
        //System.out.println(wh.getResourceAmountWarehouse());

        //The resources available should be the ones that were in the market tray
        assertTrue(res.equals(p.getBoard().getWarehouse().getResourcesAvailable()));

    }

    @Test
    void devCardMarketUpdate(){

        //Adding one player to the game
        GameManager gm = new GameManager(communicationHandler);
        gm.addPlayer("Player 1");
        Player p = gm.getGame().getPlayers()[0];

        //Supposing player has chosen the first card from the DevCards b_1
        DevCard chosen = CardManager.loadDevCardsFromJson().get(0);

        //Suppose he wants to put it at the first position (it should be free)
        gm.devCardMarketUpdate(p, chosen, 0);

        //The card should have not been added, because the player lacks the funds
        assertEquals(0, p.getBoard().getOwnedDevCards().size());


        //Now let's add 2 coins to the player board (the cards price)
        Resources res = new Resources();
        res.add(ResourceType.COINS, 2);
        p.getBoard().getWarehouse().deposit(res, 0);

        //Recalling the player trying to buy the card
        gm.devCardMarketUpdate(p, chosen, 0);

        //This time the card should be in players board
        assertEquals(chosen, p.getBoard().getOwnedDevCards().get(0));

        //And the warehouse should be empty
        assertEquals(0, p.getBoard().getWarehouse().getResourceAmountWarehouse());


        //Now supposing the player wants to buy another level 1 card but wants to put it above this one
        //This move is illegal, so even if he has the resources he shouldn't be able to do so

        //The card is b_2
        DevCard chosen2 = CardManager.loadDevCardsFromJson().get(1);

        //It costs 1 coin, 1 servant and 1 stone
        Resources res2 = new Resources();
        res2.add(ResourceType.COINS, 1).add(ResourceType.STONES, 1).add(ResourceType.SERVANTS, 1);
        //Adding these resource in one warehouse floor is an illegal move, but we will ignore this for now
        //since it doesn't have to do with this test
        p.getBoard().getWarehouse().deposit(res2, 1);

        //Player tries to put card above the other one
        gm.devCardMarketUpdate(p, chosen2, 0);

        //The card should have not been added so only the previous card should be in his board
        assertEquals(1, p.getBoard().getOwnedDevCards().size());

        //Also the resources should have not been touched
        assertEquals(3, p.getBoard().getWarehouse().getResourceAmountWarehouse());


        //If he on the other hand tries to put it on the second position, which is free, there should be no problems
        gm.devCardMarketUpdate(p, chosen2, 1);

        assertEquals(2, p.getBoard().getOwnedDevCards().size());
        assertEquals(0, p.getBoard().getWarehouse().getResourceAmountWarehouse());

    }

    @Test
    void produce(){

        //Adding one player to the game
        GameManager gm = new GameManager(communicationHandler);
        gm.addPlayer("Player 1");
        Player p = gm.getGame().getPlayers()[0];

        //Supposing the player wants to activate 2 productions
        //One which costs res and adds res and faith points
        //One which costs a choice and adds a choice


    }

}