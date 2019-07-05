package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseTargettingScope extends ResponseInput
{
    private ColorId targetBasicMode;//Target for basic mode
    //Attribute for the request
    private Color targetAmmo;

    /**
     * Create the response message for TargettingScope in basic mode
     * @param targetBasicMode target for targetting scope
     * @param targetAmmo color of the ammo to use
     */
    public ResponseTargettingScope(ColorId targetBasicMode, Color targetAmmo)
    {
        this.targetBasicMode = targetBasicMode;
        this.targetAmmo= targetAmmo;
    }



    public Color getTargetAmmo() {
        return targetAmmo;
    }

    /**
     * get the target for TargettingScope in basic mode
     * @return the target for TargettingScope in basic mode
     */



    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }
}
