package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.events.Action;
import it.polimi.ingsw.model.events.EventHandler;
import it.polimi.ingsw.model.events.data.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.game.util.GameFactory;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.general.ResourcesException;
import it.polimi.ingsw.model.playerboard.*;


import com.google.gson.Gson;

import java.io.*;


import java.util.ArrayList;
import java.util.Collections;


public class GameManager {

    private GamePhase gamePhase = GamePhase.PREGAME;
    private Game game;

    public Game getGame() {
        return game;
    }


    //TODO RECHECK IF METHOD WILL BE USED LIKE THIS
    public void addPlayer(String nickname){
        try {
            game.addPlayer(nickname);
        }catch (Exception e){
            System.out.println("Player limit reached!");
        }
    }

    /**
     * Method for letting player choose a resource
     * @return the resources
     */
    private Resources askPlayerToChooseResource(Player p){

        EventHandler.makeRequest(Action.CHOOSE_RESOURCE, p.getNickname());
        ChooseResourceEventData data = EventHandler.getResponse();
        Resources res = data.getResources();

        return res;
    }

    /**
     * Player has to put the resources in the correct Warehouse place
     * @param res player has to put
     * @param wh warehouse that will be updated
     */
    private void askPlayerToPutResources(Player p, Resources res, Warehouse wh){
        EventHandler.makeRequest(Action.PUT_RESOURCES, p.getNickname());
        PutResourcesEventData data = EventHandler.getResponse();
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

        /*

        // Setting up market tray

        ArrayList<ResourceMarble> marbles = new ArrayList<>();

        ResourceMarble marble1 = new ResourceMarble(ResourceType.FAITH, 1);
        ResourceMarble marble2 = new ResourceMarble(ResourceType.STONES, 1);
        ResourceMarble marble3 = new ResourceMarble(ResourceType.STONES, 1);
        ResourceMarble marble4 = new ResourceMarble(ResourceType.COINS, 1);
        ResourceMarble marble5 = new ResourceMarble(ResourceType.COINS, 1);
        ResourceMarble marble6 = new ResourceMarble(ResourceType.SHIELDS, 1);
        ResourceMarble marble7 = new ResourceMarble(ResourceType.SHIELDS, 1);
        ResourceMarble marble8 = new ResourceMarble(ResourceType.SERVANTS, 1);
        ResourceMarble marble9 = new ResourceMarble(ResourceType.SERVANTS, 1);
        ResourceMarble marble10 = new ResourceMarble(ResourceType.BLANK, 1);
        ResourceMarble marble11 = new ResourceMarble(ResourceType.BLANK, 1);
        ResourceMarble marble12 = new ResourceMarble(ResourceType.BLANK, 1);
        ResourceMarble marble13 = new ResourceMarble(ResourceType.BLANK, 1);
        marbles.add(marble1);
        marbles.add(marble2);
        marbles.add(marble3);
        marbles.add(marble4);
        marbles.add(marble5);
        marbles.add(marble6);
        marbles.add(marble7);
        marbles.add(marble8);
        marbles.add(marble9);
        marbles.add(marble10);
        marbles.add(marble11);
        marbles.add(marble12);
        marbles.add(marble13);

        MarketTray MT = null;
        try {
            MT = new MarketTray(3, 4, marbles);
        }catch (Exception e){
            e.printStackTrace();
        }


        //setting up dev card market



        ArrayList<DevCard> devCards = null;
        try (Reader reader = new FileReader("src/main/resources/devcards.json")) {

            // Convert JSON File to Java Object
            devCards = gson.fromJson(reader, ArrayList.class);
            // print staff object
            //System.out.println(devCards);

        } catch (IOException e) {
            e.printStackTrace();
        }

        DevCardMarket DMC = new DevCardMarket(devCards);

        */

        // initialize game (with default settings)
        game = GameFactory.CreateGame();


        //Initialize leader cards
        Gson gson = new Gson();
        ArrayList<LeadCard> leadCards = null;
        try (Reader reader = new FileReader("src/main/resources/leadcards.json")) {

            // Convert JSON File to Java Object
            leadCards = gson.fromJson(reader, ArrayList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //shuffle leadCards
        Collections.shuffle(leadCards);

        //shuffle player order
        game.shufflePlayers();

        //Preparing stuff for player board
        ArrayList<Warehouse> wh = new ArrayList<>();
        ArrayList<Strongbox> sb = new ArrayList<>();
        ArrayList<ProductionPowers> pp = new ArrayList<>();
        ArrayList<PlayerBoard> pb = new ArrayList<>();


        //Getting player Leader choices and Extra resources
        for (int i = 0; i< game.getPlayers().length; i++){

            //Set up player board
            wh.add(new Warehouse());
            sb.add(new Strongbox());
            pp.add(new ProductionPowers(3));
            pb.add(new PlayerBoard(3, wh.get(i), sb.get(i), pp.get(i)));

            game.getPlayers()[i].setBoard(pb.get(i)); //Assign Board to Player


            //The player has been offered 4 leader cards on the model side and is choosing 2
            EventHandler.makeRequest(Action.CHOOSE_2_LEADERS, game.getPlayers()[i].getNickname());
            Choose2LeadersEventData data = EventHandler.getResponse();
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
     */

    private void startGame(){

        gamePhase = GamePhase.TURN;

        boolean lastRound = false;

        //Turns will keep being played until it's the last round and it's the last players turn
        while (!lastRound && !(game.getCurrentPlayer() == game.getPlayers()[game.getPlayers().length-1])){

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

        //TODO notify player it's his turn

        TurnChoice choice = null;

        //Player may keep doing as many actions as he wants as long as he doesn't end his turn
        do {

            EventHandler.makeRequest(Action.CHOOSE_ACTION, player.getNickname());
            ChooseActionEventData data = EventHandler.getResponse();

            switch (data.getChoice()) {
                case RESOURCEMARKET:

                    EventHandler.makeRequest(Action.RESOURCE_MARKET, player.getNickname());
                    ResourceMarketEventData playerChoice = EventHandler.getResponse();

                    //Do this action only if the player has not used his primary action
                    if(!primaryActionUsed){
                        primaryActionUsed = resourceMarketUpdate(player, playerChoice.isRow(), playerChoice.getIndex());
                    }else{
                        //TODO notify player he has already used his primary action
                    }

                case DEVCARDMARKET:

                    EventHandler.makeRequest(Action.RESOURCE_MARKET, player.getNickname());
                    DevCardEventData devCardChoice = EventHandler.getResponse();

                    //Do this action only if the player has not used his primary action
                    if(!primaryActionUsed){
                        primaryActionUsed = devCardMarketUpdate(player, devCardChoice.getChooenCard(), devCardChoice.getPosition());
                    }else{
                        //TODO notify player he has already used his primary action
                    }

                case REARRANGEWAREHOUSE:
                    //Basically we ask the player to put all resources that he has in warehouse in his warehouse
                    askPlayerToPutResources(player, game.getCurrentPlayer().getBoard().getWarehouse().getResourcesAvailable(), game.getCurrentPlayer().getBoard().getWarehouse());

                case ENDTURN:
                    //Nothing player just ends his turn
            }

        }while(choice != TurnChoice.ENDTURN);

    }

    public void endGame(){

        gamePhase = GamePhase.END;

        //Calculate VP for each player
        int[] VP = new int[game.getPlayers().length]; //An integer array for storing player VP so it can be more accessible

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
            if ( array[i] > array[winner] ) winner = i;

            //check if players have equal VP and and one has more resources
            if (array[i] == array[winner] && ( game.getPlayers()[i].getBoard().getResourcesAvailable().getTotalAmount() > game.getPlayers()[winner].getBoard().getResourcesAvailable().getTotalAmount()) ){

            }
        }
        return winner; // position of the first largest found
    }


    /**
     *
     * @param player
     * @param row
     * @param index
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
                //TODO recheck how will the player be only offered the two resources he has the card

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

    private void produceUpdate(Player player, Production chosenProduction){
        Resources fromStrongbox = new Resources(); // The resources that should be withdrawn from strongbox after the first withdrawal from warehouse has been done
        //TODO player chooses production

        //check if affordable
        if( player.getBoard().getResourcesAvailable().isGreaterThan( chosenProduction.getInput() )){

            fromStrongbox.add(chosenProduction.getInput());

            try {
                //Withdraw as many resources as you need from warehouse
                fromStrongbox.remove(player.getBoard().getWarehouse().withdraw(chosenProduction.getInput()));
            }catch (Exception e){
                e.printStackTrace();
            }
            //Withdraw the rest from strongbox
            player.getBoard().getStrongbox().withdraw(fromStrongbox);

            //Add the output of production to the strongbox
            player.getBoard().getStrongbox().deposit(chosenProduction.getOutput());

        }else{
            //TODO Tell player he doesn't have enough resources
        }
    }

    private void playLeaderUpdate(Player player, LeadCard chosenLeader){

        if(chosenLeader.affordable(player)){
            chosenLeader.play(player);
        }else{
            //TODO remind player that he doesn't meet the requirements to play this card
        }
    }

    private void discardLeaderUpdate(Player player, LeadCard chosenLeader){

        chosenLeader.discard(player);
    }

}
