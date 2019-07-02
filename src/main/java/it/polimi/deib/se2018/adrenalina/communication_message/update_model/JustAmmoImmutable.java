package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;

import java.io.Serializable;

/**
 * This class represent a copy Immutable of a JustAmmo tiles coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class JustAmmoImmutable extends AmmoTilesImmutable implements Serializable
{
    private Color singleAmmo;
    private Color doubleAmmo;

    /**
     * Create the copy of JustAmmo
     * @param ammoCardID Id of the ammo tiles
     * @param singleAmmo color of the first ammo
     * @param doubleAmmo color of the double ammo
     */
    public JustAmmoImmutable(int ammoCardID, Color singleAmmo, Color doubleAmmo) {
        this.ammoCardID = ammoCardID;
        this.singleAmmo = singleAmmo;
        this.doubleAmmo = doubleAmmo;
    }


    /**
     * Getter for the color of the first ammo
     * @return color of the first ammo
     */
    public Color getSingleAmmo() {
        return singleAmmo;
    }

    /**
     * Getter for the color of the double ammo
     * @return color of the double ammo
     */
    public Color getDoubleAmmo() {
        return doubleAmmo;
    }
}
