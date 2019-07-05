package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */
public class ResponseHeatSeeker extends ResponseInput
{
    private ColorId targetBasicMode;


    /**
     * This method will set the targets in the response tp use the weapon.
     *
     */
    ResponseHeatSeeker(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
    }


    /**
     * This is a public getter for the attribute targetBasicMode
     *
     * @return the attribute targetBasicMode
     */
    public ColorId getTagetBasicMode()
    {
        return targetBasicMode;
    }

}
