package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
/**
 * This class implements the response for this weapon.
 *
 * @author Cysko7927
 */
public class ResponseNewton extends ResponseInput
{
    //Attribute for the response
    int x;
    int y;
    ColorId target;

    /**
     * Create the response message for Newton
     * @param x coordinate x of the square chosen by user
     * @param y coordinate y of the square chosen by user
     * @param target target to move chosen by user
     */
    public ResponseNewton(int x, int y, ColorId target)
    {
        this.x = x;
        this.y = y;
        this.target = target;
    }

    /**
     * @return get the coordinate x chosen by user
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return get the coordinate y chosen by user
     */
    public int getY()
    {
        return y;
    }

    /**
     * @return get the color of the target chosen by user
     */
    public ColorId getTarget()
    {
        return target;
    }
}
