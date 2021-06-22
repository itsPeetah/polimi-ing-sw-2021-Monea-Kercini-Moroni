package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.data.player.Strongbox;

public class ASCIIStrongbox {
    public static void draw(Strongbox strongbox){
        System.out.println("::::: STRONGBOX CONTENT :::::");
        ASCIIResources.draw(strongbox.getContent());
        System.out.println("\n:::::::::::::::::::::::::::::");
    }
}
