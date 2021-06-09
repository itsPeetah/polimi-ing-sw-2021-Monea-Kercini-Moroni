package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.data.momentary.ResourcesToPut;

public class ASCIIResourcesToPut {

    public static void draw(ResourcesToPut rtp){
        System.out.println("Resources to organize in the warehouse: ");
        ASCIIResources.draw(rtp.getRes());
        System.out.println("---------------------------------------");
    }
}
