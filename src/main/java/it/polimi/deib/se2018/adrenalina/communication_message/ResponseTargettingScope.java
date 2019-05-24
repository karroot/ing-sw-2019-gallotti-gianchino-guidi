package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class ResponseTargettingScope extends ResponseInput
{
    private ColorId targetBasicMode;//Target for basic mode
    //Attribute for the request


    /**
     * Create the response message for TargettingScope in basic mode
     * @param targetBasicMode target for LockRifle
     */
    public ResponseTargettingScope(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;

    }
    /**
     * @return get the target for TargettingScope in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }
}
