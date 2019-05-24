package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.LinkedList;
import java.util.List;

public class RequestTractatorBeam extends WeaponWithModeAlternative {

    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    private List<ColorId> playersAlternativeMode;//Targets for the basic mode
    private List<String> squareBasicMode;

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private ColorId targetAlternativeMode;//Targets chosen for the alternative mode
    private int x;
    private int y;
    /**
     * Create a message of request for the weapon TractatorBeam
     * @param avaiableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAlternativeMode targets for the alternative mode
     */
    public RequestTractatorBeam(boolean[] avaiableMethod, List<ColorId> playerBasicMode, List<ColorId> playerAlternativeMode, List<String> squareBasicMode)
    {
        this.nameAlternaivemode = "modalità punitore";
        this.avaiableMethod = avaiableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAlternativeMode = playerAlternativeMode;
        this.squareBasicMode=squareBasicMode;
        responseIsReady = false;
    }

    @Override
    protected void inputAlternativeMode() {

        int i = 1; //Variable to cycle

        System.out.println("Scegli bersaglio:");

        for (ColorId t:playersAlternativeMode) //Print the possible choice
        {
            System.out.println(i + ":" + t);
            i++;
        }
        int anInt = inputInt(1, i - 1);

        targetAlternativeMode = playersAlternativeMode.get(anInt -1);


    }

    @Override
    protected void inputBasicMode()
    {
        int i = 1; //Variable to cycle

        System.out.println("Scegli bersaglio:");

        for (ColorId t:playersBasicMode) //Print the possible choice
        {
            System.out.println(i + ":" + t);
            i++;
        }
        int anInt = inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(anInt -1);



        System.out.println("Scegli square dove spostarlo:");

        i=1;
        for (String s : squareBasicMode) //Print the possible choice
        {
            System.out.println(i + ":" + s);
            i++;
        }
         anInt = inputInt(1, i - 1);
//Save the coordinate
        y = Integer.parseInt(squareBasicMode.get(anInt -1).substring(11));
            x = Integer.parseInt(squareBasicMode.get(anInt -1).substring(4,5));//Works if the coordinates are between 1 and 9

    }
    /**
     *Generate the response message for the Electro Schyte with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseTractatorBeam(targetAlternativeMode);

        return new ResponseTractatorBeam(targetBasicMode,x,y);
    }
}
