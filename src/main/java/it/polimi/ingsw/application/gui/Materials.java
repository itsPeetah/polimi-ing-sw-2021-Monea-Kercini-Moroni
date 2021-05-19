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
    final PhongMaterial purple = new PhongMaterial();

    /**
     * Constructor
     */

    Materials(){
        red.setDiffuseColor(Color.RED);
        blue.setDiffuseColor(Color.BLUE);
        yellow.setDiffuseColor(Color.YELLOW);
        white.setDiffuseColor(Color.WHITE);
        gray.setDiffuseColor(Color.GRAY);
        purple.setDiffuseColor(Color.PURPLE);
    }

    public PhongMaterial Materials(Materials m){

        switch (m){

            case RED:
                return red;

            case BLUE:
                return blue;

            case GRAY:
                return gray;

            case WHITE:
                return white;

            case PURPLE:
                return purple;

            case YELLOW:
                return yellow;

        }

        return null;

    }

}
