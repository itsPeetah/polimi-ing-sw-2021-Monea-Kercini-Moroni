package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.network.server.metapackets.actions.Action;
import it.polimi.ingsw.network.server.metapackets.actions.ActionData;
import it.polimi.ingsw.network.server.metapackets.actions.ActionHandler;
import it.polimi.ingsw.network.server.metapackets.actions.ActionPacket;

/**
 * Class for manually sending actions to ActionHandler.
 */
public class MockResponse {
    public final static int WAIT_TIME = 10;
    Thread sendingThread;

    /**
     * Create a mock response.
     * @param action action to be sent.
     * @param data data of the action.
     */
    protected MockResponse(Action action, ActionData data) {
        Gson gson = new Gson();
        String stringData = gson.toJson(data, action.getClassOfData());
        ActionPacket actionPacket = new ActionPacket(action, stringData);
        sendingThread = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ActionHandler.notify(actionPacket);
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
        sendingThread.interrupt();
    }
}
