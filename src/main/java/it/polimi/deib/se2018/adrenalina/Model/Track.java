package it.polimi.deib.se2018.adrenalina.Model;

import java.io.Serializable;

/**
 * @author Gallotti
 * It represents the structure of Track
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
