package it.polimi.deib.se2018.adrenalina.Model.power_up_cards;

import java.util.*;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;


public abstract class PowerUpCard
{


    private Player player;

    protected Color color;

    protected int idPU;

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

    public PowerUpCard() {

    }

    /**
     * this card belong now at a player
     * @param player player that noe he has the card
     * @exception NullPointerException if the player is null
     */
    public void setPlayer(Player player) throws NullPointerException
    {
        if (player == null)
            throw new NullPointerException("Parametro plaer nullo");

        this.player = player;
    }

    /**
     * Say which player has this card, if nobody has this card then it returns null
     * @return the player that has this card
     */
    public Player getPlayer()
    {
        return player;
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
}