package it.polimi.deib.se2018.adrenalina.communication_message;

import java.util.List;

public class RequestReloadWeapon extends RequestInput {
    //Attribute for the request
    protected String weaponsAvailableToReaload;//Targets for the basic mode

    //Attribute for the response
    protected int weaponToReload;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput() {
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

        System.out.println("vuoi ricaricare " + weaponsAvailableToReaload );


            System.out.println( "1: si " );
            System.out.println( "2: no " );

        int anInt = inputInt(1,2);

        weaponToReload = anInt ;


    }

}
