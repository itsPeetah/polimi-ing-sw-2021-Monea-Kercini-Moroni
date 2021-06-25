package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIDevCardMarket;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIILeadCard;
import it.polimi.ingsw.application.cli.components.ASCIIElements.ASCIIMarketTray;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.CLIGame;
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
        ASCIIMarketTray.draw(getMarketTray());
        ASCIIDevCardMarket.draw(getDevCardMarket());
        println("CHOOSE YOUR LEADER CARDS:"); println("");
        for(LeadCard lc : getLeadersToChooseFrom().getLeaders()){
            ASCIILeadCard.draw(lc);
        }
    }

    @Override
    public void execute(String command, String[] arguments) {

        if (command == null) error("Could not parse the command.");
        else if (!command.equals("pick")) error("Invalid command.");
        else if(command.length() < 3) error("Error: missing arguments.");
        else if(arguments == null || arguments.length < 2) error("Error: missing arguments.");
        else{
            int arg1 = Integer.parseInt(arguments[0]); int arg2 = Integer.parseInt(arguments[1]);
            if(arg1 == arg2) error("Please insert two different numbers.");
            if(arg1 < 1 || arg1 > 4 || arg2 < 1 || arg2 > 4) error("Please insert two integers between 1 and 4.");
            else chooseLeaders(arg1 - 1, arg2 - 1);
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
