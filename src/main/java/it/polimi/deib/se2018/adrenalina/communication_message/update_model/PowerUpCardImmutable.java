package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;

import java.io.Serializable;

public class PowerUpCardImmutable implements Serializable
{
    protected Color color;

    protected int idPU;

    protected String name;

    public PowerUpCardImmutable(PowerUpCard powerUpCard)
    {
        color = powerUpCard.getColor();
        idPU = powerUpCard.getIdPU();
        name = powerUpCard.getName();
    }

    public Color getColor() {
        return color;
    }

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

    @Override
    public String toString() {
        return powerToString();

    }
}
