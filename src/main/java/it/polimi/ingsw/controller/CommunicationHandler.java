package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.actions.Action;
import it.polimi.ingsw.controller.actions.ActionData;
import it.polimi.ingsw.controller.actions.ActionPacket;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.controller.messages.MessagePacket;
import it.polimi.ingsw.controller.updates.Update;
import it.polimi.ingsw.controller.updates.UpdateData;
import it.polimi.ingsw.controller.updates.UpdatePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.controller.actions.Action.*;

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

public class CommunicationHandler {

    /* ACTION HANDLER ATTRIBUTES */

    private final Object lock;
    private final List<Action> requestAction;
    private String requestPlayer;
    private ActionData responseData;
    private Action responseAction;
    private Boolean ready;

    /* MESSAGE HANDLER ATTRIBUTES */

    private final BlockingQueue<MessagePacket> messageQueue;

    /* UPDATE HANDLER ATTRIBUTES */

    private final BlockingQueue<UpdatePacket> updateQueue;

    /**
     * Constructor
     */
    CommunicationHandler() {
        lock = new Object();
        requestAction = new ArrayList<>();
        requestPlayer = null;
        responseData = null;
        responseAction = NONE;
        ready = false;
        messageQueue = new LinkedBlockingDeque<>();
        updateQueue = new LinkedBlockingDeque<>();
    }

    /* ACTION HANDLER METHODS */

    /**
     * Add an expected action and the player that must perform it.
     * Every time this method is called, the old expected actions are deleted.
     * @param requestedAction action to be performed.
     * @param requestPlayer username of the player that must perform the action. If another action has already been added, make sure that the player for this action is the same one!
     */
    public void setExpectedAction(Action requestedAction, String requestPlayer) {
        synchronized(lock) {

            clear();

            // Set the player to wait for
            this.requestPlayer = requestPlayer;

            // Finally, add the action
            addExpectedAction(requestedAction);

        }
    }

    /**
     * Add an expected action.
     * @apiNote This method must be called ONLY AFTER a setExpectedAction call!
     * @param requestedAction action to be performed.
     */
    public void addExpectedAction(Action requestedAction) {
        synchronized(lock) {

            // Add the action
            requestAction.add(requestedAction);

        }
    }

    /**
     * Set a response event from an incoming packet.
     * @param actionPacket message from a player
     */
    public void notify(ActionPacket actionPacket) {
        Action responseAction = actionPacket.getAction();
        ActionData responseData = responseAction.parseData(actionPacket.getData());
        synchronized(lock) {

            // If the action is among the expected ones and the performing player is also the expected one, save the data and notify the controller.
            if(requestAction.contains(responseAction) && responseData.getPlayer().equals(requestPlayer)) {

                // Set the response attributes
                this.responseData = responseData;
                this.responseAction = responseAction;

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
    public Action getResponseAction() {
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
    public <T extends ActionData> T getResponseData() {
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
    private void clear() {
        ready = false;
        requestAction.clear();
        requestPlayer = null;
    }

    /* MESSAGE HANDLER METHODS */

    /**
     * Send a message to a player.
     * @param player player to whom the message is addressed.
     * @param message type of message.
     */
    public void sendMessage(String player, Message message) {
        MessagePacket newPacket = new MessagePacket(player, message);
        messageQueue.add(newPacket);
    }

    /**
     * Take a message to be sent to a player.
     * WARNING: if no message is present, the caller will be blocked forever.
     */
    public MessagePacket takeMessage() throws InterruptedException {
        return messageQueue.take();
    }

    /**
     * Take a message to be sent to a player.
     * WARNING: if no message is present, the caller will be blocked until the specified timeout is over.
     * When the timeout expires, null is returned.
     */
    public MessagePacket pollMessage(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return messageQueue.poll(timeout, timeUnit);
    }

    /* UPDATE HANDLER METHODS */

    /**
     * Push an update to be sent to all players.
     */
    public void pushUpdate(Update type, UpdateData data) {
        String jsonData = type.parseData(data);
        UpdatePacket updatePacket = new UpdatePacket(type, jsonData);
        updateQueue.add(updatePacket);
    }

    /**
     * Take an update ready to be notified to all players.
     * WARNING: if no update is present, the caller will be blocked forever.
     */
    public UpdatePacket takeUpdate() throws InterruptedException {
        return updateQueue.take();
    }

    /**
     * Take an update ready to be notified to all players.
     * WARNING: if no update is present, the caller will be blocked until the specified timeout is over.
     * When the timeout expires, null is returned.
     */
    public UpdatePacket pollUpdate(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return updateQueue.poll(timeout, timeUnit);
    }
}
