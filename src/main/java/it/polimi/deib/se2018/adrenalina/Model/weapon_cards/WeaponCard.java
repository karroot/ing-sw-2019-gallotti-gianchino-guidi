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

    protected Player player;

    protected Color color;

    protected int weaponID;

    protected int redAmmoCost;
    protected int yellowAmmoCost;
    protected int blueAmmoCost;

    protected boolean isLoaded;


    /**
     *
     * @param color
     * @param weaponID
     * @param isLoaded
     * @exception NullPointerException if color is null
     */
    public WeaponCard(Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        if (color == null)
            throw new  NullPointerException("colore nullo");

        this.color = color;
        this.weaponID = weaponID;
        this.isLoaded = isLoaded;
    }

    public int getRedAmmoCost()
    {
        return redAmmoCost;
    }

    public int getYellowAmmoCost()
    {
        return yellowAmmoCost;
    }

    public int getBlueAmmoCost()
    {
        return blueAmmoCost;
    }

    public String getName()
    {
        return name;
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    public void setLoaded(boolean loaded)
    {
        isLoaded = loaded;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }
}