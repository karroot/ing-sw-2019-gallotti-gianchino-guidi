package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;

public class ResponseRunAround extends ResponseInput {
    private int x;
    private int y;

    /**
     * Create the response message for RunAround in basic mode
     * @param y coordinate y of the square
     * @param x coordinate x of the square
     */
    public ResponseRunAround(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}