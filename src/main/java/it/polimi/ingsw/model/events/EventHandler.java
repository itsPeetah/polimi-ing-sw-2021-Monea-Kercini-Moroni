package it.polimi.ingsw.model.events;

import static it.polimi.ingsw.model.events.Action.*;

/* CODE EXAMPLE FOR HANDLER USAGE:


        // Get a reference to the singleton EventHandler
        EventHandler eventHandler = EventHandler.getEventHandler();

        // Make a new request to the EventHandler for an action from a player
        eventHandler.makeRequest(requestAction, requestPlayer);

        // Define local variables for needed data
        Warehouse wh = new Warehouse();

        // Retrieve data in synchronization
        synchronized(eventHandler) {
            // Check if the player made a response
            while(!eventHandler.isReady()) {
                try {
                    // If not, wait.
                    eventHandler.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Retrieve data from the event
            ParticularEventData data = (ParticularEventData)eventHandler.getData();

            // Now, save the data to local variables
            wh = data.getWarehouse();
        }

        // Now that data has been saved on local variables, exit the synchronized and do operations on data
        wh.doWhateverYouWant;

     */

public class EventHandler {
    private static EventHandler eventHandler = null;
    private Action requestAction;
    private String requestPlayer;
    private EventData responseData;
    private Boolean ready;

    private EventHandler() {
        requestAction = NONE;
        requestPlayer = null;
        responseData = null;
        ready = false;
    }

    /**
     * Get a reference to the singleton EventHandler
     */
    public static EventHandler getEventHandler() {
        if(eventHandler == null) eventHandler = new EventHandler();
        return eventHandler;
    }

    /**
     * Set the expected action and the player that must perform it.
     * @param requestAction action to be performed.
     * @param requestPlayer username of the player that must perform the action.
     */
    public synchronized void makeRequest(Action requestAction, String requestPlayer) {
        this.requestAction = requestAction;
        this.requestPlayer = requestPlayer;
    }

    /**
     * Set a response event from an incoming packet.
     * @param responseAction action of the response event.
     * @param responsePlayer username of the player performing the response event.
     * @param responseData data of the response event.
     */
    public synchronized void notify(Action responseAction, String responsePlayer, EventData responseData) {
        // If the action is the expected one and the performing player is also the expected one, save the data and notify the controller.
        if(responseAction == requestAction && responsePlayer.equals(requestPlayer)) {
            this.responseData = responseData;
            ready = true;
            notifyAll();
        }
    }

    /**
     * Check if the requested event has been performed and data is ready to be retrieved.
     */
    public synchronized Boolean isReady() {
        return ready;
    }

    /**
     * Get data from the last requested event.
     */
    public synchronized EventData getData() {
        return responseData;
    }


}
