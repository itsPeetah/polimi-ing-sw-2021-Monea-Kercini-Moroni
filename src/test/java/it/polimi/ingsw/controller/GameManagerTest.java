package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void setup(){
        GameManager gm = new GameManager();
        gm.setupGame();
    }




}