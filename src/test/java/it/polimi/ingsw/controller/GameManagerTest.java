package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.actions.Action;
import it.polimi.ingsw.network.server.metapackets.actions.data.Choose2LeadersActionData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameManagerTest {

    @Test
    void setup(){

        GameManager gm = new GameManager();

        gm.addPlayer("Player 1");

        //Testing Leaders
        LeadCard myLeaders[] = new LeadCard[2];
        myLeaders[0]  = CardManager.loadLeadCardsFromJson().get(0);
        Choose2LeadersActionData TwoLeaders = new Choose2LeadersActionData();
        TwoLeaders.setLeaders(myLeaders);
        TwoLeaders.setPlayer("Player 1");
        MockResponse MR = new MockResponse(Action.CHOOSE_2_LEADERS, TwoLeaders);

        MR.startSendingResponse();

        gm.setupGame();

        MR.stopSendingResponse();

        //Testing if we have the player in game
        assertEquals("Player 1", gm.getGame().getPlayers()[0].getNickname() );

        //Testing if we added the correct cart to that player
        assertEquals(myLeaders[0].getCardId(), gm.getGame().getPlayers()[0].getLeaders().getPlayableCards().get(0).getCardId() );
    }

    @Test
    void addPlayer(){
        GameManager gm = new GameManager();

        gm.addPlayer("Player 1");
    }




}