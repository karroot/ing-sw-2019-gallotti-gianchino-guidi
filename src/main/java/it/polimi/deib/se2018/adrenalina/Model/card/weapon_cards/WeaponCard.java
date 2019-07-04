package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.Card;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.WeaponCardImmutable;

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

    public Color getColor() {
        return color;
    }

    public int getWeaponID() {
        return weaponID;
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


    public WeaponCardImmutable getCopyImm()
    {
        return new WeaponCardImmutable(this);
    }


    /**
     * This method takes a response message specified for a weapon and it reads the inputs and after
     * it uses it to use this weapon during the match
     * The response message travels from client to Server and the controller give it to the CardWeapon
     * @param responseMessage response message specified for the weapon
     * @throws Exception if there are error in the response message
     */
    public abstract void useWeapon (ResponseInput responseMessage) throws Exception;


    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans , represents which modes can be used by player
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public  abstract boolean[]  checkAvailableMode();

    /**
     * Get a message of request for this weapon
     * The content of the message depends if the modes of this weapon can be used
     * by the player that has got it
     * @return Message of request to send at client
     */
    public abstract RequestInput getRequestMessage();


}