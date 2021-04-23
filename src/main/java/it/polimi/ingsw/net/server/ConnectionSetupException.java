package it.polimi.ingsw.net.server;

import it.polimi.ingsw.net.messages.ConnectionSetupMessage;

public class ConnectionSetupException extends Exception{

    public ConnectionSetupException() {super();}
    /**
     * @param errorMessage Error message for the exception.
     */
    public ConnectionSetupException(String errorMessage) {
        super(errorMessage);
    }
}
