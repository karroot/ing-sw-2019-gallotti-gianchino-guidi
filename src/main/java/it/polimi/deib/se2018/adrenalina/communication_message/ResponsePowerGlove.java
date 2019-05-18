package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class ResponsePowerGlove extends ResponseInput
{
    private ColorId targetBasicMode;//Target for basic mode
    private List<ColorId> targetsAlternativeMode;//targets for the alternative mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for Powerglove in basic mode
     * @param targetBasicMode target for PowerGlove
     */
    public ResponsePowerGlove(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        this.mode = false;
    }

    /**
     * Create the response message for Powerglove in alternative mode
     * @param targetsAlternativeMode targets for Powerglove
     */
    public ResponsePowerGlove(List<ColorId> targetsAlternativeMode)
    {
        this.targetsAlternativeMode = targetsAlternativeMode;
        this.mode = true;
    }

    /**
     * @return get the target for Powerglove in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * @return get the targets for Powerglove in alternative mode
     */
    public List<ColorId> getTargetsAlternativeMode()
    {
        return targetsAlternativeMode;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}
