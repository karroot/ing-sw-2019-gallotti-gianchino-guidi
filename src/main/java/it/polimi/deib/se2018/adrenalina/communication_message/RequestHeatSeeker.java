package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

/**
 * This class implements the request that the controller sends to the client if the player decided to use the weapon HeatSeeker
 *
 * @author gioguidi
 *
 */
public class RequestHeatSeeker extends RequestInput
{
    //Attribute for the request
    private List<ColorId> colorIdListBasicMode;
    protected boolean[] availableMethod;

    //Attribute for the response
    private ColorId targetBasicMode;

    /**
     * This method implement the request for the weapon.
     *
     * @param availableMethod is a vector of boolean that indicates the available mode of the weapon
     * @param colorIdListBasicMode are the possible target for the basic mode
     */
    public RequestHeatSeeker (boolean[] availableMethod, List<ColorId> colorIdListBasicMode)
    {
        this.availableMethod = availableMethod;
        this.colorIdListBasicMode = colorIdListBasicMode;
        responseIsReady = false;
    }


    /**
     * This method will ask the user to choose the target for the weapon.
     *
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        this.terminal=terminal;
        int i=1;

        List<ColorId> colorIdList = colorIdListBasicMode;

        terminal.addTextInput("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : colorIdList)
        {
            terminal.addOptionInput(i + ": " + colorIdIterate);
            i++;
            int s = 0;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetBasicMode = colorIdList.get(choice - 1);
        responseIsReady = true;

    }

    /**
     * This method generates the response message for the weapon with all the choices of the player
     *
     * @return the response message
     */
    @Override
    public ResponseInput generateResponseMessage()
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi.");

        return new ResponseHeatSeeker(targetBasicMode);
    }


}
