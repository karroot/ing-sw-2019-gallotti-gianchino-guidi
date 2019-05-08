package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;


/**
 * @author Cysko7927
 */
public class Teleporter extends PowerUpCard
{


    /**
     * Create a powerUp card generic
     * After the creation nobody (Player) has this card
     * @param color Color of the card
     * @param idPU id that identifies the card
     * @exception NullPointerException if color is null
     */
    public Teleporter(Color color, int idPU)
    {
        super(color, idPU);
    }

    /**
     * To use the teleporter card
     * Move the player that has this card square located in a square in an other square
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     * @exception NullPointerException If player of the card is null
     * @exception IllegalArgumentException If coordinate are not valid
     */

    public void usePowerUp(int x, int y) throws NullPointerException ,IllegalArgumentException
    {
        moveYourself(x,y);//Move the player that has this card in the square (x,y)
    }
}