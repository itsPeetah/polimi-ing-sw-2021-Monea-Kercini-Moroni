package it.polimi.ingsw.controller.view.game;

public enum GameState {
    IDLE,
    SETUP,
    TURN_CHOICE,
    ORGANIZE_WAREHOUSE,
    ORGANIZE_WAREHOUSE_S, //Extremely rare case where the fourth player has organized his 2 resources incorrectly
    CHOOSE_LEADERS,
    REPLACE_BLANKS,
    PICK_RESOURCES,
    ENDGAME;
}
