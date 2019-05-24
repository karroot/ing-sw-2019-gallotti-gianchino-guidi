package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseMachineGun extends ResponseInput {
    private ColorId targetBasicMode;//Target for basic mode
    private ColorId targetBasicModeSecond;//second target chosen for the basic mode
    private ColorId targetAdditionalMode;//target chosen for the first additional mode
    private ColorId targetSecondAdditionalMode;//target chosen for the second additional mode
    private ColorId targetSecondAdditionalModeSecond;//second target chosen for the second additional mode
    boolean mode;//false = player chose the basic mode, true = player chose the first additional mode
    boolean secondMode;//false = player chose the basic mode, true = player chose the second additional mode

    /**
     * Create the response message for MachineGun in basic mode
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicMode;
        mode = false;
    }

    /**
     * Create the response message for MachineGun in basic mode plus additional
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     * @param targetAdditionalMode targets for first additional mode of MachineGun
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond,ColorId targetAdditionalMode)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicMode;
        this.targetAdditionalMode = targetAdditionalMode;
        mode = true;
    }

    /**
     * Create the response message for MachineGun in basic mode plus  second additional
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     * @param targetSecondAdditionalMode targets for first additional mode of MachineGun
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond,ColorId targetSecondAdditionalMode, ColorId targetSecondAdditionalModeSecond)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicMode;
        this.targetSecondAdditionalMode=targetSecondAdditionalMode;
        this.targetSecondAdditionalModeSecond=targetSecondAdditionalModeSecond;

        secondMode = true;
    }

    /**
     * Create the response message for MachineGun in basic mode plus first and second additional
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     * @param targetSecondAdditionalMode targets for first additional mode of MachineGun
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond,ColorId targetAdditionalMode,ColorId targetSecondAdditionalMode, ColorId targetSecondAdditionalModeSecond)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicMode;
        this.targetSecondAdditionalMode=targetSecondAdditionalMode;
        this.targetSecondAdditionalModeSecond=targetSecondAdditionalModeSecond;
        this.targetAdditionalMode = targetAdditionalMode;
        mode = true;
        secondMode = true;
    }
    /**
     * @return get the target for MachineGun in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }
    /**
     * @return get the second target for MachineGun in basic mode
     */
    public ColorId getTargetBasicModeSecond()
    {
        return targetBasicModeSecond;
    }
    /**
     * @return get the  target for MachineGun in second additional mode
     */
    public ColorId getTargetSecondAdditionalMode() {
        return targetSecondAdditionalMode;
    }
    /**
     * @return get the second target for MachineGun in second additional mode
     */
    public ColorId getTargetSecondAdditionalModeSecond() {
        return targetSecondAdditionalModeSecond;
    }

    /**
     * @return get the targets for MachineGun in first additional mode
     */
    public ColorId getTargetAdditionalMode()
    {
        return targetAdditionalMode;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }

    /**
     * @return Say which mode chose the user
     */
    public boolean isSecondMode()
    {
        return secondMode;
    }
}
