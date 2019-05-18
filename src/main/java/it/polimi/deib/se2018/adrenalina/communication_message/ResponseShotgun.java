package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author Cysko7927
 */
public class ResponseShotgun extends ResponseInput
{
    private ColorId target;//Target for basic mode or alternative mode

    private boolean mode;//false = player chose the basic mode, true = player chose the alternative mode


    /**
     * Create the message of response for the shotgun
     * It Contains all the input and necessary information for using the shotgun
     * @param target target chosen by user to shooting with shotgun
     * @param mode false = player chose the basic mode, true = player chose the alternative mode
     */
    public ResponseShotgun(ColorId target, boolean mode)
    {
        this.target = target;
        this.mode = mode;
    }

    /**
     * @return get the target for Shotgun in basic mode or alternative mode
     */
    public ColorId getTarget()
    {
        return target;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }

}
