package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.ExSocket;

public class RemoteUser {

    private final String id;
    private ExSocket socket;

    /**
     * Class constructor.
     */
    public RemoteUser(String id, ExSocket socket){
        this.id = id;
        this.socket = socket;
    }

    public String getId() {return id;}
    public ExSocket getSocket() {return socket;}
}
