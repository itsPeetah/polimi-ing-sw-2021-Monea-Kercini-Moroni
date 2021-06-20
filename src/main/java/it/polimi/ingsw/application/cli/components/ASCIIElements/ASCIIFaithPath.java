package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.view.data.CommonData;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.player.FaithTrack;

public class ASCIIFaithPath {
    public static void draw(GameData gameData, boolean isSinglePlayer){

        System.out.println("Faith track:");
        drawCell(1);
        for(int i = 2; i < 25; i++) {
            System.out.print("..");
            drawCell(i);
        }

        FaithTrack ft;
        for(String p : gameData.getPlayersList()){
            ft = gameData.getPlayerData(p).getFaithTrack();
            System.out.println(p + "'s faith progress:");
            System.out.println("FPs: " + ft.getFaith());
            String reports = "Reports attended:";
            int count = 0;
            for(int i = 0; i < 3; i++){
                if(ft.getReportsAttended()[i] != null && ft.getReportsAttended()[i].booleanValue()){
                    count++;
                    reports += " " + i;
                }
            }
            if(count < 1) reports += " none";
            System.out.println(reports);
            System.out.println("____________");
        }
        if(isSinglePlayer){
            System.out.println("Lorenzo's faith progress: " + gameData.getCommon().getLorenzo().getBlackCross());
        }

    }

    private static void drawCell(int i){
        String color = ANSIColor.RESET;
        if((i >= 5 && i <= 7) || (i >= 12 && i <= 15) || i >= 19) color = ANSIColor.YELLOW;
        if(i % 8 == 0 && i > 0) color = ANSIColor.RED;
        if(i % 3 == 0 && i > 0) color = ANSIColor.GREEN;
        System.out.print(color + "["+ i + "]" + ANSIColor.RESET);
    }
}
