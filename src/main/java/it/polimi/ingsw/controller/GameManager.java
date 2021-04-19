package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.general.ResourceType;


import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    private GamePhase gamePhase;
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

    public void setupGame(){

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

        try {
            MarketTray MT = new MarketTray(3, 4, marbles);
        }catch (Exception e){
            System.out.println("Market Tray was initialized wrong!");
        }


        //setting up dev card market

        // TODO DevCardMarket DCM = new DevCardMarket(devcards.json)

        //TODO set up lead cards
        //shuffle lead cards

        //shuffle player order
        game.shufflePlayers();



        for (int i = 0; i< game.getPlayers().length; i++){

        }






    }


}
