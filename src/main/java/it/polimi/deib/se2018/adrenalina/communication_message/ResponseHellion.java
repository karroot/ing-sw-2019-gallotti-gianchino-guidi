package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */

public class ResponseHellion extends ResponseInput
{
    private ColorId targetBasicMode;
    private ColorId targetAlternativeMode;
    boolean mode;

    /**
     * This method will set the targets in the response tp use the weapon.
     *
     */
    ResponseHellion (ColorId targetBasicMode, ColorId targetAlternativeMode)
    {
        if (targetBasicMode != null)
        {
            this.targetBasicMode = targetBasicMode;
            mode = false;
        }

        if (targetAlternativeMode != null)
        {
            this.targetAlternativeMode = targetAlternativeMode;
            mode = true;
        }

    }


    /**
     * This is a public getter for the attribute targetBasicMode
     *
     * @return the attribute targetBasicMode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }


    /**
     * This is a public getter for the attribute targetAlternativeMode
     *
     * @return the attribute targetAlternativeMode
     */
    public ColorId getTargetAlternativeMode()
    {
        return targetAlternativeMode;
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
