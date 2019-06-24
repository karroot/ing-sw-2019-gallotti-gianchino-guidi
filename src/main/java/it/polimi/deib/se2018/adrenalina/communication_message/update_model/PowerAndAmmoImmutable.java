package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;

import java.io.Serializable;

public class PowerAndAmmoImmutable extends AmmoTilesImmutable implements Serializable
{
    private Color singleAmmo;
    private Color secondSingleAmmo;

    public PowerAndAmmoImmutable(int ammoCardID, Color singleAmmo, Color secondSingleAmmo) {
        this.ammoCardID = ammoCardID;
        this.singleAmmo = singleAmmo;
        this.secondSingleAmmo = secondSingleAmmo;
    }


    public Color getSingleAmmo() {
        return singleAmmo;
    }

    public Color getSecondSingleAmmo() {
        return secondSingleAmmo;
    }
}
