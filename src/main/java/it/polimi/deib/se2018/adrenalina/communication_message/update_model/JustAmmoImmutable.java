package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;

import java.io.Serializable;

public class JustAmmoImmutable extends AmmoTilesImmutable implements Serializable
{
    private int ammoCardID;
    private Color singleAmmo;
    private Color doubleAmmo;

    public JustAmmoImmutable(int ammoCardID, Color singleAmmo, Color doubleAmmo) {
        this.ammoCardID = ammoCardID;
        this.singleAmmo = singleAmmo;
        this.doubleAmmo = doubleAmmo;
    }

    public int getAmmoCardID() {
        return ammoCardID;
    }

    public Color getSingleAmmo() {
        return singleAmmo;
    }

    public Color getDoubleAmmo() {
        return doubleAmmo;
    }
}
