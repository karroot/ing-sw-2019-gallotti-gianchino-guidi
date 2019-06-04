package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;

import java.util.List;

/**
 * @author giovanni
 */
public class RequestHeatSeeker extends RequestInput
{
    //Attribute for the request
    private List<ColorId> colorIdListBasicMode;
    protected boolean[] avaiableMethod;

    //Attribute for the response
    private ColorId targetBasicMode;

    public RequestHeatSeeker (boolean[] avaiableMethod, List<ColorId> colorIdListBasicMode)
    {
        this.avaiableMethod = avaiableMethod;
        this.colorIdListBasicMode = colorIdListBasicMode;
        responseIsReady = false;
    }


    @Override
    public void printActionsAndReceiveInput()
    {
        int i=1;

        List<ColorId> colorIdList = colorIdListBasicMode;

        System.out.println("Scegli una player bersaglio:");

        for (ColorId colorIdIterate : colorIdList)
        {
            System.out.println(i + " " + colorIdIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetBasicMode = colorIdList.get(choice - 1);
        responseIsReady = true;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");


        return new ResponseHeatSeeker(targetBasicMode);
    }


}
