package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;

import java.io.Serializable;

/**
 * This class represent a copy Immutable of a Power up Card  coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class PowerUpCardImmutable implements Serializable
{
    protected Color color;

    protected int idPU;

    protected String name;

    /**
     * Create the copy of the power up card
     * @param powerUpCard object power up card from the model
     */
    public PowerUpCardImmutable(PowerUpCard powerUpCard)
    {
        color = powerUpCard.getColor();
        idPU = powerUpCard.getIdPU();
        name = powerUpCard.getName();
    }

    /**
     * Getter for the color of the powerUp
     * @return color of the powerUp
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for the ID of the powerUp
     * @return ID of the powerUp
     */
    public int getIdPU() {
        return idPU;
    }

    /**
     * Getter for the name of the powerUp
     * @return name of the powerUp
     */
    public String getName() {
        return name;
    }

    /**
     * Return a string where is written the name and the color of the powerUp
     * @return the name and the color of the powerUp
     */
    public String powerToString()
    {
        return name+ ":" + color;
    }

    /**
     * Return a string where is written the name and the color of the powerUp
     * @return the name and the color of the powerUp
     */
    @Override
    public String toString() {
        return powerToString();

    }
}
