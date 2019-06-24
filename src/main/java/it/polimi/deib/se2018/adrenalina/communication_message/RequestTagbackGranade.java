package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

public class RequestTagbackGranade extends RequestInput
{
    //Attribute for the request
    protected ColorId playerBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode

    public RequestTagbackGranade(ColorId playersBasicMode) {
        this.playerBasicMode = playersBasicMode;
    }

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) {
        inputBasicMode();
        responseIsReady = true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseTagbackGranade(targetBasicMode);
    }


    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("vuoi usare un powerup:");

        System.out.println("1 : sì");
        System.out.println("2 : no");

        int anInt = inputInt(1,2);
        if (anInt==1)
            targetBasicMode = playerBasicMode;
        else
            targetBasicMode=null;
    }

}
