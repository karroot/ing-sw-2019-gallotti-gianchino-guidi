package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 *
 *
 * @author gioguidi
 */

public class ResponseHellion extends ResponseInput
{
    private ColorId targetBasicMode;
    private ColorId targetAlternativeMode;
    boolean mode;

    /**
     *
     * @param targetBasicMode
     * @param targetAlternativeMode
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


    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }


    public ColorId getTargetAlternativeMode()
    {
        return targetAlternativeMode;
    }


    public boolean isMode()
    {
        return mode;
    }
}
