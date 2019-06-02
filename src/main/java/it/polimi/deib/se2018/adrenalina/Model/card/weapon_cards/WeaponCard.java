package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.Card;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

/**
 * This class implements a weapon.
 *
 * @author giovanni
 */

public abstract class WeaponCard extends Card
{

    protected String name;

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

    /**
     * This method takes a response message specified for a weapon and it reads the inputs and after
     * it uses it to use this weapon during the match
     * The response message travels from client to Server and the controller give it to the CardWeapon
     * @param responseMessage response message specified for the weapon
     */
    public abstract void useWeapon (ResponseInput responseMessage);

}