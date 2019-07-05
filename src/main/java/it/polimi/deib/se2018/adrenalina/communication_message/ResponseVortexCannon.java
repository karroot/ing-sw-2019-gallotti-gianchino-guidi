package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */
public class ResponseVortexCannon extends ResponseInput
{
    private ColorId targetPlayerBasicMode;
    private String targetVortexSquareAsString;
    private ColorId target1BlackHoleMode;
    private ColorId target2BlackHoleMode;

    boolean mode;

    /**
     * This method will set the targets in the response tp use the weapon.
     *
     */
    ResponseVortexCannon (ColorId targetPlayerBasicMode, String targetVortexSquareAsString, ColorId target1BlackHoleMode, ColorId target2BlackHoleMode)
    {
        if (targetPlayerBasicMode != null && targetVortexSquareAsString != null)
        {
            this.targetPlayerBasicMode = targetPlayerBasicMode;
            this.targetVortexSquareAsString = targetVortexSquareAsString;
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

    /**
     * This is a public getter for the attribute targetPlayerBasicMode
     *
     * @return the attribute targetPlayerBasicMode
     */
    public ColorId getTargetPlayerBasicMode()
    {
        return targetPlayerBasicMode;
    }

    /**
     * This is a public getter for the attribute targetVortexSquareAsString
     *
     * @return the attribute targetVortexSquareAsString
     */
    public String getTargetVortexSquareAsString()
    {
        return targetVortexSquareAsString;
    }

    /**
     * This is a public getter for the attribute target1BlackHoleMode
     *
     * @return the attribute target1BlackHoleMode
     */
    public ColorId getTarget1BlackHoleMode()
    {
        return target1BlackHoleMode;
    }

    /**
     * This is a public getter for the attribute target2BlackHoleMode
     *
     * @return the attribute target2BlackHoleMode
     */
    public ColorId getTarget2BlackHoleMode()
    {
        return target2BlackHoleMode;
    }

    /**
     * This is a public getter for the attribute mode
     *
     * @return the attribute mode
     */
    public boolean isMode()
    {
        return mode;
    }


}
