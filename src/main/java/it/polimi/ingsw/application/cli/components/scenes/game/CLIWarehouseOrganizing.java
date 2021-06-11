package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIResources;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIResourcesToPut;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIWarehouse;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.PutResourcesActionData;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.general.ResourcesException;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;
import it.polimi.ingsw.view.data.player.Warehouse;

import java.util.Optional;

public class CLIWarehouseOrganizing extends CLIScene implements CLIGameSubScene {

    private Warehouse tempWarehouse;
    private Resources queuedResources;
    private Resources leaderResources;

    public CLIWarehouseOrganizing(){
        super();
    }

    @Override
    public void update(GameData data) {

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
        ASCIIResources.draw(queuedResources);
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
        println("Use command \"take <cell>\" to remove a resource from the specified cell.");
        println("Use command \"confirm\" or \"ok\" to confirm and submit.");
    }

    @Override
    public void execute(String command, String[] arguments) {
                int res, cell;
        switch (command){
            case "help": help(); break;
            case "put":
                if(arguments == null || arguments.length < 2) {
                    error("Missing arguments. Type \"help\" for further information.");
                    break;
                }
                try{
                    res = Integer.parseInt(arguments[0]);
                    cell = Integer.parseInt(arguments[1]);
                } catch(NumberFormatException ex){
                    error(ex.getMessage());
                    break;
                }
                if(res < 1 || res > 4 || cell < 1 || cell > 10){
                    error("Resource must be within 1 and 4, cell must be withing 1 and 10");
                    break;
                }
                tryPut(res, cell);
                break;
            case "take":
                if(arguments.length < 1) {
                    error("Missing argument. Type \"help\" for further information.");
                    break;
                }
                try{
                    cell = Integer.parseInt(arguments[1]);
                } catch(NumberFormatException ex){
                    error("NumberFormatException: " + ex.getMessage());
                    break;
                }
                if(cell < 1 || cell > 6){
                    error("Cell must be withing 1 and 6");
                    break;
                }
                tryTake(cell);
                break;
            case "confirm":
            case "ok":
                confirm();
                break;
        }
    }

    private Warehouse getWarehouse(){
        return GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getWarehouse();
    }

    private ResourcesToPut getResourcesToPut(){
        return GameApplication.getInstance().getGameController().getGameData().getMomentary().getResourcesToPut();
    }

    private void tryPut(int res, int cell){
        int row = cell > 5 ? 2 : cell > 3 ? 1 : 0;
        boolean extra = cell > 6;
        int leader = cell > 8 ? 1 : 0;
        ResourceType rt = res == 1 ? ResourceType.SERVANTS : res == 2 ? ResourceType.COINS : res == 3 ? ResourceType.SHIELDS : ResourceType.STONES;
        if(!extra){
            if(tempWarehouse.getContent()[row] == null) tempWarehouse.getContent()[row] = new Resources();

            if(rowEmpty(tempWarehouse.getContent()[row]) || rowContainsType(tempWarehouse.getContent()[row], rt)){
                int rowAmount = tempWarehouse.getContent()[row].getTotalAmount();
                boolean rowFull = (row == 2 && rowAmount > 0) || (row == 1 && rowAmount > 1) || (row == 2 && rowAmount > 2);
                if(rowFull){
                    error("That row is full. Remove the resources and try again.");
                    return;
                }
                try{
                    queuedResources.remove(rt, 1);
                    tempWarehouse.getContent()[row].add(rt, 1);
                    println("Successfully deposited resources");
                    show();
                } catch (ResourcesException ex){
                    error(ex.getMessage());
                    return;
                }
            } else {
                error("You can't add that resource in the specified space.");
                return;
            }
        } else {
            if(tempWarehouse.getActivatedLeaders()[leader] == null){
                error("This space is not available.");
                return;
            } else if(tempWarehouse.getActivatedLeaders()[leader].getAbility().getExtraWarehouseSpace().getAmountOf(rt) < 1){
                error("This space is not reserved for that kind of resource.");
                return;
            } else if(tempWarehouse.getExtra()[leader].getTotalAmount() > 1){
                error("The extra space granted from this leader card is full.");
                return;
            } try{
                if (tempWarehouse.getExtra()[leader] == null) tempWarehouse.getExtra()[leader] = new Resources();

                queuedResources.remove(rt, 1);
                tempWarehouse.getExtra()[leader].add(rt, 1);
                println("Successfully deposited resources");
                show();
            } catch(ResourcesException ex){
                error(ex.getMessage());
                return;
            }
        }
    }

