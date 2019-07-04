package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author giovanni
 */
public class RequestHellion extends WeaponWithModeAlternative
{
    //Attribute for the request
    private HashMap<String, List<ColorId>> hashMapBasicMode;//Targets for the basic mode
    private HashMap<String, List<ColorId>> hashMapNanoTracerMode;//Targets for the basic mode

    

    //Attribute for the response
    private String targetBasicModeSquare;//Target chosen for the basic mode
    private ColorId targetBasicModePlayer;
    private String targetAlternativeModeSquare;//Target chosen for the alternative mode
    private ColorId targetAlternativeModePlayer;

    private ColorId targetBasicMode;
    private ColorId targetAlternativeMode;



    public RequestHellion(boolean[] availableMethod, HashMap<String, List<ColorId>> hashMapBasicMode, HashMap<String, List<ColorId>> hashMapNanoTracerMode)
    {
        this.availableMethod = availableMethod;
        this.nameAlternaivemode = "modalità nano-traccianti";
        this.hashMapBasicMode = hashMapBasicMode;
        this.hashMapNanoTracerMode = hashMapNanoTracerMode;
        responseIsReady = false;
    }



    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseHellion(null,targetAlternativeMode);

        return new ResponseHellion(targetBasicMode, null);
    }



    private void inputBasicModeSquare()
    {
        int i = 1;

        List<String> stringSquaresList = new ArrayList<>();

        stringSquaresList.addAll(hashMapBasicMode.keySet());

        terminal.addTextInput("Scegli lo square dove si trova il player bersaglio:");

        for (String squareAsStringIterate : stringSquaresList)
        {
            terminal.addOptionInput(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetBasicModeSquare = stringSquaresList.get(choice - 1);
    }

    private void inputBasicModePlayer()
    {
        int i = 1;

        List<ColorId> colorIdList = hashMapBasicMode.get(targetBasicModeSquare);

        terminal.addTextInput("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : colorIdList)
        {
            terminal.addOptionInput(i + " " + colorIdIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetBasicModePlayer = colorIdList.get(choice - 1);
    }


    @Override
    protected void inputBasicMode ()
    {
        inputBasicModeSquare();
        inputBasicModePlayer();

        targetBasicMode = targetBasicModePlayer;
        responseIsReady = true;
    }


    private void inputAlternativeModeSquare()
    {
        int i = 1;

        List<String> stringSquaresList = new ArrayList<>();

        stringSquaresList.addAll(hashMapNanoTracerMode.keySet());

        terminal.addTextInput("Scegli lo square dove si trova il player bersaglio:");

        for (String squareAsStringIterate : stringSquaresList)
        {
            terminal.addOptionInput(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetAlternativeModeSquare = stringSquaresList.get(choice - 1);
    }

    private void inputAlternativeModePlayer()
    {
        int i = 1;

        List<ColorId> colorIdList = hashMapNanoTracerMode.get(targetAlternativeModeSquare);

        terminal.addTextInput("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : colorIdList)
        {
            terminal.addOptionInput(i + " " + colorIdIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetAlternativeModePlayer = colorIdList.get(choice - 1);
    }


    @Override
    protected void inputAlternativeMode()
    {
       inputAlternativeModeSquare();
       inputAlternativeModePlayer();
       targetAlternativeMode = targetAlternativeModePlayer;

       responseIsReady = true;
    }



}
