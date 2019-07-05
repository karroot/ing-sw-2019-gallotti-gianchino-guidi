package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to grab ammo or power and ammo
 * @author Karroot
 */
public class RequestGrabStuff extends RequestInput
{
    //Attribute for the request
    protected List<String> squareAviableToGrab;//Targets for the basic mode

    //Attribute for the response
    private int x;
    private int y;

    /**
     * Create the request to send at the client
     * @param squareAviableToGrab list of all squares that the player can choose
     */
    public RequestGrabStuff(List<String> squareAviableToGrab) {
        this.squareAviableToGrab = squareAviableToGrab;
    }

    /**
     * Ask at the user to choose a square where to grab
     * @param terminal terminal that represents the GUI or CLI
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady=true;
    }

    /**
     * Generate the response Message with the coordinate of the square chosen by user
     * @return response message
     * @throws IllegalStateException if the inputs there weren't taken
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseGrabStuff(x,y);
    }

    protected void  inputBasicMode()
    {
        int i = 1;

        terminal.addTextInput("Scegli dove spostarti per raccogliere:");

        for (String w : squareAviableToGrab)
        {

            terminal.addOptionInput(i + ": " + w);
            i++;

        }

        int anInt = terminal.inputInt(1, i - 1);

        y = Integer.parseInt(squareAviableToGrab.get(anInt -1).substring(11));
        x = Integer.parseInt(squareAviableToGrab.get(anInt -1).substring(4,5));//Works if the coordinates are between 1 and 9


    }
}
