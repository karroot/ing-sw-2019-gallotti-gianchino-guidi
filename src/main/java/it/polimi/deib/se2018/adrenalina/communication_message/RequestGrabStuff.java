package it.polimi.deib.se2018.adrenalina.communication_message;

import java.util.List;

public class RequestGrabStuff extends RequestInput
{
    //Attribute for the request
    protected List<String> squareAviableToGrab;//Targets for the basic mode

    //Attribute for the response
    protected String squareToGoForGrab;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady=true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseGrabStuff(squareToGoForGrab);
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

        squareToGoForGrab = squareAviableToGrab.get(anInt - 1);


    }
}
