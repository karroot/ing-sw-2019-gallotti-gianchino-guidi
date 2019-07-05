package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.View.Terminal;
/**
 *  This class represents the request that the conroller sends at the client if the player decided
 *  to reload the weapon
 * @author Karroot
 */
public class RequestReloadWeapon extends RequestInput {
    //Attribute for the request
    protected String weaponsAvailableToReaload;//Targets for the basic mode

    //Attribute for the response
    protected int weaponToReload;//Target chosen for the basic mode
    /**
     * Ask at the user which weapon he want to reload
     * @param terminal terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }
    /**
     * this method is used to set the weapons that can be reload
     * @param weaponsAvailableToReaload is the weapon that can be reload
     */
    public RequestReloadWeapon(String weaponsAvailableToReaload) {
        this.weaponsAvailableToReaload = weaponsAvailableToReaload;
    }
    /**
     * this method return the response for reload
     * @return response for reload
     * @throws IllegalStateException if input are not taken yet
     */
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
