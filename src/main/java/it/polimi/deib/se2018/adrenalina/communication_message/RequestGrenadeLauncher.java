package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.HashMap;
import java.util.List;

/**
 * @author giovanni
 */
public class RequestGrenadeLauncher extends WeaponWithOneAdditionalEffects
{
    //Attribute for the request
    private List<ColorId> playersBasicMode;
    private List<String> squaresExtraGrenadeAsString;
    private HashMap<ColorId, List<String>> hashMapToMovePlayers;

    //Attribute for the response
    private ColorId targetBasicMode;
    private String targetSquareToMoveBasicModeAsString = null;
    private String targetSquareExtraGrenadeAsString;

    private boolean move;



    public RequestGrenadeLauncher(boolean[] availableMethod, List<ColorId> playerBasicMode, List<String> squaresExtraGrenadeAsString, HashMap<ColorId, List<String>> hashMapToMovePlayers)
    {
        this.nameAdditionalmode = "modalità granata extra";
        this.availableMethod = availableMethod;
        this.playersBasicMode = playersBasicMode;
        this.hashMapToMovePlayers = hashMapToMovePlayers;
        this.squaresExtraGrenadeAsString = squaresExtraGrenadeAsString;
        responseIsReady = false;
    }


    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
        {
            if (move)
                return new ResponseGrenadeLauncher(targetBasicMode , targetSquareToMoveBasicModeAsString, targetSquareExtraGrenadeAsString);

            return new ResponseGrenadeLauncher(targetBasicMode , null, targetSquareExtraGrenadeAsString);
        }

        if (move)
            return new ResponseGrenadeLauncher(targetBasicMode, targetSquareToMoveBasicModeAsString, null);
        return new ResponseGrenadeLauncher(targetBasicMode, null, null);

    }

    @Override
    public void printActionsAndReceiveInput() {
        int choice=0;

        System.out.println("Cosa vuoi fare:"); //Ask to user the first effect
        if (availableMethod[0])//Print the possible effects
        {
            System.out.println("1: solo attacco base");
        }
        if (availableMethod[1])//Print the possible effects
        {
            System.out.println("2:attacco base e granata extra");
        }

        if (availableMethod[1])//Print the possible effects
        {
            choice = inputInt(1, 2);
        }
        else choice = inputInt(1, 1);

        inputBasicMode();

        if (choice==2)
        {
            inputAdditionalMode();
            mode = true;
        }

        responseIsReady = true;

    }

    @Override
    protected void inputBasicMode()
    {
        int i = 1;

        List<ColorId> colorIdList = playersBasicMode;

        System.out.println("Scegli un player bersaglio:");

        for (ColorId colorIdIterate : playersBasicMode)
        {
            System.out.println(i + " " + colorIdIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(choice - 1);

        //Ask if the user wants move the target
        System.out.println("Vuoi Spostare il bersaglio?");
        System.out.println("1: Sì");
        System.out.println("2: No");

        choice = inputInt(1, 2);

        if (choice == 2)//If the user has chosen no
        {
            targetSquareToMoveBasicModeAsString=null;
            return;
        }

        move = true;


        System.out.println("Scegli un quadrato dove spostare il tuo bersaglio:");

        int w = 1;

        List<String> squaresAsString = hashMapToMovePlayers.get(targetBasicMode);

        for (String squareAsStringIterate : squaresAsString)
        {
            System.out.println(w+":"+ squareAsStringIterate);
            w++;
        }

        choice = inputInt(1, w - 1);

        targetSquareToMoveBasicModeAsString = squaresAsString.get(choice - 1);



    }

    @Override
    protected void inputAdditionalMode()
    {
        int i = 1;

        List<String> squaresToTargetForAdditionalGrenade = squaresExtraGrenadeAsString;

        System.out.println("Scegli uno square bersaglio per la granata aggiuntiva:");

        for (String squareAsStringIterate : squaresToTargetForAdditionalGrenade)
        {
            System.out.println(i + " " + squareAsStringIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);

        targetSquareExtraGrenadeAsString = squaresToTargetForAdditionalGrenade.get(choice - 1);
    }


}
