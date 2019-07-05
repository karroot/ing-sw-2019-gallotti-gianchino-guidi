package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseTagbackGranade extends ResponseInput
{
    private ColorId targetBasicMode;//Target for basic mode
    /**
     * Create the response message for TagbackGranade
     * @param targetBasicMode target for granade
     */
    public ResponseTagbackGranade(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;

    }
    /**get the target for TagbackGranade
     * @return the target for TagbackGranade
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }
}
