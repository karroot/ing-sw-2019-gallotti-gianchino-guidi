package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Cysko7927
 */
public class RequestRailgun extends WeaponWithModeAlternative
{
    //Attributes for the request
    private Map<String,List<ColorId>> playersWithCardinalDirections;//Targets for the basic mode or alternative mode

    //Attributes for the response
    private ColorId target1;//Target chosen for the basic mode
    private ColorId target2;//Targets chosen for the alternative mode
    
    //Others attributes
    String direction;
    List<ColorId> players = new ArrayList<>();

    /**
     * Create a message of request for the weapon playersWithCardinalDirections
     * @param availableMethod mode available
     * @param playersWithCardinalDirections Map with the cardinal directions and all players that can be targeted
     */
    public RequestRailgun(boolean[] availableMethod ,Map<String, List<ColorId>> playersWithCardinalDirections)
    {
        this.nameAlternaivemode = "modalità perforazione";
        this.availableMethod = availableMethod;
        this.playersWithCardinalDirections = playersWithCardinalDirections;
        responseIsReady = false;
    }

    //Ask at the user to choice a second target to use the alternative mode of Rail gun
    @Override
    protected void inputAlternativeMode()
    {
        inputBasicMode();

        int j = 1;

        for (ColorId z:players)
        {
            terminal.addOptionInput(j+":"+z);
            j++;
        }

        int choiceTarget = terminal.inputInt(1, j - 1);

        target2 = players.get(choiceTarget - 1);
        players.remove(choiceTarget - 1);

    }

    //Ask at the user to choice a cardinal direction and a target for the basic mode
    @Override
    protected void inputBasicMode()
    {
        terminal.addTextInput("Scegli una direzione cardinale:");

        List<String> choicesDirections = new ArrayList<>(playersWithCardinalDirections.keySet());

        int i = 1;

        for (String t:choicesDirections)
        {
            terminal.addOptionInput(i+":"+t);
            i++;
        }

        int choiceDirection = terminal.inputInt(1, i - 1);

        players = playersWithCardinalDirections.get(choicesDirections.get(choiceDirection - 1));

        i = 1;

        for (ColorId z:players)
        {
            terminal.addOptionInput(i+":"+z);
            i++;
        }

        int choiceTarget = terminal.inputInt(1, i - 1);

        target1 = players.get(choiceTarget - 1);
        players.remove(choiceTarget - 1);

    }

    /**
     * Generate the response message for the Railgun with all user's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseRailgun(target1,target2);

        return new ResponseRailgun(target1);
    }
}
