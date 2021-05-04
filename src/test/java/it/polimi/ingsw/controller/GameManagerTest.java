package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.actions.Action;
import it.polimi.ingsw.network.server.metapackets.actions.data.Choose2LeadersActionData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GameManagerTest {

    @Test
    void setup(){

        GameManager gm = new GameManager();

        gm.addPlayer("Player 1");

        //Testing Leaders
        LeadCard myLeaders[] = new LeadCard[2];
        Choose2LeadersActionData TwoLeaders = new Choose2LeadersActionData();
        TwoLeaders.setLeaders(myLeaders);
        TwoLeaders.setPlayer("Player 1");

        MockResponse MR = new MockResponse(Action.CHOOSE_2_LEADERS, TwoLeaders);

        MR.startSendingResponse();

        gm.setupGame();

        MR.stopSendingResponse();
    }

    @Test
    void addPlayer(){
        GameManager gm = new GameManager();

        gm.addPlayer("Player 1");
    }




}