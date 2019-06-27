package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestPowerGlove extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    private Map<String,List<List<ColorId>>> playersAlternativeMode;//Targets for the basic mode

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private List<ColorId> targetsAlternativeMode = new LinkedList<>();//Targets chosen for the alternative mode

    /**
     * Create a message of request for the weapon Powerglove
     * @param availableMethod mode available
     * @param playersBasicMode targets for the basic mode
     * @param playersAlternativeMode targets for the alternative mode
     */
    public RequestPowerGlove(boolean[] availableMethod, List<ColorId> playersBasicMode, Map<String, List<List<ColorId>>> playersAlternativeMode)
    {
        this.availableMethod = availableMethod;
        this.nameAlternaivemode = "modalità cento pugni";
        this.playersBasicMode = playersBasicMode;
        this.playersAlternativeMode = playersAlternativeMode;
        responseIsReady = false;
    }

    //Ask at the user to choice the two targets for the alternative mode of PowerGlove
    @Override
    protected void inputAlternativeMode()
    {
        List<String> directionPossible = new ArrayList<>(playersAlternativeMode.keySet());

        terminal.addTextInput("Scegli una direzione:");

        int i = 1;

        for (String t:directionPossible)
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        int chose = terminal.inputInt(1,i-1);

        List<List<ColorId>> temp = playersAlternativeMode.get(directionPossible.get(chose - 1));

        terminal.addTextInput("Scegli un player per il primo spostamento");

        i = 1;

        for (ColorId t:temp.get(0))
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        chose = terminal.inputInt(1,i-1);

        targetsAlternativeMode.add(temp.get(0).get(chose));

        terminal.addTextInput("Scegli un player per il secondo spostamento");

        i = 1;

        for (ColorId t:temp.get(1))
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        chose = terminal.inputInt(1,i-1);

        targetsAlternativeMode.add(temp.get(1).get(chose));

    }

    //Ask at the user to choice a target for the basic mode of Powerglove
    @Override
    protected void inputBasicMode()
    {
        int l = 1;

        terminal.addTextInput("Scegli un bersaglio:");

        for (ColorId a:playersBasicMode)
        {
            terminal.addOptionInput(l + ":" + a);
            l++;
        }

        int chose = terminal.inputInt(1, l - 1);

        targetBasicMode = playersBasicMode.get(chose -1);
    }

    /**
     *Generate the response message for the PowerGlove with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponsePowerGlove(targetsAlternativeMode);

        return new ResponsePowerGlove(targetBasicMode);
    }
}
