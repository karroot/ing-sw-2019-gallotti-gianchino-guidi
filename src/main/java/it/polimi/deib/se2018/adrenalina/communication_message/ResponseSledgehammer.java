package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;



public class ResponseSledgehammer extends ResponseInput
{
    private ColorId target;//Target chosen for the basic mode or the alternative mode
    boolean move;//Indicates if the user has chosen to move the target
    int x; //Coordinates of the squares where the target will move
    int y;
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for Sledgehammer in basic mode
     * @param target target for Sledgehammer
     */
    public ResponseSledgehammer(ColorId target)
    {
        this.target = target;
        this.mode = false;
    }

    /**
     * Create the response message for Sledgehammer in alternative mode
     * @param target targets for Sledgehammer
     * @param x coordinate x of the square chosen by user
     * @param y coordinate y of the square chosen by user
     * @param move Indicates if the user has chosen to move the target
     */
    public ResponseSledgehammer(ColorId target, int x, int y , boolean move)
    {
        this.target = target;
        this.x = x;
        this.y = y;
        this.move = move;
        this.mode = true;
    }


    /**
     * @return get the target for Sledgehammer in basic mode
     */
    public ColorId getTarget()
    {
        return target;
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
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }

    /**
     * @return Say if the user has chosen to move the target
     */
    public boolean isMove()
    {
        return move;
    }

}
