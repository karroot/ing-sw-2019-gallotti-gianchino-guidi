package it.polimi.deib.se2018.adrenalina.communication_message;



import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to move around
 * @author Karroot
 */
public class RequestRunAround extends RequestInput {
    private int x;
    private int y;
    //Attribute for the request
    private List<String> squareToChose;

    public RequestRunAround(List<String> squareToChose) {
        this.squareToChose = squareToChose;
    }

    /**
     * this method print the coordinates where the player can move himself
     * @param terminal terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    { this.terminal=terminal;
        inputBasicMode();
        responseIsReady=true;
    }

    /**
     * this method return the response for run around
     * @return response for  run around
     * @throws IllegalStateException if input are not taken yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseRunAround(x,y);
    }

    protected void  inputBasicMode()
    {
        int i = 1;

        terminal.addTextInput("Scegli le coordinate:");

        for (String q: squareToChose)
        {
            terminal.addOptionInput(i + ":" + q );
            i++;
        }

        int anInt = terminal.inputInt(1, i - 1);
        y = Integer.parseInt(squareToChose.get(anInt -1).substring(11));
        x = Integer.parseInt(squareToChose.get(anInt -1).substring(4,5));//Works if the coordinates are between 1 and 9



    }
}
