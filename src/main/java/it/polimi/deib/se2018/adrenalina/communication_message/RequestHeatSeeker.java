package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

/**
 * @author gioguidi
 */
public class RequestHeatSeeker extends RequestInput
{
    //Attribute for the request
    private List<ColorId> colorIdListBasicMode;
    protected boolean[] availableMethod;

    //Attribute for the response
    private ColorId targetBasicMode;

    /**
     *
     * @param availableMethod
     * @param colorIdListBasicMode
     */
    public RequestHeatSeeker (boolean[] availableMethod, List<ColorId> colorIdListBasicMode)
    {
        this.availableMethod = availableMethod;
        this.colorIdListBasicMode = colorIdListBasicMode;
        responseIsReady = false;
    }


    /**
     *
     * @param terminal
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
            terminal.addOptionInput(i + " : " + colorIdIterate);
            i++;
        }

        int s = 0;

        int choice = terminal.inputInt(1, i - 1);

        targetBasicMode = colorIdList.get(choice - 1);
        responseIsReady = true;

    }

    /**
     *
     * @return
     */
    @Override
    public ResponseInput generateResponseMessage()
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi.");

        return new ResponseHeatSeeker(targetBasicMode);
    }


}
