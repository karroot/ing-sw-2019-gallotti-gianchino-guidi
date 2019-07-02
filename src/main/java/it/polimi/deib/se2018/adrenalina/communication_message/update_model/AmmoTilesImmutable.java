package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import java.io.Serializable;

/**
 * This class represent a copy Immutable of an AmmoTiles generic that coming by model
 */
public abstract class AmmoTilesImmutable implements Serializable
{
    protected int ammoCardID;

    /**
     * Getter for the Id of Ammo Card (being used by GUI)
     * @return Id of Ammo Card
     */
    public int getAmmoCardID() {
        return ammoCardID;
    }
}
