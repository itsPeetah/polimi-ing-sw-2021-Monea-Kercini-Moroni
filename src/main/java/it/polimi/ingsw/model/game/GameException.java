package it.polimi.ingsw.model.game;

/**
 * Game class specific exceptions.
 */
class GameException extends Exception {
    /**
     * @param errorMessage Error message for the exception.
     */
    public GameException(String errorMessage) {
        super(errorMessage);
    }
}