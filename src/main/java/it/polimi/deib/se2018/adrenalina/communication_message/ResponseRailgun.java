package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author Cysko7927
 */
public class ResponseRailgun extends ResponseInput
{
    private ColorId target1;//Target chosen for the basic mode and also for the alternative mode in other cases
    private ColorId target2 = null;//Targets chosen for the alternative mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for Railgun in alternative mode
     * @param target1 first target for Railgun
     * @param target2 second target for Railgun
     */
    public ResponseRailgun(ColorId target1, ColorId target2)
    {
        this.target1 = target1;
        this.target2 = target2;
        this.mode = true;
    }
    /**
     * Create the response message for Railgun in basic mode
     * @param target1 target for Railgun
     */
    public ResponseRailgun(ColorId target1)
    {
        this.target1 = target1;
        this.mode = false;
    }

    /**
     * @return get the target for Railgun in basic mode or alternative mode
     */
    public ColorId getTarget1()
    {
        return target1;
    }

    /**
     * @return get the second target for Railgun for the alternative mode
     */
    public ColorId getTarget2()
    {
        return target2;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}
