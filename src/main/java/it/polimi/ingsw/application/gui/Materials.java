package it.polimi.ingsw.application.gui;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;

public enum Materials {
    RED,
    BLUE,
    PURPLE,
    YELLOW,
    GRAY,
    WHITE;


    final PhongMaterial red = new PhongMaterial();
    final PhongMaterial blue = new PhongMaterial();
    final PhongMaterial yellow = new PhongMaterial();
    final PhongMaterial white = new PhongMaterial();
    final PhongMaterial gray = new PhongMaterial();

    Materials(){
        red.setDiffuseColor(Color.RED);
        blue.setDiffuseColor(Color.BLUE);
        yellow.setDiffuseColor(Color.YELLOW);
        white.setDiffuseColor(Color.WHITE);
        gray.setDiffuseColor(Color.GRAY);
    }

}
