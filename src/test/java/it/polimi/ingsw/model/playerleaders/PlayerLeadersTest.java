package it.polimi.ingsw.model.playerleaders;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.cards.LeadCardAbility;
import it.polimi.ingsw.model.cards.LeadCardRequirements;
import it.polimi.ingsw.model.game.MarketTray;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLeadersTest {

    //Method for creating a basic LC for testing, taking only it's victory points as input
    private LeadCard basicLC(int vp){

        //First initialize the LeadCardReq
        Resources res = new Resources();
        //res.add(ResourceType.STONES, 3);
        HashMap<Color, Integer> colorReq = new HashMap<>();
        HashMap<Color, Level> levelReq = new HashMap<>();
        LeadCardRequirements LCC = new LeadCardRequirements(colorReq, levelReq, res);

        //Then the LeadCardAbility
        //Resources res2 = new Resources();
        Production prod = new Production(res, res);
        LeadCardAbility LCA = new LeadCardAbility(res, res, ResourceType.STONES, prod);

        //And lastly the LeadCard
        return new LeadCard(vp, "a", LCC, LCA);

    }

    @Test
    void getPlayableCards() {
        //This method is automatically tested below
    }

    @Test
    void getPlayedCards() {
        //This method is automatically tested below
    }

    /* Test the method with the index as input */
    @Test
    void playCardWithIndex() {
        LeadCard[] Hand = new LeadCard[3];
        Hand[0] = basicLC(1);
        Hand[1] = basicLC(2);
        Hand[2] = basicLC(5);
        //Created an Array of LeadCards
        PlayerLeaders pl = new PlayerLeaders(3);

        pl.setCards(Hand);

        assertEquals(pl.getPlayedCards().size(), 0); //Check that we have no cards played.

        try{pl.playCard(0);
        } catch (Exception e){
            fail();
        }

        try{pl.playCard(2);
        } catch (Exception e){
            fail();
        }

        LeadCard[] PlayedHand = new LeadCard[2];
        PlayedHand[0] = Hand[0];
        PlayedHand[1] = Hand[2];

        //Check if played cards correspond

        assertArrayEquals(PlayedHand, pl.getPlayedCards().toArray(new LeadCard[0]));



        //Card has already been played

        assertThrows(PlayerLeadersException.class, ()-> pl.playCard(0));

        LeadCard[] Hand2 = new LeadCard[1];
        Hand2[0] = Hand[1];

        //Check if the new hand corresponds

        assertArrayEquals(Hand2, pl.getPlayableCards().toArray(new LeadCard[0]));
    }

    /* Test the method with the LeadCard as input */
    @Test
    void playCardWithLeadCard() {
        LeadCard[] Hand = new LeadCard[3];
        Hand[0] = CardManager.loadLeadCardsFromJson().get(0);
        Hand[1] = CardManager.loadLeadCardsFromJson().get(1);
        Hand[2] = CardManager.loadLeadCardsFromJson().get(2);
        //Created an Array of LeadCards
        PlayerLeaders pl = new PlayerLeaders(3);

        pl.setCards(Hand);

        assertEquals(pl.getPlayedCards().size(), 0); //Check that we have no cards played.

        try{
            pl.playCard(Hand[0]);
        } catch (Exception e){
            fail();
        }

        try{pl.playCard(Hand[2]);
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }

        LeadCard[] PlayedHand = new LeadCard[2];
        PlayedHand[0] = Hand[0];
        PlayedHand[1] = Hand[2];

        //Check if played cards correspond

        assertArrayEquals(PlayedHand, pl.getPlayedCards().toArray(new LeadCard[0]));

        //Card has already been played

        assertThrows(PlayerLeadersException.class, ()-> pl.playCard(Hand[0]));

        LeadCard[] Hand2 = new LeadCard[1];
        Hand2[0] = Hand[1];

        //Check if the new hand corresponds

        assertArrayEquals(Hand2, pl.getPlayableCards().toArray(new LeadCard[0]));

        // Check if playing a card that is not in the player leaders throws the exception
        assertThrows(PlayerLeadersException.class, ()-> pl.playCard(basicLC(0)));
    }

    @Test
    void setCards() {
        LeadCard[] Hand = new LeadCard[3];
        Hand[0] = basicLC(1);
        Hand[1] = basicLC(2);
        Hand[2] = basicLC(5);
        //Created an Array of LeadCards
        PlayerLeaders pl = new PlayerLeaders(3);

        pl.setCards(Hand);

        //Check if the cards are correct
        assertArrayEquals(Hand, pl.getPlayableCards().toArray(new LeadCard[0]));

    }

    @Test
    void getPlayedCardVictoryPoints() {
        LeadCard[] Hand = new LeadCard[3];
        Hand[0] = CardManager.loadLeadCardsFromJson().get(0);
        Hand[1] = CardManager.loadLeadCardsFromJson().get(1);
        Hand[2] = CardManager.loadLeadCardsFromJson().get(2);
        //Created an Array of LeadCards
        PlayerLeaders pl = new PlayerLeaders(3);

        assertEquals(0, pl.getPlayedCardVictoryPoints());

        pl.setCards(Hand);

        assertEquals(0, pl.getPlayedCardVictoryPoints());

        try{pl.playCard(0);
        } catch (Exception e){
            fail();
        }

        try{pl.playCard(2);
        } catch (Exception e){
            fail();
        }

        assertEquals(4, pl.getPlayedCardVictoryPoints());

    }
}