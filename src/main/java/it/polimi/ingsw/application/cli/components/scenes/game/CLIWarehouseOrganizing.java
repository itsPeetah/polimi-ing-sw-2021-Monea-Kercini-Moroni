package it.polimi.ingsw.application.cli.components.scenes.game;

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
        printWarehouse();
        printResourcesToPut();
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
                tryPutInWarehouse(arguments);
                break;
            case "move":
                break;
            case "confirm":
            case "ok":

                break;
        }
    }

    private void printWarehouse(){

        Resources[] inWarehouse = tempWarehouse.getContent();
        Resources current;

        for(int i = inWarehouse.length - 1; i >= 0; i--){
            current = inWarehouse[i];
            if(i == 2){
                println("[" + warehouseResourceAsString(getResourceType(current), 6) + "]");
            }
        }
    }

    private String warehouseResourceAsString(ResourceType resourceType, int fallbackCellNumber){
        String s = "";
        if(resourceType == null) return Integer.toString(fallbackCellNumber);
        switch (resourceType){
            case SERVANTS: s += ANSIColor.PURPLE; break;
            case COINS: s += ANSIColor.YELLOW; break;
            case SHIELDS: s += ANSIColor.BLUE; break;
            case STONES: s += ANSIColor.WHITE_BACKGROUND; break;
        }
        return s + "@" + ANSIColor.RESET;
    }

    private ResourceType getResourceType(Resources resources) {
        for(ResourceType resourceType: ResourceType.values()) {
            if(resources == null) return null;
            int resCount = resources.getAmountOf(resourceType);
            if(resCount > 0) return resourceType;
        }
        return null;
    }

    private void printResourcesToPut(){

        /*if(resourcesToPut==null) {
            println("You have no resources to put in the warehouse.");
            this.execute("confirm", null);
        }
        else{
            println("You have to organize these resources in the warehouse:");
            int amount;
            for(ResourceType resourceType : ResourceType.values()){
                amount = resourcesToPut.getRes().getAmountOf(resourceType);
                if(amount > 0)
                switch (resourceType){
                    case SERVANTS:
                        println("(A) " + ANSIColor.PURPLE + "Servants" + ANSIColor.RESET +": x"+ amount);
                        break;
                    case COINS:
                        println("(B) " + ANSIColor.YELLOW + "Coins" + ANSIColor.RESET +": x"+ amount);
                        break;
                    case SHIELDS:
                        println("(C) " + ANSIColor.BLUE + "Shields" + ANSIColor.RESET +": x"+ amount);
                        break;
                    case STONES:
                        println("(D) " +ANSIColor.WHITE_BACKGROUND + "Stones" + ANSIColor.RESET +": x"+ amount);
                        break;
                }
            }
        }*/
    }

    private void tryPutInWarehouse(String[] arguments) {
        /*if (arguments == null || arguments.length < 2) {
            error("Missing arguments.");
            return;
        }
        String res = arguments[0].toUpperCase();
        int cell;

        try {
            cell = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException ex) {
            error("Invalid argument: \"cell\" should be an integer.");
            return;
        }

        if (!"ABCD".contains(res)) {
            error("Invalid argument: \"res\" should either be A, B, C or D.");
            return;
        }
        if (cell < 1 || cell > 6) {
            error("Invalid argument: \"cell\" should be between 1 and 6.");
            return;
        }

        ResourceType rt = res.equals("A") ? ResourceType.SERVANTS : res.equals("B") ? ResourceType.COINS :
                res.equals("C") ? ResourceType.SHIELDS : ResourceType.STONES;

        if(resourcesToPut.getRes().getAmountOf(rt) < 1){
            error("You don't own this resource!");
            return;
        }

        int resIndex;
        if(cell <= 1) resIndex = 2;
        else if (cell <= 3) resIndex = 1;
        else resIndex = 0;

        // TODO Move resources from put to warehouse*/

    }

}
