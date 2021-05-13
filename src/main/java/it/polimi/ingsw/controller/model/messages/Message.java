package it.polimi.ingsw.controller.model.messages;

public enum Message {
    ERROR("ERROR"),
    OK("Action executed correctly."),
    GAME_HAS_STARTED("The game has begun!"),
    CHOOSE_LEADERS("Choose your 2 leaders to keep in your hand."),
    CHOOSE_RESOURCE("Please choose a resource of your choice."),
    INCORRECT_SUBSTITUTION("You don't have a leader that lets you get that resource."),
    START_TURN("It's your turn!"),
    NOT_ENOUGH_RESOURCES("You don't have enough resources."),
    ILLEGAL_CARD_PLACE("You can't put the card there."),
    REQUIREMENTS_NOT_MET("You don't meet the requirements to play this card."),
    ALREADY_USED_PRIMARY_ACTION("You have already used your primary action."),
    WAREHOUSE_UNORGANIZED("Your warehouse is not organized."),
    WINNER("VICTORY!"),
    LOSER("WASTED! You were no match for Lorenzo's magnificence!"),
    LOSER_MULTIPLAYER("WASTED!");

    private final String message;

    Message(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
