package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author giovanni
 */
public class ResponseHeatSeeker extends ResponseInput
{
    private ColorId targetBasicMode;

    public ResponseHeatSeeker(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
    }

    public ColorId getTagetBasicMode() {
        return targetBasicMode;
    }
}
