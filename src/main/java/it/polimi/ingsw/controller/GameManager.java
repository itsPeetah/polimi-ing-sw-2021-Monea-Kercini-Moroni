package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.ProductionPowers;
import it.polimi.ingsw.model.playerboard.Strongbox;
import it.polimi.ingsw.model.playerboard.Warehouse;

import com.google.gson.Gson;

import java.io.*;


import java.util.ArrayList;
import java.util.Collections;


public class GameManager {

    private GamePhase gamePhase = GamePhase.PREGAME;
    private TurnChoice turnChoice;
    private Game game;

    public Game getGame() {
        return game;
    }


    //RECHECK IF METHOD WILL BE USED LIKE THIS
    public void addPlayer(String nickname){
        try {
            game.addPlayer(nickname);
        }catch (Exception e){
            System.out.println("Player limit reached!");
        }
    }

    /**
     * Method for letting player choose a resource
     * @param quantity number of resources player has to choose
     * @return the resources
     */

    private Resources askPlayerToChooseResources(int quantity){
        //TODO CLI method that returns player choice
        return new Resources();
    }

    /**
     * Player has to put the resources in the correct Warehouse place
     * @param res player has to put
     * @param wh warehouse that will be updated
     */

    private void askPlayerToPutResources(Resources res, Warehouse wh){
        //TODO CLI method that returns the updated warehouse
    }

    /**
     * Setting up new game after all the players have joined
     */

    public void setupGame(){

        gamePhase = GamePhase.START;

        // Setting up market tray

        ArrayList<ResourceMarble> marbles = new ArrayList<ResourceMarble>();

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

        Gson gson = new Gson();

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

        // initialize game
        game = new Game(MT, DMC);


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


        //Preparing stuff for playerboards
        ArrayList<Warehouse> wh = new ArrayList<>();
        ArrayList<Strongbox> sb = new ArrayList<>();
        ArrayList<ProductionPowers> pp = new ArrayList<>();
        ArrayList<PlayerBoard> pb = new ArrayList<>();

        for (int i = 0; i< game.getPlayers().length; i++){

            //Set up player board
            wh.add(new Warehouse());
            sb.add(new Strongbox());
            pp.add(new ProductionPowers(3));
            pb.add(new PlayerBoard(3, wh.get(i), sb.get(i), pp.get(i)));

            game.getPlayers()[i].setBoard(pb.get(i)); //Set Board to Player


            //TODO Give 4 cards to player, he keeps 2 of his choice

            if (i>=1){ //second player gets an extra resource

                Resources extra;
                extra = askPlayerToChooseResources(1);
                askPlayerToPutResources(extra, game.getPlayers()[i].getBoard().getWarehouse());

            }
            if (i>=2){ //third player gets an extra faith in addition to the resource
                game.getPlayers()[i].getBoard().incrementFaithPoints(1);
            }
            if (i>=3){//fourth player gets all of the above and again an extra resource

                Resources extra2;
                extra2 = askPlayerToChooseResources(1);
                askPlayerToPutResources(extra2, game.getPlayers()[i].getBoard().getWarehouse());

            }

        }



    }

    /**
     * The game starts
     */

    public void startGame(){

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


    }

    /**
     * This method represents a players turn
     * @param player
     */

    public void playTurn(Player player){

        boolean primaryActionUsed = false;

        //TODO notify player it's his turn

        TurnChoice choice = null;

        //Player may keep doing as many actions as he wants as long as he doesn't end his turn
        do {

            //TODO get players choice on what he wants to do - he must be offered only secondary actions if he has already used his primary one

            switch (choice) {

                case PLAYLEADER:

                    LeadCard chosenLeader = null;

                    //TODO ask player which leader he intends to play depending on his hand

                    if(chosenLeader.affordable(player)){
                        chosenLeader.play(player);
                    }else{
                        //TODO remind player that he doesn't meet the requirements to play this card
                    }
                case DISCARDLEADER:


                case REARRANGEWAREHOUSE:
                    //Basically we ask the player to put all resources that he has in warehouse in his warehouse
                    askPlayerToPutResources(game.getCurrentPlayer().getBoard().getWarehouse().getResourcesAvailable(), game.getCurrentPlayer().getBoard().getWarehouse());

            }

        }while(choice != TurnChoice.ENDTURN);

    }


}
