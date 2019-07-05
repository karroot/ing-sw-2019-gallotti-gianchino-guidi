package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
/**
 * @author gabriele
 */
public class ResponseLockRifle extends ResponseInput {
    private ColorId targetBasicMode=null;//Target for basic mode
    private ColorId targetsAdditionalMode=null;//targets for the alternative mode
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
        this.targetBasicMode = targetBasicMode;
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

    /**targets for LockRifle in alternative mode
     * @return get the targets for LockRifle in alternative mode
     */
    public ColorId getTargetsAdditionalMode()
    {
        return targetsAdditionalMode;
    }

    /** Say which mode chose the user
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}
