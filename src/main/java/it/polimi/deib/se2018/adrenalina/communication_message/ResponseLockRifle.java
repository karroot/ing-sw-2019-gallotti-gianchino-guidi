package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;
/**
 * @author gabriele
 */
public class ResponseLockRifle extends ResponseInput {
    private ColorId targetBasicMode;//Target for basic mode
    private ColorId targetsAdditionalMode;//targets for the alternative mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for LockRifle in basic mode
     * @param targetBasicMode target for LockRifle
     */
    public ResponseLockRifle(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    /**
     * Create the response message for LockRifle in alternative mode
     * @param targetsAdditionalMode targets for LockRifle
     */
    public ResponseLockRifle(ColorId targetBasicMode,ColorId targetsAdditionalMode)
    {
        this.targetsAdditionalMode = targetsAdditionalMode;
        mode = true;
    }

    /**
     * @return get the target for LockRifle in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * @return get the targets for LockRifle in alternative mode
     */
    public ColorId getTargetsAdditionalMode()
    {
        return targetsAdditionalMode;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}
