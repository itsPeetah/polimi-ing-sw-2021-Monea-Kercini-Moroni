package it.polimi.ingsw.network.server.metapackets.actions;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.server.metapackets.actions.Action.*;

/* CODE EXAMPLE FOR HANDLER USAGE:

        // Make a new request to the ActionHandler for an action from a player
        ActionHandler.setExpectedAction(requestAction, requestPlayer);

        // Ask for the result. IMPORTANT: this method is a BLOCKING method, meaning that it will stop the thread of the controller
        YourActionData data = ActionHandler.getResponseData();

        // Now you can freely use the retrieved data (this is an example)
        String stringRequested = data.useYourActionDataMethod();

        NOTE: if you wanna wait for the first of MORE THAN ONE action, do something like this!

        // Make a new request to the ActionHandler for an action from a player.
        // IMPORTANT: It's IMPORTANT that this method is called BEFORE you add other expected actions.
        // Because this methods sets the expected player and removes the previous request.
        ActionHandler.setExpectedAction(requestAction1, requestPlayer);

        // Add how many actions as you like
        // IMPORTANT: It's important that this method is called AFTER you called setExpectedAction as said before.
        ActionHandler.addExpectedAction(requestAction2);
        ActionHandler.addExpectedAction(requestAction3);


        // Ask for the result. IMPORTANT: both these methods are BLOCKING methods, meaning that they will stop the thread of the controller if a response has not arrived yet!
        Action performedAction = ActionHandler.getResponseAction();
        YourActionData data = ActionHandler.getResponseData();

        // Now you can freely use the retrieved data (this is an example)
        String stringRequested = data.useYourActionDataMethod();

*/

public class ActionHandler {
    private static final Object lock = new Object();
    private static final List<Action> requestAction = new ArrayList<>();
    private static String requestPlayer = null;
    private static ActionData responseData = null;
    private static Action responseAction = NONE;
    private static Boolean ready = false;
    private static final String EXCEPTION_MESSAGE = "Exception thrown by EventHandler";

    /**
     * Add an expected action and the player that must perform it.
     * Every time this method is called, the old expected actions are deleted.
     * @param requestedAction action to be performed.
     * @param requestPlayer username of the player that must perform the action. If another action has already been added, make sure that the player for this action is the same one!
     */
    public static void setExpectedAction(Action requestedAction, String requestPlayer) {
        synchronized(lock) {

            ActionHandler.clear();

            // Set the player to wait for
            ActionHandler.requestPlayer = requestPlayer;

            // Finally, add the action
            ActionHandler.addExpectedAction(requestedAction);

        }
    }

    /**
     * Add an expected action.
     * @apiNote This method must be called ONLY AFTER a setExpectedAction call!
     * @param requestedAction action to be performed.
     */
    public static void addExpectedAction(Action requestedAction) {
        synchronized(lock) {

            // Add the action
            ActionHandler.requestAction.add(requestedAction);

        }
    }

    /**
     * Set a response event from an incoming packet.
     * @param actionPacket message from a player
     */
    public static void notify(ActionPacket actionPacket) {
        Action responseAction = actionPacket.getAction();
        ActionData responseData = responseAction.parseData(actionPacket.getData());
        synchronized(lock) {

            // If the action is among the expected ones and the performing player is also the expected one, save the data and notify the controller.
            if(requestAction.contains(responseAction) && responseData.getPlayer().equals(requestPlayer)) {

                // Set the response attributes
                ActionHandler.responseData = responseData;
                ActionHandler.responseAction = responseAction;

                // Notify the controller
                ready = true;
                lock.notifyAll();

            }
        }
    }

    /**
     * Get the response action for the last request.
     * If the response has not arrived yet, the caller is blocked.
     */
    public static Action getResponseAction() {
        synchronized(lock) {
            // Check if the player generated a response
            while (!ready) {
                try {
                    // If not, wait.
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Once the response arrived, return it, casted to the correct type.
            return responseAction;
        }
    }

    /**
     * Get the response data for the last request.
     * If data has not arrived yet, the caller is blocked.
     * @param <T> type of the requested data.
     */
    public static <T extends ActionData> T getResponseData() {
        synchronized(lock) {
            // Check if the player generated a response
            while (!ready) {
                try {
                    // If not, wait.
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Once the response arrived, return it, casted to the correct type.
            return (T)responseData;
        }
    }

    /**
     * Reset the expected player and actions fields. This has to be called after a successful notify
     */
    private static void clear() {
        ready = false;
        requestAction.clear();
        requestPlayer = null;
    }
}
