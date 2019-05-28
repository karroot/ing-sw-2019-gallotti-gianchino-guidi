package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;

import java.util.HashMap;
import java.util.List;

/**
 * @author giovanni
 */
public class RequestHellion extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<String> squaresBasicMode;//Targets for the basic mode
    private List<ColorId> playersBasicMode;
    private List<String> squaresAlternativeMode;//Targets for the basic mode
    private List<ColorId> playersAlterativeMode;

    //Attribute for the response
    private String targetBasicModeSquare;//Target chosen for the basic mode
    private ColorId targetBasicModePlayer;
    private String targetAlternativeModeSquare;//Target chosen for the alternative mode
    private ColorId targetAlternativeModePlayer;

    private HashMap<String, ColorId> targetBasicMode = new HashMap<>();
    private HashMap<String, ColorId> targetAlternativeMode = new HashMap<>();



    public RequestHellion(boolean[] avaiableMethod, List<String> squaresBasicMode, List<ColorId> playersBasicMode, List<String> squaresAlternativeMode, List<ColorId> playersAlterativeMode)
    {
        this.avaiableMethod = avaiableMethod;
        this.nameAlternaivemode = "modalità nano-traccianti";
        this.squaresBasicMode = squaresBasicMode;
        this.playersBasicMode = playersBasicMode;
        this.squaresAlternativeMode = squaresAlternativeMode;
        this.playersAlterativeMode = playersAlterativeMode;
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

        List<String> stringSquaresList = squaresBasicMode;

        System.out.println("Scegli una stanza bersaglio:");

        for (String squareAsStringIterate : stringSquaresList)
        {
            System.out.println(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetBasicModeSquare = stringSquaresList.get(choice - 1);
    }

    private void inputBasicModePlayer()
    {
        int i = 1;

        List<ColorId> colorIdList = playersBasicMode;

        System.out.println("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : colorIdList)
        {
            System.out.println(i + " " + colorIdIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetBasicModePlayer = colorIdList.get(choice - 1);
    }


    @Override
    protected void inputBasicMode ()
    {
        inputBasicModeSquare();
        inputBasicModePlayer();

        targetBasicMode.put(targetBasicModeSquare, targetBasicModePlayer);
        responseIsReady = true;
        //todo funziona??
    }


    private void inputAlternativeModeSquare()
    {
        int i = 1;

        List<String> stringSquaresList = squaresAlternativeMode;

        System.out.println("Scegli una stanza bersaglio:");

        for (String squareAsStringIterate : stringSquaresList)
        {
            System.out.println(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetAlternativeModeSquare = stringSquaresList.get(choice - 1);
    }

    private void inputAlternativeModePlayer()
    {
        int i = 1;

        List<ColorId> colorIdList = playersAlterativeMode;

        System.out.println("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : colorIdList)
        {
            System.out.println(i + " " + colorIdIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetAlternativeModePlayer = colorIdList.get(choice - 1);
    }


    @Override
    protected void inputAlternativeMode()
    {
       inputAlternativeModeSquare();
       inputAlternativeModePlayer();
       targetAlternativeMode.put(targetAlternativeModeSquare,targetAlternativeModePlayer);

       responseIsReady = true;
    }



}
