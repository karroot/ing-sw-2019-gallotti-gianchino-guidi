package it.polimi.deib.se2018.adrenalina.communication_message;

public class ResponseGrabStuff extends ResponseInput
{
    private int x;
    private int y;

    public ResponseGrabStuff(int x, int y)
    {
        this.x=x;
        this.y=y;

    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
