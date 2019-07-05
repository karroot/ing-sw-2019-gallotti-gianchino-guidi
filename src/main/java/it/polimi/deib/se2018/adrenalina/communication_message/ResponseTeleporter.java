package it.polimi.deib.se2018.adrenalina.communication_message;

public class ResponseTeleporter extends ResponseInput
{
    //Attribute for the response
    private int x;//Coordinates for the square chosen by user
    private int y;

    /**
     *  Create the response message for Teleporter
     * @param x coordinate x of the square chosen by user
     * @param y coordinate y of the square chosen by user
     */
    public ResponseTeleporter(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * the coordinate x chosen by user
     * @return get the coordinate x chosen by user
     */
    public int getX()
    {
        return x;
    }

    /**
     * Getter for the coordinate y chosen by user
     * @return get the coordinate y chosen by user
     */
    public int getY()
    {
        return y;
    }
}
