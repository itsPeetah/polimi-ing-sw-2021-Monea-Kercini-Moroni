package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.*;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.CLIGame;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.DevCardActionData;
import it.polimi.ingsw.controller.model.actions.data.NoneActionData;
import it.polimi.ingsw.controller.model.actions.data.ProduceActionData;
import it.polimi.ingsw.controller.model.actions.data.ResourceMarketActionData;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.playerboard.ProductionPowers;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.data.player.DevCards;
import it.polimi.ingsw.view.data.player.Strongbox;
import it.polimi.ingsw.view.data.player.Warehouse;

import javax.swing.*;
import java.util.ArrayList;

public class CLIBoard extends CLIScene implements CLIGameSubScene {

    @Override
    public void update(GameData data) {

    }

    @Override
    public void help() {
        println("Visualization:");
        println("Use \"view <'mt'|'dcm'|'ft'>\" to visualize the Market Tray, the Development Card Market or the Fait Track.");
        println("Use \"view dev [<player>]\" to visualize someone's development cards (omitting the player will show yours).");
        println("Use \"view res [<player>]\" to visualize someone's warehouse and strongbox resources (omitting the player will show yours).");
        println("Use \"view leaders [<player>]\" to visualize someone's leaders (omitting the player will show yours) (you won't be able to see non-activated leaders from other players).");
        println("Actions:");
        println("Use \"get <'row'|'col'> <index>\" to acquire resources from the market tray.");
        println("Use \"buy <color> <tier> <stack_index>\" to buy a development card.");
        println("\tColors: b (blue), g (green), p (purple), y (yellow), tiers: 1, 2, 3, stack_indices: 1, 2, 3");
        println("Use \"produce <indices>\" to activate your productions.");
        println("\tExample usage: \"produce 0 1 2 3\" to activate all production.");
        println("\tExample usage: \"produce 0\" to only activate the default production.");
        println("\tExample usage: \"produce 1 3\" to only activate the productions in the first and third stack.");
        println("Use \"activate <index>\" to activate a leader.");
        println("Use \"discard <index>\" to discard a leader.");
        println("User \"endturn\" to end your turn.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        if ((arguments == null || arguments.length < 1) && ( !"end endturn help".contains(command))) {
            error("Missing arguments.");
        }

        switch (command) {
            case "view":
                onView(arguments);
                break;
            case "get":
                getResources(arguments);
                break;
            case "buy":
                onBuy(arguments);
                break;
            case "produce":
                onProduce(arguments);
                break;
            case "activate":

                break;
            case "discard":

                break;
            case "end":
            case "endturn":
                onEndTurn();
                break;
            case "help":
                help();
                break;
            default:
                error("Invalid command");
                break;
        }
    }

    public void printDevCardMarket() {
        DevCardMarket dcm = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket();
        ASCIIDevCardMarket.draw(dcm);
    }

    public void printMarketTray() {
        MarketTray mt = GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray();
        ASCIIMarketTray.draw(mt);
    }

    private void printResources(String player) {
        if (player == null) player = GameApplication.getInstance().getUserNickname();
        if (!GameApplication.getInstance().getGameController().getGameData().getPlayersList().contains(player)) {
            error("The desired player does not exist.");
            return;
        }
        Warehouse wh = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getWarehouse();
        Strongbox sb = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getStrongbox();
        ASCIIWarehouse.draw(wh);
        ASCIIStrongbox.draw(sb);
    }

    private void printProductions(String player) {
        if (player == null) player = GameApplication.getInstance().getUserNickname();
        if (!GameApplication.getInstance().getGameController().getGameData().getPlayersList().contains(player)) {
            error("The desired player does not exist.");
            return;
        }
        ASCIIDevelopment.draw(player);
    }

    private void printLeaders(String player) {
        if (player == null) player = GameApplication.getInstance().getUserNickname();
        if (!GameApplication.getInstance().getGameController().getGameData().getPlayersList().contains(player)) {
            error("The desired player does not exist.");
            return;
        }
        ASCIIPlayerLeaders.draw(player);
    }

