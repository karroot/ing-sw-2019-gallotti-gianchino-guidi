package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Track;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoardImmutable implements Serializable
{
    private int originalSkullCouner;
    private int skullCounter;
    private int code;
    private List<Track> killShotTrack;


    public BoardImmutable(GameBoard board)
    {
        this.skullCounter = board.getSkullCounter();
        this.code = board.getCode();
        this.killShotTrack = board.getKillShotTrack();
        this.originalSkullCouner = board.getOriginalSkullCounter();
    }

    public int getSkullCounter() {
        return skullCounter;
    }

    public int getCode() {
        return code;
    }

    public List<Track> getKillShotTrack() {
        return new ArrayList<>(killShotTrack);
    }

    public int getOriginalSkullCouner() {
        return originalSkullCouner;
    }
}
