package it.polimi.deib.se2018.adrenalina.Model;

import java.io.Serializable;

/**
 * It represents the structure of Track
 *
 * @author Karroot
 *
 */
public class Track implements Serializable
{
    private ColorId player;
    private int  pointCounter;

    public Track(ColorId player,int pointCounter){
        this.player=player;
        this.pointCounter=pointCounter;

    }

    public ColorId getPlayer() {
        return player;
    }

    public int getPointCounter() {
        return pointCounter;
    }

    /**
     * this method is used to return the string of player's color and his point
     * @return the string of player's color and his point
     */
    @Override
    public String toString() {
        return "{" +
                "Colore Giocatore=" + player +
                ", Numero punti=" + pointCounter +
                "}\n";
    }

    public void setPlayer(ColorId player) {
        this.player = player;
    }

    public void setPointCounter(int pointCounter) {
        this.pointCounter = pointCounter;
    }
}
