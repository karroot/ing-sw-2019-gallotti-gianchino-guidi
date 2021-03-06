package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.Card;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PowerUpCardImmutable;

/**
 * This class represents a generic power up card
 *
 * @author Cysko7927
 */
public abstract class PowerUpCard extends Card
{


    protected Color color;

    protected int idPU;

    protected String name;

    /**
     * Create a powerUp card generic
     * After the creation nobody (Player) has this card
     * @param color Color of the card
     * @param idPU id that identifies the card
     * @exception NullPointerException if color is null
     */
    public PowerUpCard(Color color, int idPU) throws NullPointerException
    {
        if (color == null)
            throw new NullPointerException("Parametro color nullo");

        this.color = color;
        this.idPU = idPU;
        player = null;
    }


    /**
     * Say what is the color of this card
     * @return color of the card
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Say the id of this card
     * @return id of the card
     */
    public int getIdPU() {
        return idPU;
    }

    public String getName() {
        return name;
    }

    public String powerToString()
    {
        return name+ ":" + color;
    }

    public PowerUpCardImmutable getCopyImm()
    {
        return new PowerUpCardImmutable(this);
    }




}