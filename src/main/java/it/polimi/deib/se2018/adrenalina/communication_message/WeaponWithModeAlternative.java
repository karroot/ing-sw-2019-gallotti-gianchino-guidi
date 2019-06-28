package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

/**
 * @author Cysko7927
 */
public abstract class WeaponWithModeAlternative extends RequestInput
{

    //Attribute for the request
    protected boolean[] availableMethod; //Represent which mode are possible
    protected String nameAlternaivemode; //Name of the alternative mode

    //Attribute for the response
    protected boolean mode; //Represent if the user choices the basic mode(false) or the alternative mode(true)
    //Attribute for the request
    protected List<ColorId> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode



    /**
     * Method that handles the inputs for the use of a weapon with two alternative mode
     * @param terminal
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    { this.terminal=terminal;
        int choice = 0;
        //Print the possible mode of the weapon

        terminal.addTextInput("Scegli modalità Arma:");

        if (availableMethod[0])
        {
            terminal.addOptionInput("1:modalità base");
        }

        if (availableMethod[1])//If the alternative mode can be used
        {
            terminal.addOptionInput("2:"+nameAlternaivemode);
        }

        int j = 0;

        //Handle the possible choice of the users asking the correct inputs
        if (availableMethod[0] && availableMethod[1])
            choice = terminal.inputInt(1, 2);

        if (!availableMethod[0] && availableMethod[1])
        {
            choice = terminal.inputInt(2, 2);
            if (choice == 1)
                choice = 2;
        }

        if (availableMethod[0] && !availableMethod[1])
            choice = terminal.inputInt(1, 1);


        if (choice == 1) //If the user choices the basic mode
        {
            inputBasicMode();//Ask all the information necessary to use the basic mode
            mode = false; //Set the attribute mode
        }
        else
        {
            inputAlternativeMode();//Ask all the information necessary to use the alternative mode
            mode = true;//Set the attribute mode
        }

        responseIsReady = true;

    }

    //Methods for to obtain all necessary information to use the two modes and built the response message
    protected abstract void inputAlternativeMode();
    //Ask at the user one target to hit with the weapon
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
