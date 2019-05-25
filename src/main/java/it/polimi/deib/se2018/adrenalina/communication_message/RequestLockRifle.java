package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;


import java.util.List;
/**
 * @author gabriele
 */
public class RequestLockRifle extends WeaponWithOneAdditionalEffects {
    //Attribute for the request
    private List<ColorId> playersAdditionalMode;//Targets for the additional mode

    //Attribute for the response
    private ColorId targetAdditionalMode;//Targets chosen for the alternative mode


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
            return new ResponseLockRifle(targetBasicMode , targetAdditionalMode);

        return new ResponseLockRifle(targetBasicMode);
    }


    @Override
    public void printActionsAndReceiveInput() {

        int choice=0;
        System.out.println("Cosa vuoi fare:"); //Ask to user the first effect
        if (avaiableMethod[0])//Print the possible effects
        {
            System.out.println("1: solo attacco base");
        }
        if (avaiableMethod[1])//Print the possible effects
        {
            System.out.println("2:attacco base e aggiunta marchio");
        }
        if (avaiableMethod[1])//Print the possible effects
        {
             choice = inputInt(1, 2);
        }
        else choice = inputInt(1, 1);

        if (choice==2)
        {
            inputAdditionalMode();
        }
        inputBasicMode();
        responseIsReady = true;
    }


    @Override
    protected void inputAdditionalMode()
    {
        List<ColorId> players;
        players = playersAdditionalMode;
        int i = 1;

        for (ColorId t:players)//Ask to user the target
        {
            System.out.println(i+":"+t);
            i++;
        }
        int choice = inputInt(1, i - 1);
        targetAdditionalMode  = players.get(choice-1);
    }



}