    private void onView(String[] args) {
        switch (args[0]) {
            case "mt":
                printMarketTray();
                break;
            case "dcm":
                printDevCardMarket();
                break;
            case "ft":
                ASCIIFaithPath.draw(GameApplication.getInstance().getGameController().getGameData(),
                        GameApplication.getInstance().getGameController().isSinglePlayer());
                break;
            case "leaders":
                printLeaders(args.length < 2 ? null : args[1]);
                break;
            case "dev":
                printProductions(args.length < 2 ? null : args[1]);
                break;
            case "res":
                printResources(args.length < 2 ? null : args[1]);
                break;
            default:
                error("Invalid argument.");
                break;
        }
    }

    private void getResources(String[] args) {
        // Missing args
        if (args.length < 2) {
            error("Missing arguments");
            return;
        }
        // Get index
        int index = 0;
        try {
            index = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            error("Could not parse the index.");
            return;
        }
        // Validate index
        if (index < 1 || index > 4) {
            error("Index should be within 1-4");
            return;
        }
        boolean row;
        switch (args[0]) {
            case "row":
                // Row index
                if (index > 3) {
                    error("Row index should be within 1-3");
                    return;
                }
                row = true;
                break;
            case "col":
                row = false;
                break;
            default:
                error("Invalid argument, use either 'row' or 'col'.");
                return;
        }

        ResourceMarketActionData rmad = new ResourceMarketActionData(row, index - 1);
        rmad.setPlayer(GameApplication.getInstance().getUserNickname());
        ActionPacket ap = new ActionPacket(Action.RESOURCE_MARKET, JSONUtility.toJson(rmad, ResourceMarketActionData.class));
        CLIGame.pushAction(ap);
    }

    private void onBuy(String[] args){
        if(args.length < 3){
            error("Missing arguments.");
            return;
        }
        if(!"bgpy".contains(args[0]) && !"blue green purple yellow".contains(args[0])){
            error("Please specify a correct dev card color.");
            return;
        }
        if(!"123".contains(args[1])){
            error("Please specify a correct dev card tier.");
            return;
        }
        if(!"123".contains(args[2])){
            error("Please specify a correct production stack number.");
            return;
        }

        int tier = Integer.parseInt(args[1]) - 1;
        int stack = Integer.parseInt(args[2]);
        int color = -1;
        switch (args[0]){
            case "b":
            case "blue":
                color = 0;
                break;
            case "p":
            case "purple":
                color = 1;
                break;
            case "g":
            case "green":
                color = 2;
                break;
            case "y":
            case "yellow":
                color = 3;
                break;
        }

        DevCardMarket dcm = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket();
        try{
            DevCard chosen = dcm.getAvailableCards()[color][tier];

            DevCardActionData dcad = new DevCardActionData(chosen, stack - 1);
            dcad.setPlayer(GameApplication.getInstance().getUserNickname());
            ActionPacket ap = new ActionPacket(Action.DEV_CARD, JSONUtility.toJson(dcad, DevCardActionData.class));

            CLIGame.pushAction(ap);
        } catch (NullPointerException ex){
            error("The card is not available.");
            return;
        }

    }

    private void onProduce(String[] args){
        int[] prods = new int[args.length];

        for(int i = 0; i < args.length; i++){
            try{
                prods[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException ex){
                error("Invalid arguments. Please insert only numbers between 0 and 3.");
                return;
            }
        }

        DevCard[] dcs = GameApplication.getInstance().getGameController().getGameData()
                .getPlayerData(GameApplication.getInstance().getUserNickname()).getDevCards().getDevCards();

        ArrayList<Production> chosenProductions = new ArrayList<Production>();

        for(int p : prods){
            if(p > 0){
                if(dcs[p-1] == null){
                    error("That production is not available at the moment.");
                    return;
                } else {
                    chosenProductions.add(dcs[p-1].getProduction());
                    println("Selected production in stack #" + p);
                }
            }
            else{
                chosenProductions.add(ProductionPowers.getBasicProduction());
                println("Selected default production.");
            }
        }

        ProduceActionData pad = new ProduceActionData(chosenProductions);
        pad.setPlayer(GameApplication.getInstance().getUserNickname());
        ActionPacket ap = new ActionPacket(Action.PRODUCE, JSONUtility.toJson(pad, ProduceActionData.class));

        CLIGame.pushAction(ap);
    }

    private void onEndTurn(){
        NoneActionData nad = new NoneActionData();
        nad.setPlayer(GameApplication.getInstance().getUserNickname());
        ActionPacket ap = new ActionPacket(Action.END_TURN, JSONUtility.toJson(nad, NoneActionData.class));

        CLIGame.pushAction(ap);
    }
}
