package it.polimi.ingsw.application.common;

public class GameApplicationException extends Exception{
    public GameApplicationException(String errorMessage) {
        super(errorMessage);
    }
}
