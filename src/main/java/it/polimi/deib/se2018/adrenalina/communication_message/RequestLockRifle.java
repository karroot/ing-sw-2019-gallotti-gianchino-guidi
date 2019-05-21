package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;
/**
 * @author gabriele
 */
public class RequestLockRifle extends WeaponWithOneAdditionalEffects {
    //Attribute for the request
    private List<ColorId> playersAdditionalMode;//Targets for the basic mode

    //Attribute for the response
    private List<ColorId> targetsAdditionalMode= new LinkedList<>();//Targets chosen for the alternative mode

    /**
     * Create a message of request for the weapon LockRifle
     * @param avaiableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAdditionalMode targets for the alternative mode
     */
    public RequestLockRifle(boolean[] avaiableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAdditionalMode)
    {
        this.nameAdditionalmode = "modalità secondo aggancio";
        this.avaiableMethod = avaiableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAdditionalMode = playerAdditionalMode;
        responseIsReady = false;
    }


    /**
     *Generate the response message for the LockRifle with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseLockRifle(targetsAdditionalMode);

        return new ResponseLockRifle(targetBasicMode);
    }




    //Ask at the user to choice three target for the alternative mode if there are
    @Override
    protected void inputAdditionalMode()
    {

        int i = 1; //Variable to cycle


        List<Integer> intchoice = new LinkedList<>();

        System.out.println("Scegli un bersaglio da marchiare:");

        for (ColorId t:playersAdditionalMode) //Print the possible choice
        {
            System.out.println(i + ":" + t);
            i++;
        }
         targetsAdditionalMode.add(playersAdditionalMode.get(0));


        }

    @Override
    protected void inputBasicMode() {

    }

}

