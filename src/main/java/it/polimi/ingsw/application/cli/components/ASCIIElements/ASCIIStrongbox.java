package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.view.data.player.Strongbox;

/**
 * ASCII Strongbox drawing class
 */
public class ASCIIStrongbox {

    /**
     * Draw a Strongbox to the screen
     */
    public static void draw(Strongbox strongbox){
        System.out.println("::::: STRONGBOX CONTENT :::::");
        ASCIIResources.draw(strongbox.getContent());
        System.out.println("\n:::::::::::::::::::::::::::::");
    }
}
