package it.polimi.ingsw.network.common.messages;

public enum GameLobbyMessage implements IMessageClass {
    CREATE_ROOM("ROOMC"),
    JOIN_ROOM("ROOMJ");

    private final String messageCode;

    GameLobbyMessage(String code){
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
