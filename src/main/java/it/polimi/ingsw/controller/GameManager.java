package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.network.server.metapackets.actions.*;
import it.polimi.ingsw.network.server.metapackets.actions.data.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.game.util.GameFactory;
import it.polimi.ingsw.model.general.*;
import it.polimi.ingsw.model.playerboard.*;

import java.util.ArrayList;
import java.util.Collections;


public class GameManager {

    private GamePhase gamePhase = GamePhase.PREGAME;

    // initialize game (with default settings)
    private Game game = GameFactory.CreateGame();

    public Game getGame() {
        return game;
    }


    //TODO RECHECK IF METHOD WILL BE USED LIKE THIS
    public void addPlayer(String nickname){
        try {
            game.addPlayer(nickname);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method for letting player choose a resource
     * @return the resource
     */
    private Resources askPlayerToChooseResource(Player p){

        ActionHandler.setExpectedAction(Action.CHOOSE_RESOURCE, p.getNickname());
        ChooseResourceActionData data = ActionHandler.getResponseData();
        Resources res = data.getResources();

        return res;
    }

    /**
     * Player has to put the resources in the correct Warehouse place
     * @param res resources player has to put
     * @param wh warehouse that will be updated
     */
    private void askPlayerToPutResources(Player p, Resources res, Warehouse wh){

        ActionHandler.setExpectedAction(Action.PUT_RESOURCES, p.getNickname());
        PutResourcesActionData data = ActionHandler.getResponseData();
        Warehouse updatedWarehouse = data.getWarehouse();

        //If player has less resources than he should have give other players extra faith point
        if( (wh.getResourcesAvailable().add(res)).isGreaterThan(updatedWarehouse.getResourcesAvailable()) ){

            int extraFP = (wh.getResourceAmountWarehouse()+ res.getTotalAmount()) - updatedWarehouse.getResourceAmountWarehouse();

            for (int i = 0; i< game.getPlayers().length; i++){
                //add to all players except the one who is playing
                if(game.getPlayers()[i] != p){
                    game.getPlayers()[i].getBoard().incrementFaithPoints(extraFP);
                }
            }

            wh = updatedWarehouse;

        }else{
            //Player has hacked game !!!!!!!!!!!!!!!!!
            //TODO punish player for trying to cheat
        }
    }

    /**
     * Setting up new game after all the players have joined
     */
    public void setupGame(){

        gamePhase = GamePhase.START;


        //Initialize leader cards
        ArrayList<LeadCard> leadCards = CardManager.loadLeadCardsFromJson();

        //shuffle leadCards
        Collections.shuffle(leadCards);

        //shuffle player order
        game.shufflePlayers();

        //Preparing stuff for player board
        ArrayList<Warehouse> wh = new ArrayList<>();
        ArrayList<Strongbox> sb = new ArrayList<>();
        ArrayList<ProductionPowers> pp = new ArrayList<>();
        ArrayList<PlayerBoard> pb = new ArrayList<>();

        //Getting player Leader choices and Extra resources depending on player order
        for (int i = 0; i< game.getPlayers().length; i++){

            //Set up player board
            wh.add(new Warehouse());
            sb.add(new Strongbox());
            pp.add(new ProductionPowers(3));
            pb.add(new PlayerBoard(3, wh.get(i), sb.get(i), pp.get(i)));

            game.getPlayers()[i].setBoard(pb.get(i)); //Assign Board to Player

            //The player has been offered 4 leader cards on the model side and is choosing 2
            ActionHandler.setExpectedAction(Action.CHOOSE_2_LEADERS, game.getPlayers()[i].getNickname());
            Choose2LeadersActionData data = ActionHandler.getResponseData();
            game.getPlayers()[i].getLeaders().setCards(data.getLeaders());

            if (i>=1){ //second player gets an extra resource

                Resources extra;
                extra = askPlayerToChooseResource(game.getPlayers()[i]);
                askPlayerToPutResources(game.getPlayers()[i], extra, game.getPlayers()[i].getBoard().getWarehouse());
            }
            if (i>=2){ //third player gets an extra faith in addition to the resource
                game.getPlayers()[i].getBoard().incrementFaithPoints(1);
            }
            if (i>=3){//fourth player gets all of the above and again an extra resource

                Resources extra2;
                extra2 = askPlayerToChooseResource(game.getPlayers()[i]);
                askPlayerToPutResources(game.getPlayers()[i], extra2, game.getPlayers()[i].getBoard().getWarehouse());
            }
        }
        startGame();
    }

    /**
     * The game starts
     * The game will now stay on turn phase until last round it's triggered
     * Then the players left will play their last turn (until it's the last players turn)
     * This is where the it will be kept track of the turn and will be triggered the end game
     */

    private void startGame(){

        //System.out.println("Game has started!");

        gamePhase = GamePhase.TURN;

        boolean lastRound = false;

        //Turns will keep being played until it's the last round and it's the last players turn
        while (!lastRound || !(game.getCurrentPlayer() == game.getPlayers()[game.getPlayers().length-1])){

            //System.out.println("Game has started!");

            playTurn(game.getCurrentPlayer());

            //These are the only two ways the game ends for now

            //if player has 7 devCards
            if(game.getCurrentPlayer().getBoard().getOwnedDevCards().size() == 7){
                lastRound = true;
            }

            //if player has reached the end of the faith track
            if(game.getCurrentPlayer().getBoard().getFaithPoints() >= 20){
                lastRound = true;
            }

            game.increaseTurnCounter();
        }
        endGame();
    }

    /**
     * This method represents a players turn
     * @param player the player
     */

    private void playTurn(Player player){

        boolean primaryActionUsed = false;
        boolean turnFinished = false;

        //TODO notify player it's his turn

        //Player may keep doing as many actions as he wants as long as he doesn't end his turn
        do {
            ActionHandler.setExpectedAction(Action.RESOURCE_MARKET, player.getNickname());
            ActionHandler.addExpectedAction(Action.DEV_CARD);
            ActionHandler.addExpectedAction(Action.PRODUCE);
            ActionHandler.addExpectedAction(Action.PlAY_LEADER);
            ActionHandler.addExpectedAction(Action.DISCARD_LEADER);
            ActionHandler.addExpectedAction(Action.REARRANGE_WAREHOUSE);
            ActionHandler.addExpectedAction(Action.END_TURN);

            switch (ActionHandler.getResponseAction()) {

                //Player has chosen to acquire resources from the Market tray
                case RESOURCE_MARKET:
                    primaryActionUsed = resourceMarket(player, primaryActionUsed);
                    break;

                //Player has chosen to buy a development card
                case DEV_CARD:
                    primaryActionUsed = devCardMarket(player, primaryActionUsed);
                    break;

                //Player has chosen to produce
                case PRODUCE:
                    primaryActionUsed = produce(player, primaryActionUsed);
                    break;

                //Player has chosen to play/discard leader
                //these are not primary actions and can be used more than once during his turn, whenever player wants
                case PlAY_LEADER:
                    playLeader(player);
                    break;

                case DISCARD_LEADER:
                    discardLeader(player);
                    break;

                //Player has chosen rearrange the resources he has in his warehouse
                //(this choice is practically useless since player can arrange his warehouse anytime he acquires resources from Market Tray)
                case REARRANGE_WAREHOUSE:
                    //Basically we ask the player to put all resources that he has in warehouse in his warehouse
                    askPlayerToPutResources(player, player.getBoard().getWarehouse().getResourcesAvailable(), player.getBoard().getWarehouse());
                    break;

                case END_TURN:
                    //Nothing - player just ends his turn
                    turnFinished = true;
                    break;
            }

        }while(!turnFinished);

    }

    /**
     * Calculate victory points for each player and show victory screens
     */

    public void endGame(){

        gamePhase = GamePhase.END;

        //Calculate VP for each player
        int[] VP = new int[game.getPlayers().length]; //An integer array for storing player VP so it can be more easily accessed

        for (int i = 0; i< game.getPlayers().length; i++) {

            VP[i] = game.getPlayers()[i].getVictoryPoints();

            //TODO show player his victory points
        }

        int Winner = getWinner(VP);

        //TODO notify player #Winner that he is the winner
        //Maybe add post-game functionality
    }

    /**
     * get the position of the winning player (the one with the highest number of points)
     * in case two players have the same number of points, the one with more resources win,
     * if these are also equal the game rules give no indication in who the winner is
     * @param array of victory points
     * @return position of the winning player
     */

    private int getWinner( int[] array ) {
        int winner = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[winner] ){
                winner = i;
            }
            //check if players have equal VP and and one has more resources
            if (array[i] == array[winner] && ( game.getPlayers()[i].getBoard().getResourcesAvailable().getTotalAmount() > game.getPlayers()[winner].getBoard().getResourcesAvailable().getTotalAmount()) ){
                winner = i;
            }
        }
        return winner; // position of the one wih most VP found
    }

