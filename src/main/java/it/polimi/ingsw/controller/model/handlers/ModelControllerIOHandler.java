package it.polimi.ingsw.controller.model.handlers;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.network.common.NetworkPacket;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.controller.model.actions.Action.*;

/**
 * Abstract helper class that handle the input and output of the controller.
 * There are two different concrete implementations: one for MultiPlayer games (sending packets through the network) and one for SinglePlayer games (communicating directly with the view-controller).
 */
public abstract class ModelControllerIOHandler {

    /* ACTION HANDLER ATTRIBUTES */


    private final Object lock;
    private final List<Action> requestAction;
    private String requestPlayer;
    private ActionData responseData;
    private Action responseAction;
    private Boolean ready;

    /**
     * Constructor
     */
    public ModelControllerIOHandler() {
        lock = new Object();
        requestAction = new ArrayList<>();
        requestPlayer = null;
        responseData = null;
        responseAction = NONE;
        ready = false;
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
     * Set a response event from an incoming network packet.
     * @param actionNetworkPacket message from a player
     */
    public void notify(NetworkPacket actionNetworkPacket) {
        Gson gson = new Gson();
        ActionPacket actionPacket = gson.fromJson(actionNetworkPacket.getPayload(), ActionPacket.class);
        notify(actionPacket);
    }

    /**
     * Set a response event from an incoming action packet.
     * @param actionPacket message from a player
     */
    private void notify(ActionPacket actionPacket) {
        Action responseAction = actionPacket.getAction();
        ActionData responseData = responseAction.fromJsonToData(actionPacket.getData());
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
    public abstract void sendMessage(String player, Message message);

    /* UPDATE HANDLER METHODS */

    /**
     * Push an update to be sent to all players.
     */
    public abstract void pushUpdate(Update type, UpdateData data);
}
