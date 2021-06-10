package it.polimi.ingsw.application.cli.components.ASCIIElements;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.data.player.Warehouse;

public class ASCIIWarehouse {

    public static void draw(Warehouse wh) {

        Resources[] content = wh.getContent();
        Resources[] extra = wh.getExtra();
        LeadCard[] leaders = wh.getActivatedLeaders();

        System.out.println("::::: WAREHOUSE CONTENT :::::");
        // Top row
        System.out.print("[");
        if (content[2] != null && content[2].getTotalAmount() < 1) ASCIIResources.draw(content[2]);
        else System.out.print("Top row: empty");
        System.out.print("]\n");
        // Mid row
        System.out.print("[");
        if(content[1] != null && content[1].getTotalAmount() < 1) ASCIIResources.draw(content[1]);
        else System.out.print("Mid row: empty");
        System.out.print("]\n");
        // Bottom row
        System.out.print("[");
        if(content[0] != null && content[0].getTotalAmount() < 1) ASCIIResources.draw(content[0]);
        else System.out.print("Bot row: empty");
        System.out.print("]\n");
        System.out.println("::::::: EXTRA SPACES ::::::::");
        // Leaders
        if(leaders[0] != null){
            System.out.print("Leader 1: 2 extra spaces for ");
            ASCIIResources.draw(leaders[0].getAbility().getExtraWarehouseSpace());
            System.out.print("\n");
            System.out.print("[");
            if(extra[0] != null && extra[0].getTotalAmount() < 1) ASCIIResources.draw(extra[0]);
            else System.out.print("2 spaces available");
            System.out.print("]\n");
        }
        if(leaders[1] != null){
            System.out.print("Leader 1: 2 extra spaces for ");
            ASCIIResources.draw(leaders[1].getAbility().getExtraWarehouseSpace());
            System.out.print("\n");
            System.out.print("[");
            if(extra[1] != null && extra[1].getTotalAmount() < 1) ASCIIResources.draw(extra[1]);
            else System.out.print("2 spaces available");
            System.out.print("]\n");
        }
        System.out.println(":::::::::::::::::::::::::::::");

    }
}
