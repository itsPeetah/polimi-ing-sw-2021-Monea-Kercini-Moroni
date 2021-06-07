package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.view.data.common.MarketTray;

public class ASCIIMarketTray {

    public static void draw(MarketTray tray){
        System.out.println("=== RESOURCE MARKET TRAY ===");
        System.out.print("[");
        ASCIIResources.draw(tray.getWaiting()[0].getValue());
        System.out.print("]\n");
        for(int y = 0; y < 3; y++) {
            for (int x = 0; x < 4; x++){
                System.out.print("[");
                ASCIIResources.draw(tray.getAvailable()[y][x].getValue());
                System.out.print("]");
            }
            System.out.print(" <\n");
        }
        System.out.println(" ^  ^  ^  ^");
        System.out.println("============================");
    }


}
