package it.polimi.ingsw.network;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.network.server.GameServer;

import java.util.Scanner;

public class MaestriRinascimentoServer {

    private static final String defaultHostName = "25.75.96.19";
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

        Thread debugInput = new Thread(() -> {Scanner in = new Scanner(System.in);
            String s;
            while(true){
                s = in.nextLine();
                switch (s){
                    case "rooms":
                        System.out.println(ANSIColor.YELLOW + "Rooms:" + ANSIColor.RESET);
                        int i = 0;
                        for(String id : server.getRoomTable().getRoomIDs()) System.out.println(++i+ ": " + id);
                        break;
                    case "users":
                        System.out.println(ANSIColor.YELLOW + "Users:" + ANSIColor.RESET);
                        int j = 0;
                        for(String id : server.getUserTable().getUserIDs()) System.out.println(++j+ ": " + id);
                        break;
                }
            }});
        debugInput.setDaemon(true);
        debugInput.start();


        server.execute();
    }

    public static void setHostName(String hostName){
        MaestriRinascimentoServer.hostName = hostName;
    }

    public static void setPortNumber(int portNumber){
        MaestriRinascimentoServer.portNumber = portNumber;
    }


}
