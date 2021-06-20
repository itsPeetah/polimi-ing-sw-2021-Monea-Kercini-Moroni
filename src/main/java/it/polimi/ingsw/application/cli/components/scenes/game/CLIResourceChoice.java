package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.CLIGame;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.ChooseResourceActionData;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;

public class CLIResourceChoice extends CLIScene implements CLIGameSubScene {

    Resources chosenResources;

    public CLIResourceChoice(){
        super();
        chosenResources = null;
    }

    @Override
    public void update(GameData data) {
        chosenResources = new Resources();
    }

    @Override
    public void show() {
        println("CHOOSE RESOURCES:");
        println("1) "+ ANSIColor.PURPLE + "SERVANTS" + ANSIColor.RESET);
        println("2) "+ ANSIColor.YELLOW + "COINS" + ANSIColor.RESET);
        println("3) "+ ANSIColor.BLUE + "SHIELDS" + ANSIColor.RESET);
        println("4) "+ ANSIColor.GREEN + "STONES" + ANSIColor.RESET);
    }

    @Override
    public void help() {
        println("Use command \"pick <num>\" to choose a resource (num must be between 1 and 4).");
    }

    @Override
    public void execute(String command, String[] arguments) {
        switch (command){
            case "help":
                help();
                break;
            case "pick":
                if(arguments == null || arguments.length < 1) error("Invalid argument.");
                else{
                    try{
                        int res = Integer.parseInt(arguments[0]);
                        if(res < 1 || res > 4) error("Invalid argument: input a number between 1 and 4.");
                        else {
                            switch (res){
                                case 1: pickResource(ResourceType.SERVANTS); break;
                                case 2: pickResource(ResourceType.COINS); break;
                                case 3: pickResource(ResourceType.SHIELDS); break;
                                case 4: pickResource(ResourceType.STONES); break;
                            }
                        }
                    } catch (NumberFormatException ex){
                        error("Invalid argument: invalid number format.");
                    }
                }
                break;
        }
    }

    private void pickResource(ResourceType t){
        Resources r = new Resources().add(t, 1);
        ChooseResourceActionData actionData = new ChooseResourceActionData();
        actionData.setPlayer(GameApplication.getInstance().getUserNickname());
        actionData.setRes(r);

        ActionPacket ap = new ActionPacket(Action.CHOOSE_RESOURCE, JSONUtility.toJson(actionData, ChooseResourceActionData.class));
        /*GameApplicationIOHandler.getInstance().pushAction(ap);*/

        CLIGame.pushAction(ap);
    }
}
