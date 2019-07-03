package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.LinkedList;
import java.util.List;

public class RequestPowerUp extends RequestInput {

    //Attribute for the request
    protected List<String> powerUptoChose;//Targets for the basic mode

    //Attribute for the response
    protected List<String> chosenPowerUp;//Target chosen for the basic mode

    public RequestPowerUp(List<String> powerUptoChose) {
        this.chosenPowerUp= new LinkedList<>();
        this.powerUptoChose = powerUptoChose;
    }

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
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
            terminal.addTextInput("vuoi usare il poweup: " + t + " ?");


            terminal.addOptionInput("1 : sì");
            terminal.addOptionInput("2 : no");

             anInt = terminal.inputInt(1,2);
             if(anInt==1)
                 chosenPowerUp.add(t);
        }


    }
    public List<String> getPowerUptoChose() {
        return powerUptoChose;
    }

}

