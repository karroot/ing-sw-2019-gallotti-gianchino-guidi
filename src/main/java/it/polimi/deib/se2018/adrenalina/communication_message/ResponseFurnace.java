package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */

public class ResponseFurnace extends ResponseInput
{
    private ColorRoom targetBasicMode;//Target chosen for the basic mode
    private String targetAlternativeMode;//Target chosen for the alternative mode
    boolean mode;


    /**
     * This method will set the targets in the response to use the basic mode of the weapon.
     *
     */
    ResponseFurnace (ColorRoom targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    /**
     * This method will set the targets in the response to use the alternative mode of the weapon.
     *
     */
    ResponseFurnace (String targetAlternativeMode)
    {
        this.targetAlternativeMode = targetAlternativeMode;
        mode = true;
    }

    /**
     * This is a public getter for the attribute targetBasicMode
     *
     * @return the attribute targetBasicMode
     */
    public ColorRoom getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * This is a public getter for the attribute targetAlternativeMode
     *
     * @return the attribute targetAlternativeMode
     */
    public String getTargetAlternativeMode()
    {
        return targetAlternativeMode;
    }

    /**
     * This is a public getter for the attribute mode
     *
     * @return the attribute mode
     */
    public boolean isMode()
    {
        return mode;
    }
}
