package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Cysko7927
 */
public class ResponseZX2 extends ResponseInput
{

    private ColorId targetBasicMode;//Target for basic mode
    private List<ColorId> targetsAlternativeMode;//targets for the alternative mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for ZX2 in basic mode
     * @param targetBasicMode target for ZX2
     */
    public ResponseZX2(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    /**
     * Create the response message for ZX2 in alternative mode
     * @param targetsAlternativeMode targets for ZX2
     */
    public ResponseZX2(List<ColorId> targetsAlternativeMode)
    {
        this.targetsAlternativeMode = targetsAlternativeMode;
        mode = true;
    }

    /**
     * get the target for ZX2 in basic mode
     * @return
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * get the targets for ZX2 in alternative mode
     * @return
     */
    public List<ColorId> getTargetsAlternativeMode()
    {
        return targetsAlternativeMode;
    }

    /**
     * Say which mode chose the user
     * @return
     */
    public boolean isMode()
    {
        return mode;
    }
}
