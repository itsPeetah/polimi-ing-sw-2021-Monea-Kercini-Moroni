package it.polimi.ingsw.application.gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Materials {

    final static PhongMaterial red = new PhongMaterial();
    final static PhongMaterial blue = new PhongMaterial();
    final static PhongMaterial yellow = new PhongMaterial();
    final static PhongMaterial white = new PhongMaterial();
    final static PhongMaterial gray = new PhongMaterial();
    final static PhongMaterial purple = new PhongMaterial();

    /**
     * Constructor
     */
    public Materials(){

        blue.setDiffuseColor(Color.DEEPSKYBLUE);
        yellow.setDiffuseColor(Color.GOLD);
        white.setDiffuseColor(Color.WHITE);
        gray.setDiffuseColor(Color.GRAY);
        purple.setDiffuseColor(Color.SLATEBLUE);
        red.setDiffuseColor(Color.FIREBRICK);

        //The color the spheres will shine

        blue.setSpecularColor(Color.WHITE);
        yellow.setSpecularColor(Color.WHITE);
        white.setSpecularColor(Color.WHITE);
        gray.setSpecularColor(Color.WHITE);
        purple.setSpecularColor(Color.WHITE);
        red.setSpecularColor(Color.WHITE);
    }

    public static PhongMaterial getMaterial(MaterialsEnum m){

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
        //In case no material is found
        return null;

    }

}
