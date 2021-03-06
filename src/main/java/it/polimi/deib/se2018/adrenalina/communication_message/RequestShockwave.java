package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the request that the controller sends at the client if the player decided
 * to use the weapon Shockwave
 * @author Cysko7927
 */
public class RequestShockwave extends WeaponWithModeAlternative
{
    //Attribute for the request
    private Map<String,List<ColorId>> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    private List<ColorId> targetsBasicMode = new LinkedList<>();//Targets chosen for the basic mode

    /**
     * Create a message of request for the weapon Shockwave
     * @param playersBasicMode targets available for the basic mode
     * @param availableMethod array of booleans for the available mode
     */
    public RequestShockwave(boolean[] availableMethod, Map<String,List<ColorId>> playersBasicMode)
    {
        this.nameAlternaivemode = "modalità tsunami";
        this.availableMethod = availableMethod;
        this.playersBasicMode = playersBasicMode;
        responseIsReady = false;
    }

    @Override
    protected void inputAlternativeMode()
    {
        //The player chooses only the mode and there is nothing input to take because this mode didn't need
    }

    //Do to choose at user max three player to use the basic mode of Shockwave
    @Override
    protected void inputBasicMode()
    {
        for (String t:playersBasicMode.keySet())
        {
            terminal.addTextInput("Scegli un bersaglio che è nel quadrato con coordinate:"+t);

            int i = 1;

            for (ColorId c:playersBasicMode.get(t))
            {
                terminal.addOptionInput(i+":"+c);
                i++;
            }

            int anInt = terminal.inputInt(1, i - 1);

            targetsBasicMode.add(playersBasicMode.get(t).get(anInt));
        }

    }

    /**
     * Generate the response message for the shockwave with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseShockwave();

        return new ResponseShockwave(targetsBasicMode);
    }
}
