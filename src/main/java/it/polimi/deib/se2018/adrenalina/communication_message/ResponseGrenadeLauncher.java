package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author giovanni
 */
public class ResponseGrenadeLauncher extends ResponseInput
{
    private ColorId targetBasicMode;
    private String targetSquareToMoveBasicModeAsString;
    private String targetSquareExtraGrenadeAsString;
    boolean extraEffect = false;

    public ResponseGrenadeLauncher(ColorId targetBasicMode, String targetSquareToMoveBasicModeAsString, String targetSquareExtraGrenadeAsString)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetSquareToMoveBasicModeAsString = targetSquareToMoveBasicModeAsString;
        if (targetSquareExtraGrenadeAsString != null)
        {
            this.targetSquareExtraGrenadeAsString = targetSquareExtraGrenadeAsString;
            extraEffect = true;
        }
    }

    public ColorId getTargetBasicMode() {
        return targetBasicMode;
    }

    public String getTargetSquareToMoveBasicModeAsString() {
        return targetSquareToMoveBasicModeAsString;
    }

    public String getTargetSquareExtraGrenadeAsString() {
        return targetSquareExtraGrenadeAsString;
    }

    public boolean isExtraEffect() {
        return extraEffect;
    }
}
