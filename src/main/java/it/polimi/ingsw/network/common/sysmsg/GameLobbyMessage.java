package it.polimi.ingsw.network.common.sysmsg;

/**
 * System messages for the game lobby.
 */
public enum GameLobbyMessage implements SystemMessage {
    IN_LOBBY("LOBBY"),
    CREATE_ROOM("ROOMC"),
    JOIN_ROOM("ROOMJ"),
    LEAVE_ROOM("ROOML"),
    START_ROOM("ROOMS"),
    REJOIN_ROOM("ROOMR"),
    PLAYERS_IN_ROOM("ROOMP");

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
