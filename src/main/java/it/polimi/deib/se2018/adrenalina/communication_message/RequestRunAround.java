package it.polimi.deib.se2018.adrenalina.communication_message;



import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

public class RequestRunAround extends RequestInput {
    private int x;
    private int y;
    //Attribute for the request
    private List<String> squareToChose;

    public RequestRunAround(List<String> squareToChose) {
        this.squareToChose = squareToChose;
    }

    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        inputBasicMode();
        responseIsReady=true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseRunAround(x,y);
    }

    protected void  inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli le coordinate:");

        for (String q: squareToChose)
        {
            System.out.println(i + ":" + q );
            i++;
        }

        int anInt = inputInt(1, i - 1);
        y = Integer.parseInt(squareToChose.get(anInt -1).substring(11));
        x = Integer.parseInt(squareToChose.get(anInt -1).substring(4,5));//Works if the coordinates are between 1 and 9



    }
}
