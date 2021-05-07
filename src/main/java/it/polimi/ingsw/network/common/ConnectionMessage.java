package it.polimi.ingsw.network.common;

public enum ConnectionMessage {
    WELCOME("WELCOME"),
    HELLO("HELLO"),
    ASSIGNID("ID"),
    READY("READY"),
    OK("OK"),
    ERR("ERR");

    public static final String welcomeMessage = WELCOME.addBody("Welcome to the server!");
    public static final String unexpectedReplyError = ERR.addBody("Connection refused: unexpected reply from client.");
    public static final String connectionReadyMessage = READY.addBody("You are now connected to the server!");

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
