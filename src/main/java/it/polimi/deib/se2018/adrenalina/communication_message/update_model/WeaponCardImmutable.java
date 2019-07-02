package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;

/**
 * This class represent a copy Immutable of a Weapon card  coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class WeaponCardImmutable implements Serializable
{
    protected String name;

    protected Color color;

    protected int weaponID;

    protected int redAmmoCost;
    protected int yellowAmmoCost;
    protected int blueAmmoCost;

    protected boolean isLoaded;

    /**
     * Create the copy of the weapon card
     * @param weaponCard weapon card from the model
     */
    public WeaponCardImmutable(WeaponCard weaponCard)
    {
        name = weaponCard.getName();
        color = weaponCard.getColor();
        weaponID = weaponCard.getWeaponID();
        redAmmoCost = weaponCard.getRedAmmoCost();
        yellowAmmoCost = weaponCard.getYellowAmmoCost();
        blueAmmoCost = weaponCard.getBlueAmmoCost();
        isLoaded = weaponCard.isLoaded();
    }

    /**
     * Getter for the name of the weapon
     * @return name of the weapon
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the color of the weapon
     * @return color of the weapon
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for the Id of the weapon
     * @return Id of the weapon
     */
    public int getWeaponID() {
        return weaponID;
    }

    /**
     * Getter for the red ammo cost
     * @return red ammo cost
     */
    public int getRedAmmoCost() {
        return redAmmoCost;
    }

    /**
     * Getter for the yellow ammo cost
     * @return yellow ammo cost
     */
    public int getYellowAmmoCost() {
        return yellowAmmoCost;
    }

    /**
     * Getter for the blue ammo cost
     * @return blue ammo cost
     */
    public int getBlueAmmoCost() {
        return blueAmmoCost;
    }

    /**
     * Say if the weapon is loaded or not
     * @return weapon is loaded or not
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", redAmmoCost=" + redAmmoCost +
                ", yellowAmmoCost=" + yellowAmmoCost +
                ", blueAmmoCost=" + blueAmmoCost +
                ", isLoaded=" + isLoaded +
                '}';
    }
}
