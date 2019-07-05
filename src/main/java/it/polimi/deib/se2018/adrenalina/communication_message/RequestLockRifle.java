package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.LinkedList;
import java.util.List;
/**
 *   This class represents the request that the conroller sends at the client if the player decided
 *   to use the weapon LockRifle
 * @author Karroot
 */
public class RequestLockRifle extends WeaponWithOneAdditionalEffects {
    //Attribute for the request
    private List<ColorId> playersAdditionalMode;//Targets for the additional mode

    //Attribute for the response
    private ColorId targetAdditionalMode;//Targets chosen for the alternative mode


    /**
     * Create a message of request for the weapon LockRifle
     * @param availableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAdditionalMode targets for the alternative mode
     */
    public RequestLockRifle(boolean[] availableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAdditionalMode)
    {
        this.nameAdditionalmode = "modalità secondo aggancio";
        this.availableMethod = availableMethod;
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

    /**
     * this method will print all the available actions and ask the player to respond to requests
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        this.terminal=terminal;

        int choice=0;

        terminal.addTextInput("Cosa vuoi fare:"); //Ask to user the first effect
        if (availableMethod[0])//Print the possible effects
        {
            terminal.addOptionInput("1: solo attacco base");
        }
        if (availableMethod[1])//Print the possible effects
        {
            terminal.addOptionInput("2:attacco base e aggiunta marchio");
        }
        if (availableMethod[1])//Print the possible effects
        {
             choice = terminal.inputInt(1, 2);
        }
        else choice = terminal.inputInt(1, 1);

        inputBasicMode();
        if (choice==2)
        {
            inputAdditionalMode();
            mode=true;
        }

        responseIsReady = true;
    }


    @Override
    protected void inputAdditionalMode()
    {
        List<ColorId> players;
        players = playersAdditionalMode;
        List<ColorId> showedPlayers= new LinkedList<>();
        int i = 1;
        terminal.addTextInput("Scegli chi altro vuoi marchiare:");
        for (ColorId t:players)//Ask to user the target
        {
            if(!t.equals(targetBasicMode))  {
                terminal.addOptionInput(i + ":" + t);
                showedPlayers.add(t);
                i++;
            }
        }
        int choice = terminal.inputInt(1, i - 1);
        targetAdditionalMode  = showedPlayers.get(choice-1);
    }

@Override
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

