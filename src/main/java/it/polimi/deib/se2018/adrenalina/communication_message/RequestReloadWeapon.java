package it.polimi.deib.se2018.adrenalina.communication_message;

import java.util.List;

public class RequestReloadWeapon extends RequestInput {
    //Attribute for the request
    protected List<String> weaponsAvaiableToReaload;//Targets for the basic mode

    //Attribute for the response
    protected String weaponToReload;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady = true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseReloadWeapon(weaponToReload);
    }

    protected void inputBasicMode() {
        int i = 1;

        System.out.println("Scegli l'arma che vuoi ricaricare:");

        for (String w : weaponsAvaiableToReaload) {

            System.out.println(i + ": " + w);
            i++;

        }

        int anInt = inputInt(1, i - 1);

        weaponToReload = weaponsAvaiableToReaload.get(anInt - 1);


    }

}
