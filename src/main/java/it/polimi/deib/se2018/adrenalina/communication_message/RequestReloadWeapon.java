package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;

public class RequestReloadWeapon extends RequestInput {
    //Attribute for the request
    protected String weaponsAvailableToReaload;//Targets for the basic mode

    //Attribute for the response
    protected int weaponToReload;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

    public RequestReloadWeapon(String weaponsAvailableToReaload) {
        this.weaponsAvailableToReaload = weaponsAvailableToReaload;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseReloadWeapon(weaponToReload);
    }

    protected void inputBasicMode() {
        int i = 1;

        terminal.addTextInput("vuoi ricaricare " + weaponsAvailableToReaload );


            terminal.addOptionInput("1: si " );
            terminal.addOptionInput("2: no " );

        int anInt = terminal.inputInt(1,2);

        weaponToReload = anInt ;


    }

}
