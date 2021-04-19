package it.polimi.ingsw.model.game;

/**
 * MarketTray specific exception.
 */
public class MarketTrayException extends Exception{
    /**
     * @param errorMessage Error message for the exception.
     */
    public MarketTrayException(String errorMessage) {
        super(errorMessage);
    }
}