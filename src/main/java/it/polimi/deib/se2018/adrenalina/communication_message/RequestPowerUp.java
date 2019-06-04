package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class RequestPowerUp extends RequestInput {

    //Attribute for the request
    protected List<String> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected int targetBasicMode;//Target chosen for the basic mode

    public RequestPowerUp(List<String> playersBasicMode) {
        this.playersBasicMode = playersBasicMode;
    }

    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady = true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponsePowerUp(targetBasicMode);
    }


    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un poweup:");

        for (String t:playersBasicMode)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        targetBasicMode = anInt;

    }

}
