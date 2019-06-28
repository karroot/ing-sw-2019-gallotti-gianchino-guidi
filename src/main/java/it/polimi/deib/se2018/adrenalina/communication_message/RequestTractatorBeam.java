package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestTractatorBeam extends WeaponWithModeAlternative {

    //Attribute for the request
    private Map<ColorId,List<String>>  playersBasicMode;//Targets for the basic mode
    private List<ColorId> playersAlternativeMode;//Targets for the basic mode


    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private ColorId targetAlternativeMode;//Targets chosen for the alternative mode
    private int x;
    private int y;
    /**
     * Create a message of request for the weapon TractatorBeam
     * @param availableMethod mode available
     * @param playerBasicMode targets for the basic mode
     * @param playerAlternativeMode targets for the alternative mode
     */
    public RequestTractatorBeam(boolean[] availableMethod,  Map<ColorId,List<String>>  playerBasicMode, List<ColorId> playerAlternativeMode)
    {
        this.nameAlternaivemode = "modalità punitore";
        this.availableMethod = availableMethod;
        this.playersBasicMode = playerBasicMode;
        this.playersAlternativeMode = playerAlternativeMode;

        responseIsReady = false;
    }

    @Override
    protected void inputAlternativeMode() {

        int i = 1; //Variable to cycle

        terminal.addTextInput("Scegli bersaglio:");

        for (ColorId t:playersAlternativeMode) //Print the possible choice
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }
        int anInt = terminal.inputInt(1, i - 1);

        targetAlternativeMode = playersAlternativeMode.get(anInt -1);


    }

    @Override
    protected void inputBasicMode()
    {
        int i = 1; //Variable to cycle
        List<ColorId> target = new ArrayList<ColorId>(playersBasicMode.keySet());

        terminal.addTextInput("Scegli bersaglio:");

        for (ColorId t:playersBasicMode.keySet()) //Print the possible choice
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }
        int anInt = terminal.inputInt(1, i - 1);

        targetBasicMode = target.get(anInt -1);



        terminal.addTextInput("Scegli square dove spostarlo:");

        i=1;
        for (String s : playersBasicMode.get(targetBasicMode)) //Print the possible choice
        {
            terminal.addOptionInput(i + ":" + s);
            i++;
        }
         anInt = terminal.inputInt(1, i - 1);
//Save the coordinate
        y = Integer.parseInt(playersBasicMode.get(targetBasicMode).get(anInt -1).substring(11));
        x = Integer.parseInt(playersBasicMode.get(targetBasicMode).get(anInt -1).substring(4,5));//Works if the coordinates are between 1 and 9

        int l = 0;
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
