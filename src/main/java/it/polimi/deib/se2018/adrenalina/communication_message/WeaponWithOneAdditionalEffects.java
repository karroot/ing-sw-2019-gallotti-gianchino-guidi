package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.LinkedList;
import java.util.List;
/**
 * This is a general class that will implements a request of input
 * for a weapon with an additional mode
 * @author Cysko7927
 */
public abstract class WeaponWithOneAdditionalEffects extends RequestInput
{
    //Attribute for the request
    protected List<ColorId> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode


    boolean[] availableMethod = new boolean[2];
    protected String nameAdditionalmode; //Name of the alternative mode

    //Attribute for the response
    protected boolean mode; //Represent if the user choices the basic mode(false) or the alternative mode(true)

    /**
     * Method that handles the inputs for the use of a weapon with an additional mode
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    { this.terminal=terminal;
        int choice = 0; //Da completare
        List<Integer> acceptedInt = new LinkedList<>();

        terminal.addTextInput("Scegli modalità Arma:");

        if (availableMethod[0])//Print the possible effects
        {
            terminal.addOptionInput("1:modalità base da sola");
            acceptedInt.add(1);
        }
        if (availableMethod[1])//Print the possible effects
        {
            terminal.addOptionInput("2: modalità base con  "+ nameAdditionalmode);
            acceptedInt.add(1);
        }

        int j = 0;

        //Handle the possible choice of the users asking the correct inputs
        if (availableMethod[0] && availableMethod[1])
            choice = terminal.inputInt(1, 2);

        if (availableMethod[0] && !availableMethod[1])
            choice = terminal.inputInt(1, 1);

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

        terminal.addTextInput("Scegli un bersaglio:");

        for (ColorId t:playersBasicMode)
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        int anInt = terminal.inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(anInt -1);

    }



}
