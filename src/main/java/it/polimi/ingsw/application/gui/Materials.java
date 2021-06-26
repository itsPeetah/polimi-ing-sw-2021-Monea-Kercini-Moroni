package it.polimi.ingsw.application.gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Materials {

    private final static PhongMaterial red = new PhongMaterial();
    private final static PhongMaterial blue = new PhongMaterial();
    private final static PhongMaterial yellow = new PhongMaterial();
    private final static PhongMaterial white = new PhongMaterial();
    private final static PhongMaterial gray = new PhongMaterial();
    private final static PhongMaterial purple = new PhongMaterial();

    /**
     * Set the colors
     */
    public static void init() {
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

    /**
     * Get a material.
     * @param m color of the material.
     * @return material corresponding to the input color.
     */
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
