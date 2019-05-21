package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class ResponseElectroSchyte extends ResponseInput{
    private ColorId targetBasicMode;//Target for basic mode
    private List<ColorId> targetsAlternativeMode;//targets for the alternative mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for ElectroSchyte in basic mode
     * @param targetBasicMode target for ElectroSchyte
     */
    public ResponseElectroSchyte(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    /**
     * Create the response message for ElectroSchyte in alternative mode
     * @param targetsAlternativeMode targets for ElectroSchyte
     */
    public ResponseElectroSchyte(List<ColorId> targetsAlternativeMode)
    {
        this.targetsAlternativeMode = targetsAlternativeMode;
        mode = true;
    }

    /**
     * @return get the target for ElectroSchyte in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * @return get the targets for ElectroSchyte in alternative mode
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
