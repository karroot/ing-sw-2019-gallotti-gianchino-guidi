package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;

import java.io.Serializable;
/**
 * This class represent a copy Immutable of a Power and Ammo tile coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class PowerAndAmmoImmutable extends AmmoTilesImmutable implements Serializable
{
    private Color singleAmmo;
    private Color secondSingleAmmo;

    /**
     * Create the copy of PowerAndAmmo tile
     * @param ammoCardID Id of the ammo tiles
     * @param singleAmmo color of the first ammo
     * @param secondSingleAmmo color of the second ammo
     */
    public PowerAndAmmoImmutable(int ammoCardID, Color singleAmmo, Color secondSingleAmmo) {
        this.ammoCardID = ammoCardID;
        this.singleAmmo = singleAmmo;
        this.secondSingleAmmo = secondSingleAmmo;
    }


    /**
     * Getter for the color of the first ammo
     * @return color of the first ammo
     */
    public Color getSingleAmmo() {
        return singleAmmo;
    }

    /**
     * Getter for the color of the second ammo
     * @return color of the second ammo
     */
    public Color getSecondSingleAmmo() {
        return secondSingleAmmo;
    }
}
