package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.LinkedList;
import java.util.List;
/**
 * This class represents the request that the conroller sends at the client if the player decided
 * to use a power up
 * @author Karroot
 */
public class RequestPowerUp extends RequestInput {

    //Attribute for the request
    private List<String> powerUptoChose;//Targets for the basic mode

    //Attribute for the response
    private List<String> chosenPowerUp;//Target chosen for the basic mode

    /**
     * This is the public constructor for the class.
     *
     * @param powerUptoChose is the stringList of powerUp to set
     */
    public RequestPowerUp(List<String> powerUptoChose) {
        this.chosenPowerUp= new LinkedList<>();
        this.powerUptoChose = powerUptoChose;
    }

    /**
     * This method will prepare the chosen powerUp for the response.
     *
     * @param terminal  terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

    /**
     * This method will generate the reponse message to use the powerUp
     *
     * @return the response with the chosen powerUp
     */
    @Override
    public ResponseInput generateResponseMessage()
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi.");

        return new ResponsePowerUp(chosenPowerUp);
    }


    /**
     * This method will ask the user which powerUp he wants to use.
     *
     */
    protected void inputBasicMode()
    {
        int anInt=0;
        for (String t:powerUptoChose)
        {
        terminal.addTextInput("Vuoi usare il powerUp: " + t + " ?");


            terminal.addOptionInput("1: sì");
            terminal.addOptionInput("2: no");

             anInt = terminal.inputInt(1,2);
             if(anInt==1)
                 chosenPowerUp.add(t);
        }


    }

    /**
     * This is the public getter for the attribute powerUptoChose
     *
     * @return the value of the attribute powerUptoChose
     */
    public List<String> getPowerUptoChose()
    {
        return powerUptoChose;
    }

}

