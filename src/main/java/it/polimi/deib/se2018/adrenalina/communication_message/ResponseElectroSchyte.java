package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;
/**
 * This class implements the response for this weapon.
 *
 * @author Karroot
 */
public class ResponseElectroSchyte extends ResponseInput{
    private List<ColorId> targets;//Target for basic mode

    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for ElectroSchyte in basic mode
     * @param targets target for ElectroSchyte
     * @param mode  mode of the weapon
     */
    public ResponseElectroSchyte(List<ColorId> targets, boolean mode)
    {
        this.targets = targets;
        this.mode = mode;
    }


    /**it return all targets for basic mode
     * @return get the target for ElectroSchyte in basic mode
     */
    public List<ColorId> getTargetBasicMode()
    {
        return targets;
    }

    /**it return all targets for alternative mode
     * @return get the targets for ElectroSchyte in alternative mode
     */
    public List<ColorId> getTargetsAlternativeMode()
    {
        return targets;
    }

    /**it return if mode is active
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }
}
