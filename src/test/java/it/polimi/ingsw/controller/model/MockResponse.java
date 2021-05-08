package it.polimi.ingsw.controller.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for manually sending actions to ActionHandler.
 */
public class MockResponse {
    private static final int SLEEP_TIME = 100;
    private final Thread sendingThread;
    private final AtomicBoolean isInterrupted = new AtomicBoolean();
    private final NetworkPacket networkPacket;
    private final ModelControllerIOHandler modelControllerIOHandler;

    /**
     * Create a mock response.
     * @param action action to be sent.
     * @param data data of the action.
     */
    protected MockResponse(ModelControllerIOHandler modelControllerIOHandler, Action action, ActionData data) {
        Gson gson = new Gson();
        this.modelControllerIOHandler = modelControllerIOHandler;
        String stringData = gson.toJson(data, action.getClassOfData());
        ActionPacket actionPacket = new ActionPacket(action, stringData);
        networkPacket = NetworkPacket.buildActionPacket(actionPacket);
        sendingThread = new Thread(() -> {
            while(!isInterrupted.get()){
                modelControllerIOHandler.notify(networkPacket);
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

    protected void sendResponseWithDelay(int unitsOfTime) {
        new Thread(() -> {
            try {
                Thread.sleep((long)unitsOfTime * SLEEP_TIME);
            } catch (InterruptedException e) {
                System.out.println("Error in MockResponse!");
                e.printStackTrace();
            }
            modelControllerIOHandler.notify(networkPacket);
        }).start();
    }
}
