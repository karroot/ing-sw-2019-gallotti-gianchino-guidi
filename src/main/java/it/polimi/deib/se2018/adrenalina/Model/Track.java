package it.polimi.deib.se2018.adrenalina.Model;

/**
 * @author Gallotti
 * It represents the structure of Track
 */
public class Track {
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
}
