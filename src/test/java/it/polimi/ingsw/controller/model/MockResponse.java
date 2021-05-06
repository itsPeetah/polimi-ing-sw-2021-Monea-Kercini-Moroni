package it.polimi.ingsw.controller.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.CommunicationHandler;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.controller.model.actions.ActionPacket;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for manually sending actions to ActionHandler.
 */
public class MockResponse {
    Thread sendingThread;
    AtomicBoolean isInterrupted = new AtomicBoolean();

    /**
     * Create a mock response.
     * @param action action to be sent.
     * @param data data of the action.
     */
    protected MockResponse(CommunicationHandler communicationHandler, Action action, ActionData data) {
        Gson gson = new Gson();
        String stringData = gson.toJson(data, action.getClassOfData());
        ActionPacket actionPacket = new ActionPacket(action, stringData);
        sendingThread = new Thread(() -> {
            while(!isInterrupted.get()){
                communicationHandler.notify(actionPacket);
            }
        });
    }

    /**
     * Start sending the requested action as a response.
     * As this method is called, every WAIT_TIME milliseconds the action will be notified to the ActionHandler.
     */
    protected void startSendingResponse() {
        sendingThread.start();
    }

    /**
     * Stop sending the requested action.
     */
    protected void stopSendingResponse() {
        while(!isInterrupted.get()) isInterrupted.compareAndSet(false, true);
    }
}
