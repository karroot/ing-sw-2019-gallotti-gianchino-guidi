package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

public class RequestShootPeople extends RequestInput
{
    //Attribute for the request
    protected List<String> weaponCardsName;//Targets for the basic mode

    //Attribute for the response
    protected int weaponCardNameChosen;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady=true;
    }

    public RequestShootPeople(List<String> weaponCardsName) {
        this.weaponCardsName = weaponCardsName;
    }

    public int getSizeWeaponCardsName() {
        return weaponCardsName.size();
    }

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

