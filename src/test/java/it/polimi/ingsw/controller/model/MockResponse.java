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
    private static final int SLEEP_TIME = 1000;
    private final Thread sendingThread;
    private final AtomicBoolean isInterrupted = new AtomicBoolean();
    private final ActionPacket actionPacket;
    private final CommunicationHandler communicationHandler;

    /**
     * Create a mock response.
     * @param action action to be sent.
     * @param data data of the action.
     */
    protected MockResponse(CommunicationHandler communicationHandler, Action action, ActionData data) {
        Gson gson = new Gson();
        this.communicationHandler = communicationHandler;
        String stringData = gson.toJson(data, action.getClassOfData());
        actionPacket = new ActionPacket(action, stringData);
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

    protected void sendResponseWithDelay(int secondsToWait) {
        new Thread(() -> {
            try {
                Thread.sleep((long)secondsToWait * SLEEP_TIME);
            } catch (InterruptedException e) {
                System.out.println("Error in MockResponse!");
                e.printStackTrace();
            }
            communicationHandler.notify(actionPacket);
        }).start();
    }
}
