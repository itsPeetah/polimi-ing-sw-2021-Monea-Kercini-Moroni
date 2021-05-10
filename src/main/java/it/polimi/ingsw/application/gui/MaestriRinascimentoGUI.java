package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.network.client.GameClient;

public class MaestriRinascimentoGUI {

    private GameClient networkClient;
    private GameApplication gameApplication;

    public static boolean appRunning;

    public static void main(String[] args){

        appRunning = true;

        /*GameClient networkClient = new GameClient("localhost", 42069);*/
        GameApplication gameApplication = new GameApplication();

        while(appRunning){

            switch (GameApplication.getInstance().getApplicationState()){
                // TODO Do states
            }
        }

    }
}
