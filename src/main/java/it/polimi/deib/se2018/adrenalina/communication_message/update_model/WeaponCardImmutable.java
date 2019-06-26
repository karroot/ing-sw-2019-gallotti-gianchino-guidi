package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;

public class WeaponCardImmutable implements Serializable
{
    protected String name;

    protected Color color;

    protected int weaponID;

    protected int redAmmoCost;
    protected int yellowAmmoCost;
    protected int blueAmmoCost;

    protected boolean isLoaded;

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

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getWeaponID() {
        return weaponID;
    }

    public int getRedAmmoCost() {
        return redAmmoCost;
    }

    public int getYellowAmmoCost() {
        return yellowAmmoCost;
    }

    public int getBlueAmmoCost() {
        return blueAmmoCost;
    }

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
