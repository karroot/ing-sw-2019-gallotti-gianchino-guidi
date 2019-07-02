package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Track;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a copy Immutable of the game board coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class BoardImmutable implements Serializable
{
    private int originalSkullCouner;
    private int skullCounter;
    private int code;
    private List<Track> killShotTrack;


    /**
     * Create the copy immutable of the game board
     * @param board board of the model
     */
    public BoardImmutable(GameBoard board)
    {
        this.skullCounter = board.getSkullCounter();
        this.code = board.getCode();
        this.killShotTrack = board.getKillShotTrack();
        this.originalSkullCouner = board.getOriginalSkullCounter();
    }

    /**
     * Getter for the number of skull Counter
     * @return number of skull Counter
     */
    public int getSkullCounter() {
        return skullCounter;
    }

    /**
     * Getter for the code of Arena used during the match
     * @return code of Arena
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter for all the tracks of the killshootTrack
     * @return list of all tracks
     */
    public List<Track> getKillShotTrack() {
        return new ArrayList<>(killShotTrack);
    }

    /**
     * Getter for the starting number of skulls
     * @return the starting number of skulls
     */
    public int getOriginalSkullCouner() {
        return originalSkullCouner;
    }
}
