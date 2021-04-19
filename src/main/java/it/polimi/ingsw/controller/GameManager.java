package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;



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


        //Preparing stuff for playerboards
        ArrayList<Warehouse> wh = new ArrayList<Warehouse>();
        ArrayList<Strongbox> sb = new ArrayList<Strongbox>();
        ArrayList<ProductionPowers> pp = new ArrayList<ProductionPowers>();
        ArrayList<PlayerBoard> pb = new ArrayList<PlayerBoard>();

        for (int i = 0; i< game.getPlayers().length; i++){

            //Set up player board
            wh.add(new Warehouse());
            sb.add(new Strongbox());
            pp.add(new ProductionPowers(3));
            pb.add(new PlayerBoard(3, wh.get(i), sb.get(i), pp.get(i)));

            game.getPlayers()[i].setBoard(pb.get(i)); //Set Board to Player


            //TODO Give 4 cards to player, he keeps 2 of his choice

            if (i>=1){ //second player gets an extra resource

                Resources extra = new Resources();
                extra = askPlayerToChooseResources(1);
                askPlayerToPutResources(extra, game.getPlayers()[i].getBoard().getWarehouse());

            }
            if (i>=2){ //third player gets an extra faith in addition to the resource
                game.getPlayers()[i].getBoard().incrementFaithPoints(1);
            }
            if (i>=3){//fourth player gets all of the above and again an extra resource

                Resources extra2 = new Resources();
                extra2 = askPlayerToChooseResources(1);
                askPlayerToPutResources(extra2, game.getPlayers()[i].getBoard().getWarehouse());

            }

        }






    }


}