    /**
     * Updates Market after players choice as well as his Warehouse
     * @param player
     * @param row -true if player has chosen to acquire from row -false if he acquires a column
     * @param index the row/column he has chosen
     * @return true if it executed the action with no problems
     */
    private boolean resourceMarketUpdate(Player player, boolean row, int index) {

        //boolean row = false; //false for column, true for row
        Resources res = new Resources();

        if (row) {
            try {
                res.add(game.getResourceMarket().pickRow(index));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                res.add(game.getResourceMarket().pickColumn(index));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Check if there is a red marble (gives faith)
        if (res.getAmountOf(ResourceType.FAITH) > 0) {
            //increase the faith points
            player.getBoard().incrementFaithPoints(res.getAmountOf(ResourceType.FAITH));
            //remove the faith from resources
            try {
                res.remove(ResourceType.FAITH, res.getAmountOf(ResourceType.FAITH));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Check if the player has any Lead Ability that transforms his white marbles

        //Count how many blank replacements we have (in the majority of the cases it will be 0 and almost never 2
        int whiteReplacements = 0;
        ArrayList<ResourceType> replaceTypes = new ArrayList<>();

        for (int i = 0; i < player.getLeaders().getPlayedCards().size(); i++) {
            //if player has a leader with the white marble replacement not blank
            if (player.getLeaders().getPlayedCards().get(i).getAbility().getWhiteMarbleReplacement() != ResourceType.BLANK) {
                whiteReplacements++;
                replaceTypes.add(player.getLeaders().getPlayedCards().get(i).getAbility().getWhiteMarbleReplacement());
            }
        }

        switch (whiteReplacements) {
            case 1:
                //automatically replace the white marble with the one granted from the leader ability
                res.replaceWhite(replaceTypes.get(0));
            case 2:
                //asking the player to choose one of the two resources he can to substitute white
                //TODO recheck how will the player be only offered the two resources he has the card (probably clients side)

                Resources choice = askPlayerToChooseResource(player);

                //Converting the choice from resources in resource type and adding it
                for (ResourceType type : ResourceType.values()) {
                    if(choice.getAmountOf(type) > 0){
                        res.replaceWhite(type);
                        break;
                    }
                }

                //Than just replace white with the player choice
            default:
                //replace nothing, but do remove the white
                try {
                    res.remove(ResourceType.BLANK, res.getAmountOf(ResourceType.BLANK));
                } catch (ResourcesException e) {
                    e.printStackTrace();
                }
        }

        askPlayerToPutResources(player, res, player.getBoard().getWarehouse());
        return true;
    }

    /**
     * Updates The development card market and player board
     * @param player
     * @param chosenCard The dev card the player has chosen
     * @param position The position (pile) the player wants to put the card he bought
     * @return true if it executed the action with no problems
     */
    private boolean devCardMarketUpdate(Player player, DevCard chosenCard, int position){

        //check if affordable
        if(!chosenCard.affordable(player)){
            //TODO Tell player he doesn't have enough resources
            return false;

            //check if it's possible to place that card there
        }else if (!player.getBoard().getProductionPowers().canDevCardBePlaced(chosenCard, position)){
            //TODO Tell player he can't put that card there
            return false;

        }else{
            player.getBoard().getProductionPowers().addDevCard(chosenCard, position);
            return true;
        }
    }

    /**
     * Updates the warehouse and strongbox with players choice
     * @param player
     * @param chosenProduction by the player
     * @return true if it executed the action with no problems
     */
    private boolean produceUpdate(Player player, Production chosenProduction){

        Resources fromStrongbox = new Resources(); // The resources that should be withdrawn from strongbox after the first withdrawal from warehouse has been done

        Resources input = makePlayerChoose(player, chosenProduction.getInput()); //If player has choice in input he has to choose here

        //check if affordable
        if( player.getBoard().getResourcesAvailable().isGreaterThan( input )){

            fromStrongbox.add(input);

            try {
                //Withdraw as many resources as you need from warehouse
                fromStrongbox.remove(player.getBoard().getWarehouse().withdraw(input));
            }catch (Exception e){
                e.printStackTrace();
            }

            //Withdraw the rest from strongbox
            player.getBoard().getStrongbox().withdraw(fromStrongbox);

            Resources output = makePlayerChoose(player, chosenProduction.getOutput()); //If player has choice in input he has to choose here

            //Add the output of production to the strongbox
            player.getBoard().getStrongbox().deposit(output);

            return true;
        }else{
            //TODO Tell player he doesn't have enough resources
            return false;
        }
    }

    /**
     * Plays the Leader Card the player has chosen
     * @param player
     * @param chosenLeader
     */
    private void playLeaderUpdate(Player player, LeadCard chosenLeader){

        if(chosenLeader.affordable(player)){
            chosenLeader.play(player);
        }else{
            //TODO remind player that he doesn't meet the requirements to play this card
        }
    }

    /**
     * Discard the Leader Card the player has chosen
     * @param player
     * @param chosenLeader
     */
    private void discardLeaderUpdate(Player player, LeadCard chosenLeader){

        chosenLeader.discard(player);
    }

    /**
     * Makes the player choose all the resources he wants from the input resources, if it has any choices
     * @param p player
     * @param r resource to check
     * @return The resource, but instead of choice it has been updated with the player choices
     */
    private Resources makePlayerChoose(Player p, Resources r){

        //ask player to choose resource until he has finished all choices
        while (r.getAmountOf(ResourceType.CHOICE)>0) {

            r.add(askPlayerToChooseResource(p));

            try {
                r.remove(ResourceType.CHOICE, 1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return r;
    }


    /**THESE ARE THE METHODS INSIDE PLAYTURN
     *
     * They call their respective Update methods
     * The only difference is they take care of the I/O - getting the player choice
     * and whether the primary action has been used
     */


    private boolean resourceMarket(Player player, boolean primaryActionUsed){

        //ActionHandler.setExpectedAction(Action.RESOURCE_MARKET, player.getNickname());
        ResourceMarketActionData playerChoice = ActionHandler.getResponseData();

        //Do this action only if the player has not used his primary action
        if(!primaryActionUsed){
            primaryActionUsed = resourceMarketUpdate(player, playerChoice.isRow(), playerChoice.getIndex());
        }else{
            //TODO notify player he has already used his primary action
        }

        return primaryActionUsed;
    }

    private boolean devCardMarket(Player player, boolean primaryActionUsed){

        //ActionHandler.setExpectedAction(Action.DEV_CARD, player.getNickname());
        DevCardActionData devCardChoice = ActionHandler.getResponseData();

        //Do this action only if the player has not used his primary action
        if(!primaryActionUsed){
            primaryActionUsed = devCardMarketUpdate(player, devCardChoice.getChooenCard(), devCardChoice.getPosition());
        }else{
            //TODO notify player he has already used his primary action
        }

        return primaryActionUsed;
    }

    private boolean produce(Player player, boolean primaryActionUsed){

        //ActionHandler.setExpectedAction(Action.PRODUCE, player.getNickname());
        ProduceActionData produceChoice = ActionHandler.getResponseData();

        //Do this action only if the player has not used his primary action
        if(!primaryActionUsed){
            primaryActionUsed = produceUpdate(player, produceChoice.getChosenProd());
        }else{
            //TODO notify player he has already used his primary action
        }

        return primaryActionUsed;
    }

    private void playLeader(Player player){

        //ActionHandler.setExpectedAction(Action.PlAY_LEADER, player.getNickname());
        ChooseLeaderActionData playLeaderEventData = ActionHandler.getResponseData();

        playLeaderUpdate(player, playLeaderEventData.getChosenLeader());
    }

    private void discardLeader(Player player){

        //ActionHandler.setExpectedAction(Action.DISCARD_LEADER, player.getNickname());
        ChooseLeaderActionData discardLeaderEventData = ActionHandler.getResponseData();

        discardLeaderUpdate(player, discardLeaderEventData.getChosenLeader());
    }

}
