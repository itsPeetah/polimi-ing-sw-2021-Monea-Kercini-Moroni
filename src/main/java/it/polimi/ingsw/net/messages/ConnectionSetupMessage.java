package it.polimi.ingsw.net.messages;

public enum ConnectionSetupMessage {
    WELCOME("WELCOME"),
    HELLO("HELLO"),
    ID("ID"),
    ERR("ERR"),
    OK("OK"),
    READY("READY"),
    STOP("STOP"),
    ROOM_CREATE("R_CREATE"),
    ROOM_JOIN("R_JOIN")
    ;

    private final String messageCode;
    private ConnectionSetupMessage(String messageCode){
        this.messageCode = messageCode;
    }

    public String getMessageCode(){
        return this.messageCode;
    }

    public String composeMessage(String args){
        return this.messageCode + " " + args;
    }
}
