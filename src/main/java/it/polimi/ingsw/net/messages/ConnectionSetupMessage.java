package it.polimi.ingsw.net.messages;

public enum ConnectionSetupMessage {
    WELCOME("WELCOME"),
    HELLO("HELLO"),
    ROOM_PROMPT("ROOM"),
    ROOM_CREATE("R-CREATE"),
    ROOM_JOIN("R-JOIN"),
    ROOM_ERR("R-ERROR"),
    NICK_PROMPT("NICK"),
    ERR("ERR"),
    OK("OK")
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
