package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;



public class ResponseWhisper extends ResponseInput {
    private ColorId targetBasicMode;//Target for basic mode


    /**
     * Create the response message for Whisper in basic mode
     * @param targetBasicMode target for Whisper
     */
    public ResponseWhisper(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;

    }


    /**
     * get the target for Whisper in basic mode
     * @return get the target for Whisper in basic mode
     */
    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

}
