package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;


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
        choseTarget();
        responseIsReady = true;

    }


    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");
        return new ResponseWhisper(targetBasicMode);
    }


    protected void choseTarget()
    {
        List<ColorId> players;

        players = playersBasicMode;
        int i = 1;

        for (ColorId t:players)//Ask to user the target
        {
            System.out.println(i+":"+t);
            i++;
        }
        int choice = inputInt(1, i - 1);
        targetBasicMode = players.get(choice-1);
    }


}
