package it.polimi.ingsw.network.server.components;

public class GameRoomException extends Exception{
    /**
     * @param errorMessage Error message for the exception.
     */
    public GameRoomException(String errorMessage) {
        super(errorMessage);
    }
}
