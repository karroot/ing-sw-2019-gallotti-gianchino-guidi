package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class ResponseTractatorBeam extends ResponseInput
{

    private ColorId targetBasicMode;//Target for basic mode
    int x;
    int y;
    private ColorId targetAlternativeMode;//targets for the alternative mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for TractatorBeam in basic mode
     * @param y  coordinate y of the square
     * @param x coordinate x of the square
     * @param targetBasicMode target for TractatorBeam
     */
    public ResponseTractatorBeam(ColorId targetBasicMode,int x,int y)
    {
        this.targetBasicMode = targetBasicMode;
        this.x=x;
        this.y=y;
        mode = false;
    }

    /**
     * Create the response message for TractatorBeam in alternative mode
     * @param targetAlternativeMode target for TractatorBeam
     */
    public ResponseTractatorBeam(ColorId targetAlternativeMode)
    {
        this.targetAlternativeMode = targetAlternativeMode;
        mode = true;
    }

    /**
     * @return get the target for TractatorBeam in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * @return get the targets for TractatorBeam in alternative mode
     */
    public ColorId getTargetAlternativeMode()
    {
        return targetAlternativeMode;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}