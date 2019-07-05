package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * This class implements the response for this weapon.
 *
 *  @author Cysko7927
 */
public class ResponseShotgun extends ResponseInput
{
    private ColorId target;//Target for basic mode or alternative mode
    private int x;//Coordinates of the squares where the target will move
    private int y;
    private boolean move;//Indicates if the user has chosen to move the target
    private boolean mode;//false = player chose the basic mode, true = player chose the alternative mode


    /**
     * Create the message of response for the shotgun
     * It Contains all the input and necessary information for using the shotgun
     * @param target target chosen by user to shooting with shotgun
     * @param mode false = player chose the basic mode, true = player chose the alternative mode
     * @param move Indicates if the user has chosen to move the target
     * @param x coordinate x of the square chosen by user
     * @param y coordinate y of the square chosen by user
     */
    public ResponseShotgun(ColorId target, boolean mode, boolean move,int x,int y)
    {
        this.target = target;
        this.mode = mode;
        this.move = move;
        this.x = x;
        this.y = y;
    }

    /**
     * @return get the target for Shotgun in basic mode or alternative mode
     */
    public ColorId getTarget()
    {
        return target;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }

    /**
     * @return get the coordinate x of the square chosen by user
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return get the coordinate y of the square chosen by user
     */
    public int getY()
    {
        return y;
    }

    /**
     * @return Say if the user has chosen to move the target
     */
    public boolean isMove()
    {
        return move;
    }
}
