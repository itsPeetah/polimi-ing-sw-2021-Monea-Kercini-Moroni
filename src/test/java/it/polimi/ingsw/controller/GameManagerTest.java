package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;

import it.polimi.ingsw.model.game.util.GameCustomizationSettings;
import it.polimi.ingsw.model.game.util.GameFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void setup(){

        GameManager gm = new GameManager();

        gm.addPlayer("Player 1");

        // gm.setupGame(); --This line would create a loop since the game is forever waiting for player input
    }

    @Test
    void addPlayer(){
        GameManager gm = new GameManager();

        gm.addPlayer("Player 1");
    }




}