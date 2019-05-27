package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.List;

public class RequestTargettingScope  extends RequestInput{
    //Attribute for the request
    protected List<ColorId> playersBasicMode;//Targets for the basic mode
protected List<Color> playerAmmo; // all ammo of the player
    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode
    protected Color targetAmmo;
    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady = true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseTargettingScope(targetBasicMode,targetAmmo);
    }
    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un bersaglio:");

        for (ColorId t:playersBasicMode)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(anInt -1);


        i=1;
        System.out.println("Scegli una munizione:");

        for (Color t:playerAmmo)
        {
            System.out.println(i + ":" + t);
            i++;
        }

         anInt = inputInt(1, i - 1);
        targetAmmo= playerAmmo.get(anInt-1);
    }
}
