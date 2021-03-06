package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the request that the controller sends to the client if the player decided to use the weapon Grenade Launcher
 *
 * @author gioguidi
 *
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


    /**
     * This method implement the request for the weapon.
     *
     * @param availableMethod is a vector of boolean that indicates the available mode of the weapon
     * @param playerBasicMode are the possible targets for the basic mode
     * @param squaresExtraGrenadeAsString are the possible square target for the extra grenade
     * @param hashMapToMovePlayers is an hashmap with as key the players and values the possible squares to move them
     * @param allSquaresPlayerSees is a list of all the squares seen by the player
     */
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


    /**
     * This method generates the response message for the weapon with all the choices of the player
     *
     * @return the response message
     */
    public ResponseInput generateResponseMessage()
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi.");

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

    /**
     * This method will ask the user to choose which modes to use.
     *
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        this.terminal=terminal;
        int choiceOfPlayer=0;

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
            choiceOfPlayer = terminal.inputInt(1, 2);//the player can choose both options
        }
        else choiceOfPlayer = terminal.inputInt(1, 1);

        inputBasicMode();//will get the input for the BasicMode

        int l = 0;

        if (choiceOfPlayer==2)
        {
            inputAdditionalMode();
            mode = true;
        }

        responseIsReady = true;

    }


    /**
     * This method  the targets to use the basic mode. The player will choose the input from the possible targets value.
     */
    @Override
    protected void inputBasicMode()
    {
        int i = 1;
        List<ColorId> colorIdList = new ArrayList<>();

        terminal.addTextInput("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : playerBasicMode.keySet())
        {
            terminal.addOptionInput(i + ": " + colorIdIterate);
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
            terminal.addOptionInput(w+": "+ squareAsStringIterate);
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

    /**
     * This method sets the targets to use the additional mode. The player will choose the input from the possible targets value.
     */
    @Override
    protected void inputAdditionalMode()
    {
        int i = 1;

        List<String> squaresToTargetForAdditionalGrenade = squaresExtraGrenadeAsString;

        terminal.addTextInput("Scegli uno square bersaglio per la granata aggiuntiva:");

        for (String squareAsStringIterate : squaresToTargetForAdditionalGrenade)
        {
            terminal.addOptionInput(i + ": " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetSquareExtraGrenadeAsString = squaresToTargetForAdditionalGrenade.get(choice - 1);
    }


}
