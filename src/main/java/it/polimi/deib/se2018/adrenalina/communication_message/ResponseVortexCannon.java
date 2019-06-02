package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author giovanni
 */
public class ResponseVortexCannon extends ResponseInput
{
    private ColorId targetPlayerBasicMode;
    private String targetVortexSquareAsString;
    private ColorId target1BlackHoleMode;
    private ColorId target2BlackHoleMode;

    boolean mode;

    public ResponseVortexCannon (ColorId targetPlayerBasicMode, String targetVortexSquareAsString, ColorId target1BlackHoleMode, ColorId target2BlackHoleMode)
    {
        if (targetPlayerBasicMode != null && targetVortexSquareAsString != null)
        {
            this.targetPlayerBasicMode = targetPlayerBasicMode;
            mode = false;
        }

        if (target1BlackHoleMode != null)
        {
            this.target1BlackHoleMode = target1BlackHoleMode;
            mode = true;
            if (target2BlackHoleMode != null)
            {
                this.target2BlackHoleMode = target2BlackHoleMode;
            }
        }
    }

    public ColorId getTargetPlayerBasicMode() {
        return targetPlayerBasicMode;
    }

    public String getTargetVortexSquareAsString() {
        return targetVortexSquareAsString;
    }

    public ColorId getTarget1BlackHoleMode() {
        return target1BlackHoleMode;
    }

    public ColorId getTarget2BlackHoleMode() {
        return target2BlackHoleMode;
    }

    public boolean isMode() {
        return mode;
    }
}