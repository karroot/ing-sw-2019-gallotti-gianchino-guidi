package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to use the weapon Whisper
 * @author Karroot
 */
public class RequestWhisper extends RequestInput  {

    //Attribute for the request
    private List<ColorId> playersBasicMode;//Targets for the basic mode
    protected boolean[] availableMethod; //Represent which mode are possible

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
  
    /**
     * Create a message of request for the weapon Whisper
     * @param availableMethod mode available
     * @param playerBasicMode targets for the basic mode
     */
    public RequestWhisper(boolean[] availableMethod, List<ColorId> playerBasicMode)
    {

        this.availableMethod = availableMethod;
        this.playersBasicMode = playerBasicMode;

        responseIsReady = false;
    }
    /**
     * this method will print all the available actions and ask the player to respond to requests
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        choseTarget();
        responseIsReady = true;

    }

    /**
     *Generate the response message for the Whisper with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
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
        terminal.addTextInput("scegli target:"); //Ask to user the first effect
        for (ColorId t:players)//Ask to user the target
        {
            terminal.addOptionInput(i+":"+t);
            i++;
        }
        int choice = terminal.inputInt(1, i - 1);
        targetBasicMode = players.get(choice-1);
    }


}
