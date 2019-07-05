package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the request that the controller sends to the client if the player decided to use the weapon Vortex Cannon
 *
 * @author gioguidi
 *
 */
public class RequestVortexCannon extends WeaponWithOneAdditionalEffects
{
    //Attribute for the request
    private HashMap<String, List<ColorId>> hashMapBasicMode;

    //Attribute for the response
    private ColorId targetPlayerBasicMode;
    private String targetVortexSquareAsString;
    private ColorId target1BlackHoleMode;
    private ColorId target2BlackHoleMode = null;
    private boolean mode = false;


    /**
     * This method implement the request for the weapon.
     *
     * @param availableMethod is a vector of boolean that indicates the available mode of the weapon
     * @param hashMapBasicMode contains the targets for the basic and alternative mode
     */
    public RequestVortexCannon(boolean[] availableMethod, HashMap<String, List<ColorId>> hashMapBasicMode)
    {
        this.nameAdditionalmode = "modalità buco nero";
        this.availableMethod = availableMethod;
        this.hashMapBasicMode = hashMapBasicMode;
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
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
        {
            return new ResponseVortexCannon(targetPlayerBasicMode , targetVortexSquareAsString, target1BlackHoleMode, target2BlackHoleMode);
        } else
            return new ResponseVortexCannon(targetPlayerBasicMode, targetVortexSquareAsString, null,null);

    }

    /**
     * This method will ask the user the mode to use and then invoke the moths to choose the targets.
     *
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

        int f = 0;

        if (availableMethod[1])//Print the possible effects
        {
            terminal.addOptionInput("2: attacco base e buco nero");
        }

        if (availableMethod[1])//Print the possible effects
        {
            choice = terminal.inputInt(1, 2);
        }
        else choice = terminal.inputInt(1, 1);

        if (choice==1)
        {
            inputBasicMode();
        }

        if (choice==2)
        {
            mode = true;
            inputAdditionalMode();
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
        int j = 1;

        List<String> squaresPossiblesForVortexAsString = new ArrayList<>();
        squaresPossiblesForVortexAsString.addAll(hashMapBasicMode.keySet());

        i = askSquareAndPlayerTarget(i, squaresPossiblesForVortexAsString);
        int choice;
        terminal.addTextInput("Scegli un player bersaglio:");

        List<ColorId> playersSelectedSquareAsList = new ArrayList<>();
        playersSelectedSquareAsList.addAll(hashMapBasicMode.get(targetVortexSquareAsString));

        for (ColorId colorIdIterate : playersSelectedSquareAsList)
        {
            terminal.addOptionInput(j + ": " + colorIdIterate);
            i++;
        }

        choice = terminal.inputInt(1, j - 1);

       targetPlayerBasicMode = playersSelectedSquareAsList.get(choice - 1);

    }


    /**
     * This method will ask the user where to open the vortex.
     *
     * @param i is the index to cycle
     * @param squaresPossiblesForVortexAsString are the possible squares to open the vortex
     * @return the index i
     */
    private int askSquareAndPlayerTarget(int i, List<String> squaresPossiblesForVortexAsString)
    {
        terminal.addTextInput("Scegli uno square bersaglio dove aprire il vortice:");

        for (String squareAsStringIterate : squaresPossiblesForVortexAsString)
        {
            terminal.addOptionInput(i + ": " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetVortexSquareAsString = squaresPossiblesForVortexAsString.get(choice - 1);
        return i;
    }


    /**
     * This method sets the targets to use the additional mode. The player will choose the input from the possible targets value.
     */
    @Override
    protected void inputAdditionalMode() {

        int i = 1;
        int j = 1;

        List<String> squaresPossiblesForVortexAsString = new ArrayList<>();

        for (String squareStringIterate : hashMapBasicMode.keySet())
        {
            if (hashMapBasicMode.get(squareStringIterate).size() > 1)
                squaresPossiblesForVortexAsString.add(squareStringIterate);
        }

        i = askSquareAndPlayerTarget(i, squaresPossiblesForVortexAsString);
        int choice;


        List<ColorId> playersSelectedSquareAsList2 = new ArrayList<>();
        playersSelectedSquareAsList2.addAll(hashMapBasicMode.get(targetVortexSquareAsString));

        terminal.addTextInput("Scegli il player bersaglio per la modalità base:");

        for (ColorId colorIdIterate : playersSelectedSquareAsList2) {
            terminal.addOptionInput(j + ": " + colorIdIterate);
            i++;
        }

        choice = terminal.inputInt(1, j - 1);

        targetPlayerBasicMode = playersSelectedSquareAsList2.get(choice - 1);

        playersSelectedSquareAsList2.remove(targetPlayerBasicMode);

        terminal.addTextInput("Scegli un bersaglio per la modalità buco nero:");

        int k = 1;

        for (ColorId colorIdIterate : playersSelectedSquareAsList2) {
            terminal.addOptionInput(k + ": " + colorIdIterate);
            k++;
        }

        int choice1 = terminal.inputInt(1, k - 1);

        target1BlackHoleMode = playersSelectedSquareAsList2.get(choice1 - 1);

        playersSelectedSquareAsList2.remove(target1BlackHoleMode);

        if (!playersSelectedSquareAsList2.isEmpty()) {

            int l = 1;

            terminal.addTextInput("Scegli un secondo bersaglio per la modalità buco nero:");

            for (ColorId colorIdIterate : playersSelectedSquareAsList2) {
                terminal.addOptionInput(i + ": " + colorIdIterate);
                l++;
            }

            int choice2 = terminal.inputInt(1, l - 1);
            target2BlackHoleMode = playersSelectedSquareAsList2.get(choice2 - 1);
        }
    }


}
