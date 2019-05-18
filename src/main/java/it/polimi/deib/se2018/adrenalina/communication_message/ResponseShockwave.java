package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Cysko7927
 */
public class ResponseShockwave extends ResponseInput
{
    private List<ColorId> targetsBasicMode = new LinkedList<>();//Target chosen for the basic mode
    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for Shockwave in basic mode
     * @param targetsBasicMode targets for the Shockwave
     */
    public ResponseShockwave(List<ColorId> targetsBasicMode)
    {
        this.targetsBasicMode = targetsBasicMode;
        mode = false;
    }

    /**
     * Create the response message for Shockwave in alternative mode
     */
    public ResponseShockwave()
    {
        mode = true;
    }

    /**
     * @return get the target for ZX2 in basic mode
     */
    public List<ColorId> getTargetsBasicMode()
    {
        return targetsBasicMode;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}
