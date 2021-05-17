package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.game.*;
import it.polimi.ingsw.controller.view.game.GameController;

public class CLIGame extends CLIScene {

    private CLIBoard board;
    private CLIDevCardMarket devCardMarket;
    private CLIFaithTrack faithTrack;
    private CLILeadChoice leadChoice;
    private CLIResourceChoice resourceChoice;
    private CLIResourceMarket resourceMarket;


    public CLIGame() {
        super();
        this.board = new CLIBoard();
        this.devCardMarket = new CLIDevCardMarket();
        this.faithTrack = new CLIFaithTrack();
        this.leadChoice = new CLILeadChoice();
        this.resourceChoice = new CLIResourceChoice();
        this.resourceMarket = new CLIResourceMarket();

    }

    @Override
    public void show() {
        print("========= Masters of Renaissance - In Game =========");

        print("");print("");print("");

        print("====================================================");
    }


}
