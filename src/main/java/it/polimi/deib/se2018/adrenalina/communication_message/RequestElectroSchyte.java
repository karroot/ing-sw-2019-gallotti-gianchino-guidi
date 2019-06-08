package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;
/**
 * @author gabriele
 */
public class RequestElectroSchyte extends WeaponWithModeAlternative {
    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    private List<ColorId> playersAlternativeMode;//Targets for the basic mode

    //Attribute for the response
    private List<ColorId> targetBasicMode;//Target chosen for the basic mode
    private List<ColorId> targetsAlternativeMode = new LinkedList<>();//Targets chosen for the alternative mode

    /**
     * Create a message of request for the weapon ElectroSchyte
     * @param availableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAlternativeMode targets for the alternative mode
     */
    public RequestElectroSchyte(boolean[] availableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAlternativeMode)
    {
        this.nameAlternaivemode = "modalità mietitore";
        this.availableMethod = availableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAlternativeMode = playerAlternativeMode;
        responseIsReady = false;
    }

    @Override
    protected void inputAlternativeMode() {

        int i = 1; //Variable to cycle


        List<Integer> intchoice = new LinkedList<>();

        System.out.println("Scegli bersagli a cui fare danno:");

        for (ColorId t:playersAlternativeMode) //Print the possible choice
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int inputInt;

        int j = 1;

        while (j <= playersAlternativeMode.size())//Player must choose targets not equals
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

    /**
     *Generate the response message for the Electro Schyte with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseElectroSchyte(targetsAlternativeMode,mode);

        return new ResponseElectroSchyte(targetBasicMode,mode);
    }
}
