package it.polimi.ingsw.network.common.sysmsg;

/**
 * System messages for the game lobby.
 */
public enum GameLobbyMessage implements ISystemMessage {
    CREATE_ROOM("ROOMC"),
    JOIN_ROOM("ROOMJ"),
    LEAVE_ROOM("ROOML"),
    START_ROOM("ROOMS");

    private final String messageCode;

    /**
     * Constructor
     */
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
        String[] fields = message.split(" ");
        return messageCode.equals(fields[0]);
    }

    public boolean check(String arguments, String message){
        return this.addBody(arguments).equals(message);
    }
}
