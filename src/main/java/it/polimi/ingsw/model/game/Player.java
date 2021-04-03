package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.playerboard.PlayerBoard;

public class Player {

    private String nickname;

    private PlayerBoard board;
    // private PlayerLeaders leaders;

    public Player(String nickname) {
        this.nickname = nickname;
        this.board = new PlayerBoard();
    }

    public String getNickname() {
        return nickname;
    }

    public PlayerBoard getBoard() {
        return board;
    }

}
