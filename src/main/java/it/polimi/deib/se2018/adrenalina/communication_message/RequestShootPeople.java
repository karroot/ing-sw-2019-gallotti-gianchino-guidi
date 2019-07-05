package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to shot enemies
 * @author Karroot
 */
public class RequestShootPeople extends RequestInput
{
    //Attribute for the request
    protected List<String> weaponCardsName;//Targets for the basic mode

    //Attribute for the response
    protected int weaponCardNameChosen;//Target chosen for the basic mode

    /**
     * Ask at the user which weapon he want to use
     * @param terminal  terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady=true;
    }

    /**
     *  it set the weapon cards name
     * @param weaponCardsName name of weapon card
     */
    public RequestShootPeople(List<String> weaponCardsName) {
        this.weaponCardsName = weaponCardsName;
    }


    /**
     * Generate the response message for the shoot with all player's choice
     * @return Response Shoot People
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseShootPeople(weaponCardNameChosen);
    }

    protected void  inputBasicMode()
    {
            int i = 1;

            terminal.addTextInput("Scegli l'arma che vuoi usare:");

            for (String w : weaponCardsName)
            {

                    terminal.addOptionInput(i + ": " + w);
                    i++;

            }

            int anInt = terminal.inputInt(1, i - 1);

        weaponCardNameChosen = anInt ;


    }

    public List<String> getWeaponCardsName()
    {
        return weaponCardsName;
    }
}

