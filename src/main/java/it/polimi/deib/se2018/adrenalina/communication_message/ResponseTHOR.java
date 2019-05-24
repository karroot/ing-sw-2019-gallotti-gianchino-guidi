package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class ResponseTHOR extends ResponseInput {
    private ColorId targetBasicMode;//Target for basic mode
    private ColorId targetAlternativeMode;//target for the alternative mode
    private ColorId targetSecondAlternativeMode;//target for the alternative mode

    boolean mode;//false = player chose the basic mode, true = player chose the alternative mode
    boolean secondMode;//false = player chose the basic mode, true = player chose the alternative mode

    /**
     * Create the response message for THOR in basic mode
     * @param targetBasicMode target for THOR
     */
    public ResponseTHOR(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    /**
     * Create the response message for THOR in alternative mode
     * @param targetAdditionalMode target for THOR
     */
    public ResponseTHOR(ColorId targetBasicMode,ColorId targetAdditionalMode)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetAlternativeMode = targetAlternativeMode;
        mode = true;
    }
    /**
     * Create the response message for THOR in alternative mode
     * @param targetSecondAdditionalMode target for THOR
     */
    public ResponseTHOR(ColorId targetBasicMode,ColorId targetAdditionalMode,ColorId targetSecondAdditionalMode)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetAlternativeMode = targetAlternativeMode;
        this.targetSecondAlternativeMode = targetSecondAlternativeMode;
        secondMode = true;
    }

    /**
     * @return get the target for THOR in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    /**
     * @return get the target for THOR in additional mode
     */
    public ColorId gettargetAdditionalMode()
    {
        return targetAlternativeMode;
    }

    /**
     * @return get the target for THOR in second additional mode
     */
    public ColorId gettargetSecondAdditionalMode()
    {
        return targetSecondAlternativeMode;
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
