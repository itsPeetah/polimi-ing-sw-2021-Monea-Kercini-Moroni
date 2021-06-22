package it.polimi.ingsw.network.common;

/**
 * Enumeration for System Message (SYSTEM) NP types.
 */
public enum SystemMessage {
    WELCOME("WELCOME"),
    HELLO("HELLO"),
    ASSIGNID("ID"),
    READY("READY"),
    QUIT("QUIT"),
    OK("OK"),
    ERR("ERR"),
    PING("PING"),
    // Ex lobby messages
    IN_LOBBY("LOBBY"),
    CREATE_ROOM("ROOMC"),
    JOIN_ROOM("ROOMJ"),
    QUICK_START("QUICKSTART"),
    LEAVE_ROOM("ROOML"),
    START_ROOM("ROOMS"),
    REJOIN_ROOM("ROOMR"),
    PLAYERS_IN_ROOM("ROOMP"),
    IN_ROOM("INROOM"),
    IN_GAME("INGAME"),
    GAME_OVER("GAMEOVER"),
    CANT_JOIN("ROOMF"),
    // Ex social
    CHAT("CHAT"),
    WHISPER("WHISPER");

    public static final String welcomeMessage = WELCOME.addBody("Welcome to the server!");
    public static final String connectionReadyMessage = READY.addBody("You are now connected to the server!");

    public static final String unexpectedReplyError = ERR.addBody("Connection refused: unexpected reply from client.");
    public static final String invalidLobbyRequestError = CANT_JOIN.addBody("Invalid request: the request was not valid.");
    public static final String missingArgumentsWhileJoiningError = CANT_JOIN.addBody("Invalid request: missing arguments.");

    private final String messageCode;

    SystemMessage(String code){
        messageCode = code;
    }

    /**
     * Message code getter.
     */
    public String getCode() {
        return messageCode;
    }

    /**
     * Append arguments to the message code.
     * @param body The arguments for the message.
     * @return The full message.
     */
    public String addBody(String body){
        return messageCode + " " + body;
    }

    /**
     * Compare message code with string.
     */
    public boolean check(String message){
        return messageCode.equals(message);
    }

    /**
     * Compare message with string.
     * @param arguments Expected arguments.
     * @param message Message to compare.
     */
    public boolean check(String arguments, String message){
        return this.addBody(arguments).equals(message);
    }
}
