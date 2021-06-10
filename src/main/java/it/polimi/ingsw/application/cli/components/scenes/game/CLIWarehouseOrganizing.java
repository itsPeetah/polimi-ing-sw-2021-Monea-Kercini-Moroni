package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIResourcesToPut;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIWarehouse;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;
import it.polimi.ingsw.view.data.player.Warehouse;

public class CLIWarehouseOrganizing extends CLIScene implements CLIGameSubScene {

    private Warehouse tempWarehouse;
    private Resources queuedResources;

    public CLIWarehouseOrganizing(){
        super();
        tempWarehouse = new Warehouse();
        queuedResources = new Resources();
    }

    @Override
    public void update(GameData data) {

        Warehouse gameWarehouse = data.getPlayerData(GameApplication.getInstance().getUserNickname()).getWarehouse();
        ResourcesToPut rtp = data.getMomentary().getResourcesToPut();

        tempWarehouse = new Warehouse();
        queuedResources = new Resources();

        tempWarehouse.setContent(gameWarehouse.getContent());
        queuedResources.add(rtp.getRes());
    }

    @Override
    public void show() {
        println("Organize your warehouse:");
        ASCIIWarehouse.draw(getWarehouse());
        ASCIIResourcesToPut.draw(getResourcesToPut());
        println("+----------------------+");
    }

    @Override
    public void help() {
        println("Use command \"put <res> <cell>\" to put a resource in a warehouse cell.");
        println("Use command \"move <from> <to>\" to move a resource from a warehouse cell to another.");
        println("Use command \"remove <cell>\" to remove a resource from the specified cell.");
        println("Use command \"confirm\" or \"ok\" to confirm and submit.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        switch (command){
            case "help": help(); break;
            case "put":
                break;
            case "move":
                break;
            case "confirm":
            case "ok":

                break;
        }
    }

    private Warehouse getWarehouse(){
        return GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getWarehouse();
    }

    private ResourcesToPut getResourcesToPut(){
        return GameApplication.getInstance().getGameController().getGameData().getMomentary().getResourcesToPut();
    }


}
