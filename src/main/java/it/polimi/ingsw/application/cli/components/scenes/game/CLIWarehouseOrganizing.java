package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.data.PutResourcesActionData;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;
import it.polimi.ingsw.view.data.player.Warehouse;

import java.text.NumberFormat;

public class CLIWarehouseOrganizing extends CLIScene implements ICLIGameSubScene {

    private Warehouse whData;
    private ResourcesToPut resourcesToPut;

    private String warehouseBase
            = "    +---+\n"
            + "    | 1 |\n"
            + "  +-+-+-+-+\n"
            + "  | 2 | 3 |\n"
            + "+-+-+-+-+-+-+\n"
            + "| 4 | 5 | 6 |\n"
            + "+---+---+---+\n";

    private final int cell1Index = 16, cell2Index = 36, cell3Index = 24, cell4Index = 60, cell5Index = 64, cell6Index = 68;


    public CLIWarehouseOrganizing(){
        super();
        whData = null;
        resourcesToPut = null;
    }


    @Override
    public void update(GameData data) {
        whData = data.getPlayerData(GameApplication.getInstance().getUserNickname()).getWarehouse();
        resourcesToPut = data.getMomentary().getResourcesToPut();
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

        if(whData == null) println(warehouseBase);
        else{
            Resources[] inWarehouse = whData.getContent();
            ResourceType[] types = new ResourceType[3];
            for (int i = 0; i < inWarehouse.length; i++) {
                types[i] = getResourceType(inWarehouse[i]);
            }

            for(int i = 0; i < warehouseBase.length(); i++){

                if(i == cell1Index)
                    if(inWarehouse[2] != null && inWarehouse[2].getTotalAmount() > 0 && types[2] != null){
                        printResourceInCell(i, types[2]);
                        continue;
                    }
                else if(i == cell2Index){
                    if(inWarehouse[1] != null && inWarehouse[1].getTotalAmount() > 0 && types[1] != null){
                        printResourceInCell(i, types[1]);
                        continue;
                    }
                }
                else if(i == cell3Index){
                    if(inWarehouse[1] != null && inWarehouse[1].getTotalAmount() > 1 && types[1] != null){
                        printResourceInCell(i, types[1]);
                        continue;
                    }
                }
                else if(i == cell4Index){
                    if(inWarehouse[0] != null && inWarehouse[0].getTotalAmount() > 0 && types[0] != null){
                        printResourceInCell(i, types[0]);
                    }
                }
                else if(i == cell5Index){
                    if(inWarehouse[0] != null && inWarehouse[0].getTotalAmount() > 1 && types[0] != null){
                        printResourceInCell(i, types[0]);
                        continue;
                    }
                }
                else if(i == cell6Index){
                    if(inWarehouse[0] != null && inWarehouse[0].getTotalAmount() > 2 && types[0] != null){
                        printResourceInCell(i, types[0]);
                        continue;
                    }
                }

                print(warehouseBase.charAt(i));
            }
        }
    }

    private void printResourceInCell(int cellIndex, ResourceType resourceType){
        switch (resourceType){
            case SERVANTS: print(ANSIColor.PURPLE); break;
            case COINS: print(ANSIColor.YELLOW); break;
            case SHIELDS: print(ANSIColor.BLUE); break;
            case STONES: print(ANSIColor.WHITE_BACKGROUND); break;
        }
        print("@" + ANSIColor.RESET);
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

        if(resourcesToPut==null) {
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
        }
    }

    private void tryPutInWarehouse(String[] arguments) {
        if (arguments == null || arguments.length < 2) {
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

        // TODO Move resources from put to warehouse

    }

}
