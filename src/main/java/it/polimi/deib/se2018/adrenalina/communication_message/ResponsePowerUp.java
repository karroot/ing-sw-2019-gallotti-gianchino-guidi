package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponsePowerUp extends ResponseInput {
    private int targetBasicMode;//Target for basic mode

    public ResponsePowerUp(int targetBasicMode) {
        this.targetBasicMode = targetBasicMode;
    }

    /**
     * @return get the index of the chosen powerup
     */
    public int getTargetBasicMode()
    {
        return targetBasicMode;
    }
}
