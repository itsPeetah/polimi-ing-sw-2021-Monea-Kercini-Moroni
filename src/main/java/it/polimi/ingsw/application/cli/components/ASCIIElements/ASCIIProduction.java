package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.general.Production;

/**
 * ASCII Production drawing class
 */
public class ASCIIProduction {

    /**
     * Draw a Production to the screen
     */
    public static void draw(Production p){
        System.out.print("{");
        ASCIIResources.draw(p.getInput());
        System.out.print("} --> {");
        ASCIIResources.draw(p.getOutput());
        System.out.print("}\n");
    }
}
