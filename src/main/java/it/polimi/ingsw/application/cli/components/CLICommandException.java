package it.polimi.ingsw.application.cli.components;

public class CLICommandException extends Exception{
    /**
     * @param errorMessage Error message for the exception.
     */
    public CLICommandException(String errorMessage) {
        super(errorMessage);
    }
}
