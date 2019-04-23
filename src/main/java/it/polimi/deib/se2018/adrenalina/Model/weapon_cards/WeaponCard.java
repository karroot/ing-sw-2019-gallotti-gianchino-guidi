package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

/**
 * This class implements a weapon.
 *
 * @author giovanni
 */

public abstract class WeaponCard {

    String name;

    private Player player;

    protected Color color;

    protected int weaponID;

    protected int redAmmoCost;
    protected int yellowAmmoCost;
    protected int blueAmmoCost;

    protected boolean isLoaded;




    public WeaponCard()
    {

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

    public void basicEffect()
    {

    }

    public String getName()
    {
        return name;
    }
}