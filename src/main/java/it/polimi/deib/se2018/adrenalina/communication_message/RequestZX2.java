package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Cysko7927
 */
public class RequestZX2 extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    private List<ColorId> playersAlternativeMode;//Targets for the basic mode

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private List<ColorId> targetsAlternativeMode = new LinkedList<>();//Targets chosen for the alternative mode

    /**
     * Create a message of request for the weapon ZX2
     * @param avaiableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAlternativeMode targets for the alternative mode
     */
    public RequestZX2(boolean[] avaiableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAlternativeMode)
    {
        this.nameAlternaivemode = "modalità scanner";
        this.avaiableMethod = avaiableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAlternativeMode = playerAlternativeMode;
        responseIsReady = false;
    }


    /**
     *Generate the response message for the ZX2 with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseZX2(targetsAlternativeMode);

        return new ResponseZX2(targetBasicMode);
    }

    //Ask at the user to choice a target for the basic mode
    @Override
    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un bersaglio:");

        for (ColorId t:playersBasicMode)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(anInt -1);

    }

    //Ask at the user to choice three target for the alternative mode if there are
    @Override
    protected void inputAlternativeMode()
    {

        int i = 1; //Variable to cycle


        List<Integer> intchoice = new LinkedList<>();

        System.out.println("Scegli tre bersagli da marchiare:");

        for (ColorId t:playersAlternativeMode) //Print the possible choice
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int inputInt;

        int j = 1;

        while (j<=3 || j <= playersAlternativeMode.size())//Player must choose three target not equals
        {
            inputInt = inputInt(1, playersAlternativeMode.size());//Ask a target

            while (intchoice.contains(inputInt)) //if the target was chosen before
            {
                inputInt = inputInt(1, playersAlternativeMode.size());//Ask a new target
            }

            intchoice.add(inputInt); //Add the target in the list of targets
            targetsAlternativeMode.add(playersAlternativeMode.get(inputInt-1));

            j++;

        }

    }
}