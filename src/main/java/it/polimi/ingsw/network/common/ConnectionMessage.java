package it.polimi.ingsw.network.common;

public enum ConnectionMessage implements IMessageClass {
    WELCOME("WELCOME"),
    HELLO("HELLO"),
    ASSIGNID("ID"),
    READY("READY"),
    QUIT("QUIT"),
    OK("OK"),
    ERR("ERR");

    public static final String welcomeMessage = WELCOME.addBody("Welcome to the server!");
    public static final String connectionReadyMessage = READY.addBody("You are now connected to the server!");

    public static final String unexpectedReplyError = ERR.addBody("Connection refused: unexpected reply from client.");
    public static final String missingArgumentsError = ERR.addBody("Invalid request: missing arguments.");
    public static final String invalidRequestError = ERR.addBody("Invalid request: the request was not valid.");

    private final String messageCode;

    ConnectionMessage(String code){
        messageCode = code;
    }

    public String getCode() {
        return messageCode;
    }


    public String addBody(String body){
        return messageCode + " " + body;
    }

    public boolean check(String message){
        return messageCode.equals(message);
    }

    public boolean check(String arguments, String message){
        return this.addBody(arguments).equals(message + " ");
    }
}
