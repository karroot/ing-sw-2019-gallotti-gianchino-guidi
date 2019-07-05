package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.Card;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.WeaponCardImmutable;

/**
 * This class implements a weapon.
 *
 * @author gioguidi and Cysko7927
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
     * This is the public constructor of the class.
     *
     * @param color is the color of the weapon
     * @param weaponID is a unique ID that identifies the weapon
     * @param isLoaded is a boolen to indicate if the weapon is loaded
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

    /**
     * This is the public getter for the attribute color.
     *
     * @return the color attribute
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * This is the public getter for the attribute redAmmoCost.
     *
     * @return the redAmmoCost attribute
     */
    public int getRedAmmoCost()
    {
        return redAmmoCost;
    }

    /**
     * This is the public getter for the attribute blueAmmoCost.
     *
     * @return the blueAmmoCost attribute
     */
    public int getBlueAmmoCost()
    {
        return blueAmmoCost;
    }

    /**
     * This is the public getter for the attribute yellowAmmoCost.
     *
     * @return the yellowAmmoCost attribute
     */
    public int getYellowAmmoCost()
    {
        return yellowAmmoCost;
    }

    /**
     * This is the public getter for the attribute name.
     *
     * @return the name attribute
     */
    public String getName()
    {
        return name;
    }

    /**
     * This is the public getter for the attribute weaponID.
     *
     * @return the weaponID attribute
     */
    public int getWeaponID()
    {
        return weaponID;
    }

    /**
     * This is the public getter for the attribute isLoaded.
     *
     * @return the isLoaded attribute
     */
    public boolean isLoaded()
    {
        return isLoaded;
    }

    /**
     * This is the public setter for the attribute color.
     *
     * @param loaded is the loaded boolean value to set
     *
     */
    public void setLoaded(boolean loaded)
    {
        isLoaded = loaded;
    }

    /**
     * This is the public setter for the attribute name.
     *
     * @param name is the name string to set
     *
     */
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * This method will return a new weaponCardImmutable.
     *
     * @return a WeaponCardImmutable
     */
    public WeaponCardImmutable getCopyImm()
    {
        return new WeaponCardImmutable(this);
    }


    /**
     * This method takes a response message specified for a weapon and it reads the inputs and after
     * it uses it to use this weapon during the match
     * The response message travels from client to Server and the controller give it to the CardWeapon
     *
     * @param responseMessage response message specified for the weapon
     * @throws Exception if there are errors in the response message
     */
    public abstract void useWeapon (ResponseInput responseMessage) throws Exception;


    /**
     * Check which modes of the weapon can be used by player that has this weapon
     *
     * @return array of booleans , represents which modes can be used by player
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public  abstract boolean[]  checkAvailableMode();

    /**
     * Get a message of request for this weapon
     * The content of the message depends if the modes of this weapon can be used by the player that has got it
     *
     * @return Message of request to send at client
     */
    public abstract RequestInput getRequestMessage();


}