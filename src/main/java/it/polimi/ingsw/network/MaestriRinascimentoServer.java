package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.GameServer;

public class MaestriRinascimentoServer {

    private static final String defaultHostName = "localhost";
    private static final int defaultPortNumber = 50000;

    private static String hostName;
    private static int portNumber;

    public static void main(String[] args) {

        if(args.length < 1){
            setHostName(defaultHostName);
            setPortNumber(defaultPortNumber);
        } else if (args.length < 2){
            setHostName(args[0]);
            setPortNumber(defaultPortNumber);
        } else {
            setHostName(args[0]);
            setPortNumber(Integer.parseInt(args[1]));
        }


        GameServer server = new GameServer(hostName, portNumber).setAsInstance();
        server.execute();
    }

    public static void setHostName(String hostName){
        MaestriRinascimentoServer.hostName = hostName;
    }

    public static void setPortNumber(int portNumber){
        MaestriRinascimentoServer.portNumber = portNumber;
    }


}
