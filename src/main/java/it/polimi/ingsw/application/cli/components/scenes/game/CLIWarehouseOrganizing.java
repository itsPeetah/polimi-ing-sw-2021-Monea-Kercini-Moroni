package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIResources;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIWarehouse;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.CLIGame;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.PutResourcesActionData;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.general.ResourcesException;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;
import it.polimi.ingsw.view.data.player.Warehouse;

/**
 * CLIScene for organizing the warehouse
 */
public class CLIWarehouseOrganizing extends CLIScene  {

    private Warehouse tempWarehouse;
    private Resources queuedResources;

    public CLIWarehouseOrganizing() {
        super();
    }

    @Override
    public void update() {

        Warehouse gameWarehouse = getWarehouse();

        tempWarehouse = new Warehouse();
        queuedResources = new Resources();

        tempWarehouse.setContent(gameWarehouse.getContent());
        tempWarehouse.setActivatedLeaders(gameWarehouse.getActivatedLeaders());
        tempWarehouse.setExtra(gameWarehouse.getExtra());
        queuedResources.add(getResourcesToPut().getRes());
    }

    @Override
    public void show() {
        println("Organize your warehouse:");
        ASCIIWarehouse.draw(tempWarehouse);
        if (queuedResources.getTotalAmount() > 0)
            ASCIIResources.draw(queuedResources);
        else println("No resources remaining");
        print("\n");
        println("+-------------------------------------------------+");
        println("| Choose resource type:                           |");
        println("| 1)" + ANSIColor.PURPLE + " SERVANTS\t" + ANSIColor.RESET +
                "2)" + ANSIColor.YELLOW + " COINS\t" + ANSIColor.RESET +
                "3)" + ANSIColor.BLUE + " SHIELDS\t" + ANSIColor.RESET +
                "4)" + ANSIColor.GREEN + " STONES" + ANSIColor.RESET + " |");
        println("| Choose location:                                |");
        println("|     warehouse cells     Leader extra spaces     |");
        println("|          [6]               (if available)       |");
        println("|        [4] [5]                [7] [8]           |");
        println("|      [1] [2] [3]              [9][10]           |");
        println("+-------------------------------------------------+");
    }

    @Override
    public void help() {
        println("Use command \"put <res> <cell>\" to put a resource in a warehouse cell.");
        /*println("Use command \"take <cell>\" to remove a resource from the specified cell.");*/
        println("User command \"reset\" to reset the warehouse and start over.");
        println("Use command \"confirm\" or \"ok\" to confirm and submit.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        int res, cell;
        switch (command) {
            case "help":
                help();
                break;
            case "put":
                if (arguments == null || arguments.length < 2) {
                    error("Missing arguments. Type \"help\" for further information.");
                    break;
                }
                try {
                    res = Integer.parseInt(arguments[0]);
                    cell = Integer.parseInt(arguments[1]);
                } catch (NumberFormatException ex) {
                    error(ex.getMessage());
                    break;
                }
                if (res < 1 || res > 4 || cell < 1 || cell > 10) {
                    error("Resource must be within 1 and 4, cell must be withing 1 and 10");
                    break;
                }
                tryPut(res, cell);
                break;
            case "reset":
                resetWH();
                show();
                break;
            case "confirm":
            case "ok":
                confirm();
                break;
            default:
                error("invalid command");
                break;
        }
    }

    /**
     * Retrieve the player's WH from the current GameData
     */
    private Warehouse getWarehouse() {
        return GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getWarehouse();
    }

    /**
     * Retrieve the ResourcesToPut from the temporary data
     */
    private ResourcesToPut getResourcesToPut() {
        return GameApplication.getInstance().getGameController().getGameData().getMomentary().getResourcesToPut();
    }

    /**
     * Try putting resources in the warehouse
     * @param res resources to put in the warehouse
     * @param cell cell of the warehouse to put the resources in
     */
    private void tryPut(int res, int cell) {
        int row = cell > 5 ? 2 : cell > 3 ? 1 : 0;
        boolean extra = cell > 6;
        int leader = cell > 8 ? 1 : 0;
        ResourceType rt = res == 1 ? ResourceType.SERVANTS : res == 2 ? ResourceType.COINS : res == 3 ? ResourceType.SHIELDS : ResourceType.STONES;
        if (!extra) {
            if (tempWarehouse.getContent()[row] == null) tempWarehouse.getContent()[row] = new Resources();

            if (rowEmpty(tempWarehouse.getContent()[row]) || rowContainsType(tempWarehouse.getContent()[row], rt)) {
                int rowAmount = tempWarehouse.getContent()[row].getTotalAmount();
                boolean rowFull = (row == 2 && rowAmount > 0) || (row == 1 && rowAmount > 1) || (row == 2 && rowAmount > 2);
                if (rowFull) {
                    error("That row is full. Remove the resources and try again.");
                    return;
                }
                try {
                    queuedResources.remove(rt, 1);
                    tempWarehouse.getContent()[row].add(rt, 1);
                    println("Successfully deposited resources");
                    show();
                } catch (ResourcesException ex) {
                    error(ex.getMessage());
                    return;
                }
            } else {
                error("You can't add that resource in the specified space.");
                return;
            }
        } else {
            if (tempWarehouse.getActivatedLeaders()[leader] == null) {
                error("This space is not available.");
                return;
            } else if (tempWarehouse.getActivatedLeaders()[leader].getAbility().getExtraWarehouseSpace().getAmountOf(rt) < 1) {
                error("This space is not reserved for that kind of resource.");
                return;
            } else if (tempWarehouse.getExtra()[leader].getTotalAmount() > 1) {
                error("The extra space granted from this leader card is full.");
                return;
            }
            try {
                if (tempWarehouse.getExtra()[leader] == null) tempWarehouse.getExtra()[leader] = new Resources();

                queuedResources.remove(rt, 1);
                tempWarehouse.getExtra()[leader].add(rt, 1);
                println("Successfully deposited resources");
                show();
            } catch (ResourcesException ex) {
                error(ex.getMessage());
                return;
            }
        }
    }

    /**
     * Empty the warehouse
     */
    private void resetWH() {
        for (Resources r : tempWarehouse.getContent()) {
            if (r != null)
                queuedResources.add(r);
        }
        for (Resources r : tempWarehouse.getExtra()) {
            if (r != null)
                queuedResources.add(r);
        }
        tempWarehouse = new Warehouse();
    }

    /**
     * Check if a row is empty
     */
    private boolean rowEmpty(Resources row) {
        return row.getTotalAmount() < 1;
    }

    /**
     * Check if a row contains a certain type of resources
     */
    private boolean rowContainsType(Resources row, ResourceType type) {
        return row.getAmountOf(type) > 0;
    }

    /**
     * Confirm the warehouse selection
     */
    private void confirm() {

        it.polimi.ingsw.model.playerboard.Warehouse wh = new it.polimi.ingsw.model.playerboard.Warehouse();
        wh.deposit(tempWarehouse.getContent()[0], 0);
        wh.deposit(tempWarehouse.getContent()[1], 1);
        wh.deposit(tempWarehouse.getContent()[2], 2);
        wh.deposit(tempWarehouse.getExtra()[0], 3);
        wh.deposit(tempWarehouse.getExtra()[1], 4);

        PutResourcesActionData prad = new PutResourcesActionData();
        prad.setWh(wh);
        prad.setPlayer(GameApplication.getInstance().getUserNickname());
        ActionPacket ap = new ActionPacket(Action.PUT_RESOURCES, JSONUtility.toJson(prad, PutResourcesActionData.class));

        CLIGame.pushAction(ap);
    }


}
