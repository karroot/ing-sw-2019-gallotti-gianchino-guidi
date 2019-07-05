package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;
/**
 * This class represents the request that the controller sends at the client if the player decided
 * to use the power up tagBackGrenade
 * @author Karroot
 */
public class RequestTagbackGranade extends RequestInput
{
    //Attribute for the request
    protected ColorId playerBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode
    /**
     * request to use the tag back grenade power up
     * @param playersBasicMode player to add mark
     */
    public RequestTagbackGranade(ColorId playersBasicMode) {
        this.playerBasicMode = playersBasicMode;
    }

    /**
     * this method will print all the available actions and ask the player to respond to requests
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }
    /**
     * this method generate the response message ResponseTagbackGranade
     * @return the Response of Tag back Grenade
     * @throws IllegalStateException if response is not ready
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseTagbackGranade(targetBasicMode);
    }


    protected void inputBasicMode()
    {
        int i = 1;

        terminal.addTextInput("vuoi usare un powerup:");

        terminal.addOptionInput("1 : sì");
        terminal.addOptionInput("2 : no");

        int anInt = terminal.inputInt(1,2);
        if (anInt==1)
            targetBasicMode = playerBasicMode;
        else
            targetBasicMode=null;
    }

}
