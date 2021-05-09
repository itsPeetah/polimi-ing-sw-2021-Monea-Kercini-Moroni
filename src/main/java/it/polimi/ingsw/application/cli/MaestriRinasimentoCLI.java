package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.network.client.GameClient;

public class MaestriRinasimentoCLI {

    public static boolean appRunning;


    public static void main(String[] args){

        appRunning = true;

        GameClient networkClient = new GameClient("localhost", 42069);
        GameApplication gameApplication = new GameApplication(networkClient);

        while(appRunning){

            switch (GameApplication.getInstance().getState()){
                // TODO Do states
            }
        }

    }
}
