package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;

public abstract class WeaponWithOneAdditionalEffects extends RequestInput
{
    //Attribute for the request
    protected List<ColorId> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode


    boolean[] avaiableMethod = new boolean[2];
    protected String nameAdditionalmode; //Name of the alternative mode

    //Attribute for the response
    protected boolean mode; //Represent if the user choices the basic mode(false) or the alternative mode(true)

    @Override
    public void printActionsAndReceiveInput()
    {
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

        int j = 0;

        //Handle the possible choice of the users asking the correct inputs
        if (avaiableMethod[0] && avaiableMethod[1])
            choice = inputInt(1, 2);

        if (avaiableMethod[0] && !avaiableMethod[1])
            choice = inputInt(1, 1);

        inputBasicMode();//Ask all the information necessary to use the basic mode
        mode = false; //Set the attribute mode

        if (choice == 2) //If the user choices the basic mode
        {
            inputAdditionalMode();//Ask all the information necessary to use the alternative mode
            mode = true;//Set the attribute mode
        }


        responseIsReady = true;

    }
    protected abstract void inputAdditionalMode();
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



}
