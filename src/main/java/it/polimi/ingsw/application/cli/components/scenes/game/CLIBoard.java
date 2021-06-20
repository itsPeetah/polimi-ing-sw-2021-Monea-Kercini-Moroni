package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.*;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.data.player.Strongbox;
import it.polimi.ingsw.view.data.player.Warehouse;

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
        println("Use \"buy <color> <tier>\" to buy a development card.");
        println("Use \"produce <indices>\" to activate your productions.");
        println("\tExample usage: \"produce 0 1 2 3\" to activate all production.");
        println("\tExample usage: \"produce 0\" to only activate the default production.");
        println("\tExample usage: \"produce 1 3\" to only activate the productions in the first and third stack.");
        println("Use \"activate <index>\" to activate a leader.");
        println("Use \"discard <index>\" to discard a leader.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        if(arguments == null) arguments = new String[0];

        switch (command){
            case "view":
                if(arguments.length<1){
                    error("Missing arguments.");
                } else onView(arguments);
                break;
            case "get":

                break;
            case "buy":

                break;
            case "produce":

                break;
            case "activate":

                break;
            case "discard":

                break;
        }
    }

    public void printDevCardMarket(){
        DevCardMarket dcm = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket();
        ASCIIDevCardMarket.draw(dcm);
    }

    public void printMarketTray(){
        MarketTray mt = GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray();
        ASCIIMarketTray.draw(mt);
    }

    private void printResources(String player){
        if(player == null) player = GameApplication.getInstance().getUserNickname();
        if(!GameApplication.getInstance().getGameController().getGameData().getPlayersList().contains(player)){
            error("The desired player does not exist.");
            return;
        }
        Warehouse wh = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getWarehouse();
        Strongbox sb = GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getStrongbox();
        ASCIIWarehouse.draw(wh);
        ASCIIStrongbox.draw(sb);
    }

    private void printProductions(String player){
        if(player == null) player = GameApplication.getInstance().getUserNickname();
        if(!GameApplication.getInstance().getGameController().getGameData().getPlayersList().contains(player)){
            error("The desired player does not exist.");
            return;
        }
        ASCIIDevelopment.draw(player);
    }

    private void printLeaders(String player){
        if(player == null) player = GameApplication.getInstance().getUserNickname();
        if(!GameApplication.getInstance().getGameController().getGameData().getPlayersList().contains(player)){
            error("The desired player does not exist.");
            return;
        }
        ASCIIPlayerLeaders.draw(player);
    }

    private void onView(String[] args){
        switch (args[0]) {
            case "mt":
                printMarketTray();
                break;
            case "dcm":
                printDevCardMarket();
                break;
            case "ft":
                ASCIIFaithPath.draw(GameApplication.getInstance().getGameController().getGameData());
                break;
            case "leaders":
                printLeaders(args.length < 2 ? args[1] : null);
                break;
            case "dev":
                printProductions(args.length < 2 ? args[1] : null);
                break;
            case "res":
                printResources(args.length < 2 ? args[1] : null);
                break;
            default:
                error("Invalid argument.");
                break;
        }
    }
}
