package it.polimi.ingsw.controller;


import org.junit.jupiter.api.Test;

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