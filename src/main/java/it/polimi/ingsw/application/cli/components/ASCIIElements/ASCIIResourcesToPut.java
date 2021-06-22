package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.view.data.momentary.ResourcesToPut;

/**
 * ASCII ResourcesToPut drawing class
 */
public class ASCIIResourcesToPut {

    /**
     * Draw a ResourcesToPut object to the screen
     */
    public static void draw(ResourcesToPut rtp){
        System.out.println("Resources to organize in the warehouse: ");
        ASCIIResources.draw(rtp.getRes());
        System.out.print("\n");
    }
}
