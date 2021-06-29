package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIDevCardMarket;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIILeadCard;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIMarketTray;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.CLIGame;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.Choose2LeadersActionData;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.data.momentary.LeadersToChooseFrom;

/**
 * CLIScene for choosing
 */
public class CLILeadChoice extends CLIScene {

    public CLILeadChoice()
    {
        super();
    }

    @Override
    public void show() {
        clearConsole();
        println("");
        new Thread(() -> {
            try {
                Thread.sleep(500);
                println("CHOOSE YOUR LEADER CARDS:");
                for (LeadCard lc : getLeadersToChooseFrom().getLeaders()) {
                    ASCIILeadCard.draw(lc);
                }
            } catch (Exception ex) {
                println(ANSIColor.YELLOW + "Sorry, something went wrong while displaying the leaders automatically.");
                println("Please, type \"leaders\" to visualize the cards you can choose from." + ANSIColor.RESET);
            }
        }).start();
    }

    @Override
    public void help() {
        println("Use \"pick <1|2|3|4> <1|2|3|4>\" to choose two leaders");
        println("Use \"view <mt|dcm>\" to visualize Market Tray and Dev Card Market");
        println("Use \"leaders\" to view you leaders to choose from again");
    }

    @Override
    public void execute(String command, String[] arguments) {

        if (command == null) {
            error("Could not parse the command.");
            return;
        }

        switch (command) {
            case "pick":
                if (arguments == null || arguments.length < 2) error("Error: missing arguments.");
                else {
                    int arg1, arg2;
                    try {
                        arg1 = Integer.parseInt(arguments[0]);
                        arg2 = Integer.parseInt(arguments[1]);
                    } catch (NumberFormatException ex){
                        error("Please insert two numbers between 1 and 4.");
                        return;
                    }
                    if (arg1 == arg2) error("Please insert two different numbers.");
                    else if(arg1 < 1 || arg1 > 4 || arg2 <1 || arg2 > 4) error("Please insert two numbers between 1 and 4.");
                    else chooseLeaders(arg1 - 1, arg2 - 1);
                }
                break;
            case "view":
                if (arguments.length < 1) error("Missing argument (mt|dcm)");
                else if (arguments[0].equals("mt")) ASCIIMarketTray.draw(getMarketTray());
                else if (arguments[0].equals("dcm")) ASCIIDevCardMarket.draw(getDevCardMarket());
                else error("Invalid argument. Usage: \"view\" followed by either \"mt\" or \"dcm\"");
                break;
            case "help":
                help();
                break;
            case "lead":
            case "leaders":
                show();
                break;
            default:
                error("Invalid command.");
                break;
        }
    }

    /**
     * Procedure to choose leader cards
     */
    private void chooseLeaders(int index1, int index2){

        LeadCard[] chosen = new LeadCard[]{
                getLeadersToChooseFrom().getLeaders().get(index1),
                getLeadersToChooseFrom().getLeaders().get(index2)
        };

        Choose2LeadersActionData actionData = new Choose2LeadersActionData();
        actionData.setLeaders(chosen);
        actionData.setPlayer(GameApplication.getInstance().getUserNickname());

        ActionPacket ap = new ActionPacket(Action.CHOOSE_2_LEADERS, JSONUtility.toJson(actionData, Choose2LeadersActionData.class));
        /*GameApplicationIOHandler.getInstance().pushAction(ap);*/
        CLIGame.pushAction(ap);
    }

    /**
     * Retrieve the DCM from the GameData
     */
    public DevCardMarket getDevCardMarket(){
        return GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket();
    }

    /**
     * Retrieve the MT from the GameData
     * @return
     */
    public MarketTray getMarketTray(){
        return GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray();
    }

    /**
     * Get the LTCF from the GameData
     * @return
     */
    public LeadersToChooseFrom getLeadersToChooseFrom(){
        return GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom();
    }

}
