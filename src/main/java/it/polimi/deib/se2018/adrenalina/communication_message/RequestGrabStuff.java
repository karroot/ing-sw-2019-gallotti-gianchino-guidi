package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

public class RequestGrabStuff extends RequestInput
{
    //Attribute for the request
    protected List<String> squareAviableToGrab;//Targets for the basic mode

    //Attribute for the response
    private int x;
    private int y;

    public RequestGrabStuff(List<String> squareAviableToGrab) {
        this.squareAviableToGrab = squareAviableToGrab;
    }

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) {
        inputBasicMode();
        responseIsReady=true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseGrabStuff(x,y);
    }

    protected void  inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli dove spostarti per raccogliere:");

        for (String w : squareAviableToGrab)
        {

            System.out.println(i + ": " + w);
            i++;

        }

        int anInt = inputInt(1, i - 1);

        y = Integer.parseInt(squareAviableToGrab.get(anInt -1).substring(11));
        x = Integer.parseInt(squareAviableToGrab.get(anInt -1).substring(4,5));//Works if the coordinates are between 1 and 9


    }
}
