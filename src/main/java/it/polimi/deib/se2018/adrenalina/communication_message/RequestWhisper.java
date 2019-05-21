package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;

public class RequestWhisper extends RequestInput  {

    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    protected boolean[] avaiableMethod; //Represent which mode are possible

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
  
    /**
     * Create a message of request for the weapon Whisper
     * @param avaiableMethod mode available
     * @param playerBasicMode targets for the basic mode
     */
    public RequestWhisper(boolean[] avaiableMethod, List<ColorId> playerBasicMode)
    {

        this.avaiableMethod = avaiableMethod;
        this.playersBasicMode = playerBasicMode;

        responseIsReady = false;
    }

    @Override
    public void printActionsAndReceiveInput() {
        int choice;
    int i=1;
        for (ColorId p : playersBasicMode)
        {
            System.out.println(i +" : " + p);
            i++;
        }
       choice = inputInt(1,i-1);
targetBasicMode = playersBasicMode.get(choice-1);
        responseIsReady = true;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");
        return new ResponseElectroSchyte(targetBasicMode);
    }



}
