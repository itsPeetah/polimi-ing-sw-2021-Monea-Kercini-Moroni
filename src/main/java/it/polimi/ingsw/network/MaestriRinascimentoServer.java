package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.GameServer;

public class MaestriRinascimentoServer {

    private static final String defaultHostName = "localhost";
    private static final int defaultPortNumber = 42069;

    private static String hostName;
    private static int portNumber;

    public static void main(String[] args) {

        // TODO Get from args/read from file
        hostName = defaultHostName;
        portNumber = defaultPortNumber;

        GameServer server = new GameServer(hostName, portNumber).setAsInstance();
        server.execute();
    }


}
