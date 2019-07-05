package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseMachineGun extends ResponseInput {
    private ColorId targetBasicMode;//Target for basic mode
    private ColorId targetBasicModeSecond;//second target chosen for the basic mode
    private ColorId targetAdditionalMode;//target chosen for the first additional mode
    private ColorId targetSecondAdditionalMode;//target chosen for the second additional mode
    private ColorId targetSecondAdditionalModeSecond;//second target chosen for the second additional mode
    private boolean mode;//false = player chose the basic mode, true = player chose the first additional mode
   private boolean secondMode;//false = player chose the basic mode, true = player chose the second additional mode
    private boolean addDamage;
    /**
     * Create the response message for MachineGun in basic mode
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicModeSecond;
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
        this.targetBasicModeSecond=targetBasicModeSecond;
        this.targetAdditionalMode = targetAdditionalMode;
        mode = true;
    }

    /**
     * Create the response message for MachineGun in basic mode plus  second additional
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     * @param targetSecondAdditionalMode targets for first additional mode of MachineGun
     * @param targetSecondAdditionalModeSecond target for second additional mode
     * @param addDamage  if additional damage mode is active
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond,ColorId targetSecondAdditionalMode, ColorId targetSecondAdditionalModeSecond, Boolean addDamage)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicModeSecond;
        this.targetSecondAdditionalMode=targetSecondAdditionalMode;
        this.targetSecondAdditionalModeSecond=targetSecondAdditionalModeSecond;
        this.addDamage=addDamage;
        secondMode = true;
    }

    /**
     * Create the response message for MachineGun in basic mode plus first and second additional
     * @param targetBasicMode target for MachineGun
     * @param targetBasicModeSecond second target for MachineGun
     * @param targetAdditionalMode target for additional mode
     * @param targetSecondAdditionalMode targets for first additional mode of MachineGun
     * @param targetSecondAdditionalModeSecond target for second additional mode
     * @param addDamage  if additional damage mode is active
     */
    public ResponseMachineGun(ColorId targetBasicMode, ColorId targetBasicModeSecond,ColorId targetAdditionalMode,ColorId targetSecondAdditionalMode, ColorId targetSecondAdditionalModeSecond,Boolean addDamage)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetBasicModeSecond=targetBasicModeSecond;
        this.targetSecondAdditionalMode=targetSecondAdditionalMode;
        this.targetSecondAdditionalModeSecond=targetSecondAdditionalModeSecond;
        this.addDamage=addDamage;
        this.targetAdditionalMode = targetAdditionalMode;
        mode = true;
        secondMode = true;
    }
    /**
     * target for MachineGun in basic mode
     * @return get the target for MachineGun in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }
    /**
     * second target for MachineGun in basic mode
     * @return get the second target for MachineGun in basic mode
     */
    public ColorId getTargetBasicModeSecond()
    {
        return targetBasicModeSecond;
    }
    /**
     * target for MachineGun in second additional mode
     * @return get the  target for MachineGun in second additional mode
     */
    public ColorId getTargetSecondAdditionalMode() {
        return targetSecondAdditionalMode;
    }

    /**
     *  second target for MachineGun in second additional mode
     * @return get the second target for MachineGun in second additional mode
     */
    public ColorId getTargetSecondAdditionalModeSecond() {
        return targetSecondAdditionalModeSecond;
    }

    /**
     * targets for MachineGun in first additional mode
     * @return get the targets for MachineGun in first additional mode
     */
    public ColorId getTargetAdditionalMode()
    {
        return targetAdditionalMode;
    }

    /**
     * if mode is chosen
     * @return Say which mode chose the user
     */
    public boolean isMode()
    {
        return mode;
    }

    /**
     * if second mode is chosen
     * @return Say which mode chose the user
     */
    public boolean isSecondMode()
    {
        return secondMode;
    }

    /**
     * if add damage is chosen
     * @return Say if user want to add damage
     */
    public boolean isAddDamage() {
        return addDamage;
    }
}
