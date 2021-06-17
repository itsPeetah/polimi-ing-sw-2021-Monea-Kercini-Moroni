package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.general.Production;

public class ASCIIProduction {
    public static void draw(Production p){
        System.out.print("{");
        ASCIIResources.draw(p.getInput());
        System.out.print("} --> {");
        ASCIIResources.draw(p.getOutput());
        System.out.print("}\n");
    }
}
