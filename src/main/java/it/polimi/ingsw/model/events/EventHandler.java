package it.polimi.ingsw.model.events;

import static it.polimi.ingsw.model.events.Action.*;

/* CODE EXAMPLE FOR HANDLER USAGE:

        // Make a new request to the EventHandler for an action from a player
        EventHandler.makeRequest(requestAction, requestPlayer);

        // Ask for the result. IMPORTANT: this method is a BLOCKING method, meaning that it will stop the thread of the controller
        YourEventData data = EventHandler.getResponse();

        // Now you can freely use the retrieved data (this is an example)
        String stringRequested = data.useYourEventDataMethod();

     */

public class EventHandler {
    private static final Object lock = new Object();
    private static Action requestAction = NONE;
    private static String requestPlayer = null;
    private static EventData responseData = null;
    private static Boolean ready = false;

    /**
     * Set the expected action and the player that must perform it.
     * @param requestAction action to be performed.
     * @param requestPlayer username of the player that must perform the action.
     */
    public static void makeRequest(Action requestAction, String requestPlayer) {
        synchronized(lock) {
            EventHandler.requestAction = requestAction;
            EventHandler.requestPlayer = requestPlayer;
        }
    }

    /**
     * Set a response event from an incoming packet.
     * @param responseAction action of the response event.
     * @param responsePlayer username of the player performing the response event.
     * @param responseData data of the response event.
     */
    public static void notify(Action responseAction, String responsePlayer, EventData responseData) {
        synchronized(lock) {
            // If the action is the expected one and the performing player is also the expected one, save the data and notify the controller.
            if(responseAction == requestAction && responsePlayer.equals(requestPlayer)) {
                EventHandler.responseData = responseData;
                ready = true;
                lock.notifyAll();
            }
        }
    }

    /**
     * Get the response for the last requested action.
     * @param <T> type of the requested data.
     */
    public static <T extends EventData> T getResponse() {
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
}
