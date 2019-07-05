package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;

/**
 * @author gioguidi
 */

public class ResponseFurnace extends ResponseInput
{
    private ColorRoom targetBasicMode;//Target chosen for the basic mode
    private String targetAlternativeMode;//Target chosen for the alternative mode
    boolean mode;


    ResponseFurnace (ColorRoom targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    ResponseFurnace (String targetAlternativeMode)
    {
        this.targetAlternativeMode = targetAlternativeMode;
        mode = true;
    }


    public ColorRoom getTargetBasicMode()
    {
        return targetBasicMode;
    }


    public String getTargetAlternativeMode()
    {
        return targetAlternativeMode;
    }


    public boolean isMode()
    {
        return mode;
    }
}
