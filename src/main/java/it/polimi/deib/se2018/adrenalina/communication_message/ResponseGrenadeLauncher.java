package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */
public class ResponseGrenadeLauncher extends ResponseInput
{
    private ColorId targetBasicMode;
    private String targetSquareToMoveBasicModeAsString;
    private String targetSquareExtraGrenadeAsString;
    private boolean extraEffect = false;

    /**
     * This method will set the targets in the response to use the weapon.
     *
     */
    ResponseGrenadeLauncher(ColorId targetBasicMode, String targetSquareToMoveBasicModeAsString, String targetSquareExtraGrenadeAsString)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetSquareToMoveBasicModeAsString = targetSquareToMoveBasicModeAsString;
        if (targetSquareExtraGrenadeAsString != null)
        {
            this.targetSquareExtraGrenadeAsString = targetSquareExtraGrenadeAsString;
            extraEffect = true;
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
     * This is a public getter for the attribute targetSquareToMoveBasicModeAsString
     *
     * @return the attribute targetSquareToMoveBasicModeAsString
     */
    public String getTargetSquareToMoveBasicModeAsString()
    {
        return targetSquareToMoveBasicModeAsString;
    }

    /**
     * This is a public getter for the attribute targetSquareExtraGrenadeAsString
     *
     * @return the attribute targetSquareExtraGrenadeAsString
     */
    public String getTargetSquareExtraGrenadeAsString()
    {
        return targetSquareExtraGrenadeAsString;
    }

    /**
     * This is a public getter for the attribute extraEffect
     *
     * @return the attribute extraEffect
     */
    public boolean isExtraEffect()
    {
        return extraEffect;
    }

}
