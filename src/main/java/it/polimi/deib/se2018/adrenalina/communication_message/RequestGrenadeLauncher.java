package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author giovanni
 */
public class RequestGrenadeLauncher extends WeaponWithOneAdditionalEffects
{
    //Attribute for the request
    private  HashMap<ColorId, String> playerBasicMode;
    private List<String> squaresExtraGrenadeAsString;
    private HashMap<ColorId, List<String>> hashMapToMovePlayers;
    private List<String> allSquaresPlayerSees;

    //Attribute for the response
    private ColorId targetBasicMode;
    private String targetSquareToMoveBasicModeAsString = null;
    private String targetSquareExtraGrenadeAsString;

    private boolean move;



    public RequestGrenadeLauncher(boolean[] availableMethod,  HashMap<ColorId, String> playerBasicMode, List<String> squaresExtraGrenadeAsString, HashMap<ColorId, List<String>> hashMapToMovePlayers, List<String> allSquaresPlayerSees)
    {
        this.nameAdditionalmode = "modalità granata extra";
        this.availableMethod = availableMethod;
        this.playerBasicMode = playerBasicMode;
        this.hashMapToMovePlayers = hashMapToMovePlayers;
        this.squaresExtraGrenadeAsString = squaresExtraGrenadeAsString;
        this.allSquaresPlayerSees = allSquaresPlayerSees;
        responseIsReady = false;
    }


    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
        {
            if (move)
                return new ResponseGrenadeLauncher(targetBasicMode , targetSquareToMoveBasicModeAsString, targetSquareExtraGrenadeAsString);

            return new ResponseGrenadeLauncher(targetBasicMode , null, targetSquareExtraGrenadeAsString);
        }

        if (move)
            return new ResponseGrenadeLauncher(targetBasicMode, targetSquareToMoveBasicModeAsString, null);
        return new ResponseGrenadeLauncher(targetBasicMode, null, null);

    }

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
            terminal.addOptionInput("2: attacco base e granata extra");
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
            mode = true;
        }

        responseIsReady = true;

    }

    @Override
    protected void inputBasicMode()
    {
        int i = 1;
        List<ColorId> colorIdList = new ArrayList<>();

        terminal.addTextInput("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : playerBasicMode.keySet())
        {
            terminal.addOptionInput(i + " " + colorIdIterate);
            colorIdList.add(colorIdIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetBasicMode = colorIdList.get(choice - 1);

        //Ask if the user wants move the target
        terminal.addTextInput("Vuoi Spostare il bersaglio?");
        terminal.addOptionInput("1: Sì");
        terminal.addOptionInput("2: No");

        choice = terminal.inputInt(1, 2);

        if (choice == 2)//If the user has chosen no
        {
            targetSquareToMoveBasicModeAsString=null;
            return;
        }

        move = true;


        terminal.addTextInput("Scegli un quadrato dove spostare il tuo bersaglio:");

        int w = 1;

        List<String> squaresAsString = new ArrayList<>();

        squaresAsString.addAll(hashMapToMovePlayers.get(targetBasicMode));

        for (String squareAsStringIterate : squaresAsString)
        {
            terminal.addOptionInput(w+":"+ squareAsStringIterate);
            w++;
        }

        choice = terminal.inputInt(1, w - 1);

        targetSquareToMoveBasicModeAsString = squaresAsString.get(choice - 1);

        if (squaresExtraGrenadeAsString.contains(targetSquareToMoveBasicModeAsString))
            squaresExtraGrenadeAsString.remove(playerBasicMode.get(targetBasicMode));

        if (!squaresExtraGrenadeAsString.contains(targetSquareToMoveBasicModeAsString) && allSquaresPlayerSees.contains(targetSquareToMoveBasicModeAsString))
        {
            squaresExtraGrenadeAsString.add(targetSquareToMoveBasicModeAsString);
            squaresExtraGrenadeAsString.remove(playerBasicMode.get(targetBasicMode));
        }



    }

    @Override
    protected void inputAdditionalMode()
    {
        int i = 1;

        List<String> squaresToTargetForAdditionalGrenade = squaresExtraGrenadeAsString;

        terminal.addTextInput("Scegli uno square bersaglio per la granata aggiuntiva:");

        for (String squareAsStringIterate : squaresToTargetForAdditionalGrenade)
        {
            terminal.addOptionInput(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetSquareExtraGrenadeAsString = squaresToTargetForAdditionalGrenade.get(choice - 1);
    }


}
