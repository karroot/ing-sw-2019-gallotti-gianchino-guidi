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
public class RequestVortexCannon extends WeaponWithOneAdditionalEffects
{
    //Attribute for the request
    private HashMap<String, List<ColorId>> hashMapBasicMode;
    private List<ColorId> playersBasicMode;
    private List<String> squaresListBasicMode;
    private List<ColorId> playersBlackHoleMode;

    //Attribute for the response
    private ColorId targetPlayerBasicMode;
    private String targetVortexSquareAsString;
    private ColorId target1BlackHoleMode;
    private ColorId target2BlackHoleMode = null;

    public RequestVortexCannon(boolean[] availableMethod, HashMap<String, List<ColorId>> hashMapBasicMode)
    {
        this.nameAdditionalmode = "modalità buco nero";
        this.availableMethod = availableMethod;
        this.hashMapBasicMode = hashMapBasicMode;
        responseIsReady = false;
    }

    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
        {
            return new ResponseVortexCannon(targetPlayerBasicMode , targetVortexSquareAsString, target1BlackHoleMode, target2BlackHoleMode);
        }

        return new ResponseVortexCannon(targetPlayerBasicMode, targetVortexSquareAsString, null,null);

    }

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        int choice=0;

        terminal.addTextInput("Cosa vuoi fare:"); //Ask to user the first effect
        if (availableMethod[0])//Print the possible effects
        {
            terminal.addOptionInput("1: solo attacco base");
        }
        if (availableMethod[1])//Print the possible effects
        {
            terminal.addOptionInput("2: attacco base e buco nero");
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
        }

        responseIsReady = true;

    }

    @Override
    protected void inputBasicMode() {

        int i = 1;

        List<String> squaresPossiblesForVortexAsString = new ArrayList<>();
        squaresPossiblesForVortexAsString.addAll(hashMapBasicMode.keySet());

        terminal.addTextInput("Scegli uno square bersaglio dove aprire il vortice:");

        for (String squareAsStringIterate : squaresPossiblesForVortexAsString)
        {
            terminal.addOptionInput(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetVortexSquareAsString = squaresPossiblesForVortexAsString.get(choice - 1);
        terminal.addTextInput("Scegli un player bersaglio:");

        List<ColorId> playersSelectedSquareAsList = new ArrayList<>();
        playersSelectedSquareAsList.addAll(hashMapBasicMode.get(targetVortexSquareAsString));

        for (ColorId colorIdIterate : playersSelectedSquareAsList)
        {
            terminal.addOptionInput(i + " " + colorIdIterate);
            i++;
        }

        choice = terminal.inputInt(1, i - 1);

       targetPlayerBasicMode = playersSelectedSquareAsList.get(choice - 1);

    }


    @Override
    protected void inputAdditionalMode() {

        int i = 1;

        List<ColorId> colorIdList = new ArrayList<>();
        colorIdList.addAll(hashMapBasicMode.get(targetVortexSquareAsString));
        colorIdList.remove(targetPlayerBasicMode);

        terminal.addTextInput("Scegli un bersaglio per la modalità black hole:");

        for (ColorId colorIdIterate : colorIdList) {
            terminal.addOptionInput(i + " " + colorIdList);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        target1BlackHoleMode = colorIdList.get(choice - 1);

        colorIdList.remove(choice - 1);

        if (!colorIdList.isEmpty()) {

            i = 1;

            terminal.addTextInput("Scegli un secondo bersaglio per la modalità buco nero:");

            for (ColorId colorIdIterate : colorIdList) {
                terminal.addOptionInput(i + " " + colorIdList);
                i++;
            }

            choice = terminal.inputInt(1, i - 1);
            target2BlackHoleMode = colorIdList.get(choice - 1);
        }
    }


}
