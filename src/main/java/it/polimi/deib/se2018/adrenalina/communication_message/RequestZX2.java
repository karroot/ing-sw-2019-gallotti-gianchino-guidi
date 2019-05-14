package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class RequestZX2 extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<ColorId> playersBasicMode;
    private List<ColorId> playersAlternativeMode;

    //Attribute for the response
    private ColorId targetBasicMode;
    private List<ColorId> targetsAlternativeMode = new LinkedList<>();

    public RequestZX2(boolean[] avaiableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAlternativeMode)
    {
        this.avaiableMethod = avaiableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAlternativeMode = playerAlternativeMode;
    }




    @Override
    public ResponseInput generateResponseMessage()
    {
        if (mode)
            return new ResponseZX2(targetsAlternativeMode);

        return new ResponseZX2(targetBasicMode);
    }

    @Override
    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un bersaglio:");

        for (ColorId t:playersBasicMode)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(anInt -1);

    }

    @Override
    protected void inputAlternativeMode()
    {

        int i = 1; //Variable to cycle


        List<Integer> intchoice = new LinkedList<>();

        System.out.println("Scegli tre bersagli da marchiare:");

        for (ColorId t:playersAlternativeMode) //Print the possible choice
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int inputInt;

        for (int j = 1;j<=3;j++)//Player must choose three target not equals
        {
            inputInt = inputInt(1, playersAlternativeMode.size());//Ask a target

            while (intchoice.contains(inputInt)) //if the target was chosen before
            {
                inputInt = inputInt(1, playersAlternativeMode.size());//Ask a new target
            }

            intchoice.add(inputInt); //Add the target in the list of targets
            targetsAlternativeMode.add(playersAlternativeMode.get(inputInt-1));

        }

    }
}
