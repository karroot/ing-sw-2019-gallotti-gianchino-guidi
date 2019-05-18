package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

/**
 * @author Cysko7927
 */
public class RequestShotgun extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<ColorId> targetsBasicMode;//Targets for the basic mode
    private List<ColorId> targetsAlternativeMode;//Targets for the basic mode

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private ColorId targetAlternativeMode;//Target chosen for the basic mode

    /**
     *Create a message of Request of input for weapon Shotgun
     * @param avaiableMethod Represent the possible mode that can be used
     * @param playersBasicMode All player available for basic mode
     * @param playersAlternativeMode All player available for alternative mode
     */
    public RequestShotgun(boolean[] avaiableMethod, List<ColorId> playersBasicMode, List<ColorId> playersAlternativeMode)
    {
        nameAlternaivemode = "modalità canna lunga";
        this.avaiableMethod = avaiableMethod;
        this.targetsBasicMode = playersBasicMode;
        this.targetsAlternativeMode = playersAlternativeMode;
        responseIsReady = false;
    }


    //Ask at the user to choice a target for the alternative mode
    @Override
    protected void inputAlternativeMode()
    {
        int  k = 1;

        System.out.println("Scegli un bersaglio:");

        for (ColorId b:targetsAlternativeMode)
        {
            System.out.println(k + ":" + b);
            k++;
        }

        int choice = inputInt(1,k-1);

        targetAlternativeMode = targetsAlternativeMode.get(choice-1);
    }

    //Ask at the user to choice a target for the basic mode
    @Override
    protected void inputBasicMode()
    {
        int j = 1;

        System.out.println("Scegli un bersaglio:");

        for (ColorId z :targetsBasicMode)
        {
            System.out.println(j + ":" + z);
            j++;
        }

        int choice = inputInt(1,j-1);

        targetBasicMode = targetsBasicMode.get(choice-1);
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {

        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseShotgun(targetAlternativeMode,mode);

        return new ResponseShotgun(targetBasicMode,mode);
    }
}