    private void tryTake(int cell){
        int row = cell > 5 ? 2 : cell > 3 ? 1 : 0;
        boolean extra = cell > 6;
        int leader = cell > 8 ? 1 : 0;
        if(!extra){
            if(tempWarehouse.getContent()[row] == null) tempWarehouse.getContent()[row] = new Resources();

            if(!rowEmpty(tempWarehouse.getContent()[row])){
                int rowAmount = tempWarehouse.getContent()[row].getTotalAmount();
                boolean rowFull = (row == 2 && rowAmount > 0) || (row == 1 && rowAmount > 1) || (row == 2 && rowAmount > 2);
                if(rowFull){
                    error("That row is full. Remove the resources and try again.");
                    return;
                }
               /* try{
                    queuedResources.remove(rt, 1);
                    tempWarehouse.getContent()[row].add(rt, 1);
                    println("Successfully deposited resources");
                    show();
                } catch (ResourcesException ex){
                    error(ex.getMessage());
                    return;
                }*/
            } else {
                error("That space is already empty.");
                return;
            }
        } else {
            if(tempWarehouse.getActivatedLeaders()[leader] == null) {
                error("This space is not available.");
                return;
            } else if(tempWarehouse.getExtra()[leader].getTotalAmount() < 1){
                error("The extra space granted from this leader card is empty.");
                return;
            } /*try{
                if (tempWarehouse.getExtra()[leader] == null) tempWarehouse.getExtra()[leader] = new Resources();
                // TODO Fix
                queuedResources.remove(rt, 1);
                tempWarehouse.getExtra()[leader].add(rt, 1);
                println("Successfully deposited resources");
                show();
            } catch(ResourcesException ex){
                error(ex.getMessage());
                return;
            }*/
        }
    }

    private boolean rowEmpty(Resources row) {
        return row.getTotalAmount() < 1;
    }
    private boolean rowContainsType(Resources row, ResourceType type){
        return row.getAmountOf(type) > 0;
    }


    private void confirm(){
        /*it.polimi.ingsw.model.playerboard.Warehouse sentWarehouse = new it.polimi.ingsw.model.playerboard.Warehouse();
        // Create warehouse to send
        for(int i=0; i<3; i++) {
            Resources floorResources = new Resources();
            int resCount = (int)rows.get(2-i).stream().filter(imageView -> imageView.getImage() != null).count();
            ResourceType resType = rowsResourceTypes.get(2-i);
            if(resCount > 0) {
                floorResources.add(resType, resCount);
            }
            sentWarehouse.deposit(floorResources,i);
        }
        // Create leader extra to send
        for(int i=0; i<2; i++) {
            LeadCard activatedLeader = leadCards[i];
            if(activatedLeader != null) {
                Optional<LeadCard> searchedCard = playerLeaders.stream().filter(leadCard -> leadCard != null && leadCard.getCardId().equals(activatedLeader.getCardId())).findFirst();
                assert searchedCard.isPresent();
                int leadIndex = playerLeaders.indexOf(searchedCard.get());
                int resCount = (int)leadersResources.get(leadIndex).stream().filter(imageView -> imageView.getImage() != null).count();
                ResourceType resType = leadersResourceTypes.get(leadIndex);
                System.out.println("GUIWarehouse.onConfirmClick: " + resCount + " of " + resType);
                Resources extraResources = new Resources();
                extraResources.add(resType, resCount);
                sentWarehouse.deposit(extraResources,i+3);
                sentWarehouse.expandWithLeader(activatedLeader);
            }
        }*/

        Action a = Action.PUT_RESOURCES;
        PutResourcesActionData ad = new PutResourcesActionData();
        ad.setPlayer(GameApplication.getInstance().getUserNickname());
        ad.setWh(new it.polimi.ingsw.model.playerboard.Warehouse());
        GameApplication.getInstance().sendNetworkPacket(NetworkPacket.buildActionPacket(new ActionPacket(a, JSONUtility.toJson(ad, PutResourcesActionData.class))));
    }



}
