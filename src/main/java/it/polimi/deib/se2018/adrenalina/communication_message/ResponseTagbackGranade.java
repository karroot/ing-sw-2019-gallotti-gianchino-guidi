package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseTagbackGranade extends ResponseInput
{
    private ColorId targetBasicMode;//Target for basic mode
    /**
     * Create the response message for TagbackGranade in basic mode
     * @param targetBasicMode target for LockRifle
     */
    public ResponseTagbackGranade(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;

    }
    /**
     * @return get the target for TagbackGranade in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }
}
