package it.polimi.ingsw.model.events;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.model.events.Action.*;

/* CODE EXAMPLE FOR HANDLER USAGE:

        // Make a new request to the EventHandler for an action from a player
        EventHandler.setExpected(requestAction, requestPlayer);

        // Ask for the result. IMPORTANT: this method is a BLOCKING method, meaning that it will stop the thread of the controller
        YourEventData data = EventHandler.waitForExpectedData();

        // Now you can freely use the retrieved data (this is an example)
        String stringRequested = data.useYourEventDataMethod();

     */

public class EventHandler {
    private static final Object lock = new Object();
    private static final List<Action> requestAction = new ArrayList<>();
    private static String requestPlayer = null;
    private static EventData responseData = null;
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

            EventHandler.clear();

            // Set the player to wait for
            EventHandler.requestPlayer = requestPlayer;

            // Finally, add the action
            EventHandler.addExpectedAction(requestedAction);

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
            EventHandler.requestAction.add(requestedAction);

        }
    }

    /**
     * Set a response event from an incoming packet.
     * @param eventMessage message from a player
     */
    public static void notify(EventMessage eventMessage) {
        Action responseAction = eventMessage.getAction();
        EventData responseData = responseAction.parseData(eventMessage.getData());
        synchronized(lock) {

            // If the action is among the expected ones and the performing player is also the expected one, save the data and notify the controller.
            if(requestAction.contains(responseAction) && responseData.getPlayer().equals(requestPlayer)) {

                // Set the response attributes
                EventHandler.responseData = responseData;
                EventHandler.responseAction = responseAction;

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
    public static <T extends EventData> T getResponseData() {
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
