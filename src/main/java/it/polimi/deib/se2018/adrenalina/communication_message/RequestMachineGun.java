package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;

public class RequestMachineGun extends RequestInput {
    //Attribute for the request
    private List<ColorId> playersBasicMode;//target for the basic mode
    private List<ColorId> playersAdditionalMode;//target for the basic mode


    //Attribute for the response
    private ColorId targetBasicModeSecond;//second target chosen for the basic mode
    private ColorId targetAdditionalMode;//target chosen for the first additional mode
    private ColorId targetSecondAdditionalMode;//target chosen for the second additional mode
    private ColorId targetSecondAdditionalModeSecond;//second target chosen for the second additional mode
    
    boolean[] avaiableMethod = new boolean[3];
    protected String nameAdditionalmode; //Name of the alternative mode
    protected String nameSecondAdditionalmode; //Name of the alternative mode
    //Attribute for the response
    protected boolean mode; //Represent if the user choices the basic mode(false) or the alternative mode(true)
    protected boolean secondMode; //Represent if the user choices the basic mode(false) or the alternative mode(true)


    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode


    /**
     * Create a message of request for the weapon MachineGun
     * @param avaiableMethod mode available
     * @param playerBasicMode target for the basic mode
     * @param playerAdditionalMode target for the alternative mode
     */
    public RequestMachineGun(boolean[] avaiableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAdditionalMode)
    {
        this.nameAdditionalmode = "modalità colpo focalizzato";
        this.nameSecondAdditionalmode = "modalità tripode di supporto";
        this.avaiableMethod = avaiableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAdditionalMode = playerAdditionalMode;
        responseIsReady = false;
    }


    @Override
    public void printActionsAndReceiveInput() {
        int choice = 0; //Da completare
        List<Integer> acceptedInt = new LinkedList<>();

        System.out.println("Scegli modalità Arma:");

        if (avaiableMethod[0])//Print the possible effects
        {
            System.out.println("1:modalità base da sola");
            acceptedInt.add(1);
        }
        if (avaiableMethod[1])//Print the possible effects
        {
            System.out.println("2: modalità base con  "+ nameAdditionalmode);
            acceptedInt.add(1);
        }
        if (avaiableMethod[2])//Print the possible effects
        {
            System.out.println("3: modalità base con  "+ nameAdditionalmode + " e " + nameSecondAdditionalmode);
            acceptedInt.add(1);
        }
        int j = 0;

        //Handle the possible choice of the users asking the correct inputs
        if (avaiableMethod[0] && avaiableMethod[1] && avaiableMethod[2])
            choice = inputInt(1, 3);
        if (avaiableMethod[0] && avaiableMethod[1] && !avaiableMethod[2])
            choice = inputInt(1, 2);
        if (avaiableMethod[0] && !avaiableMethod[1] && !avaiableMethod[2])
            choice = inputInt(1, 1);

        mode = false; //Set the attribute mode
        if (choice == 3) //If the user choices the basic mode
        {
            inputSecondAdditionalMode();//Ask all the information necessary to use the alternative mode
            inputAdditionalMode();
            mode= true;//Set the attribute mode
            secondMode = true;//Set the attribute second mode
        }
        else if (choice == 2) //If the user choices the basic mode
        {
            inputAdditionalMode();//Ask all the information necessary to use the alternative mode
            mode = true;//Set the attribute mode
        }

        inputBasicMode();//Ask all the information necessary to use the basic mode





        responseIsReady = true;

    }

    /**
     *Generate the response message for the MachineGun with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
        {
            if (secondMode)
                return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond,targetAdditionalMode,targetSecondAdditionalMode,targetSecondAdditionalModeSecond);
            return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond,targetAdditionalMode);
        }
        if (secondMode)
            return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond,targetSecondAdditionalMode,targetSecondAdditionalModeSecond);
        return new ResponseMachineGun(targetBasicMode,targetBasicModeSecond);
    }




    //Ask at the user to choice three target for the alternative mode if there are



    protected void inputAdditionalMode()
    {
        List<ColorId> players;
        players = playersBasicMode;
        int i = 1;

        for (ColorId t:players)//Ask to user the target
        {
            System.out.println(i+":"+t);
            i++;
        }
        int choice = inputInt(1, i - 1);
        targetBasicMode  = players.get(choice-1);


    }

    protected void inputSecondAdditionalMode()
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

        players = playersBasicMode;
        i=1;
        for (ColorId t:players)//Ask to user the target
        {
            System.out.println(i+":"+t);
            i++;
        }
        choice = inputInt(1, i - 1);
        targetBasicMode  = players.get(choice-1);


    }


    protected void inputBasicMode()
    {
        List<ColorId> players;

        players = playersBasicMode;
        int i = 1;

        for (ColorId t:players)//Ask to user the target
        {
            System.out.println(i+":"+t);
            i++;
        }
        int choice = inputInt(1, i - 1);
        targetBasicMode = players.get(choice-1);

        i = 1;
        for (ColorId t:players)//Ask to user the target
        {
            System.out.println(i+":"+t);
            i++;
        }
        choice = inputInt(1, i - 1);
        targetBasicModeSecond  = players.get(choice-1);
    }
}