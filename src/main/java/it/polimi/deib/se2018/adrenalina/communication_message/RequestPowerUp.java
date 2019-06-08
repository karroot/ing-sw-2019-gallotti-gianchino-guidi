package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class RequestPowerUp extends RequestInput {

    //Attribute for the request
    protected List<String> powerUptoChose;//Targets for the basic mode

    //Attribute for the response
    protected int chosenPowerUp;//Target chosen for the basic mode

    public RequestPowerUp(List<String> powerUptoChose) {
        this.powerUptoChose = powerUptoChose;
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

        return new ResponsePowerUp(chosenPowerUp);
    }


    protected void inputBasicMode()
    {
        int anInt=0;
        for (String t:powerUptoChose)
        {
        System.out.println("vuoi usare il poweup: " + t + " ?");


            System.out.println( "1 : sì");
            System.out.println( "2 : no");

             anInt = inputInt(1,2);
        }



        chosenPowerUp = anInt;

    }

}
