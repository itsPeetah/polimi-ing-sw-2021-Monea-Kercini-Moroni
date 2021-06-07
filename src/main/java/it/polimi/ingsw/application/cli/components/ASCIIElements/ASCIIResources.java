package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.network.common.SystemMessage;

import java.sql.SQLOutput;

public class ASCIIResources {

    public static void draw(Resources r){

        System.out.print(ANSIColor.PURPLE);
        for(int i = 0; i < r.getAmountOf(ResourceType.SERVANTS); i++){
            System.out.print("S");
        }
        System.out.print(ANSIColor.YELLOW);
        for(int i = 0; i < r.getAmountOf(ResourceType.COINS); i++){
            System.out.print("C");
        }
        System.out.print(ANSIColor.BLUE);
        for(int i = 0; i < r.getAmountOf(ResourceType.SHIELDS); i++){
            System.out.print("S");
        }
        System.out.print(ANSIColor.GREEN);
        for(int i = 0; i < r.getAmountOf(ResourceType.STONES); i++){
            System.out.print("S");
        }
        System.out.print(ANSIColor.RED);
        for(int i = 0; i < r.getAmountOf(ResourceType.FAITH); i++){
            System.out.print("F");
        }
        System.out.print(ANSIColor.CYAN);
        for(int i = 0; i < r.getAmountOf(ResourceType.CHOICE); i++){
            System.out.print("?");
        }
        System.out.print(ANSIColor.RESET);
        for(int i = 0; i < r.getAmountOf(ResourceType.BLANK); i++){
            System.out.print("X");
        }

    }

}
