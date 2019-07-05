package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RequestTargettingScope  extends RequestInput{
    //Attribute for the request
    protected List<ColorId> playersBasicMode;//Targets for the basic mode
    protected List<Color> playerAmmo; // all ammo of the player
    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode
    protected Color targetAmmo;

    /**
     * this method will print all the available actions and ask the player to respond to requests
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

    /**
     * request to use the targetting scope power up
     * @param playersBasicMode set of player to add damage
     * @param playerAmmo list of ammo in which chose one to discard
     */
    public RequestTargettingScope(Set<ColorId> playersBasicMode, List<Color> playerAmmo) {
        this.playersBasicMode = new ArrayList<>(playersBasicMode);
        this.playerAmmo = playerAmmo;
    }

    /**
     * this method generate the response message ResponseTargettingScope
     * @return the Response of the Targetting Scope
     * @throws IllegalStateException if response is not ready
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseTargettingScope(targetBasicMode,targetAmmo);
    }

    protected void inputBasicMode()
    {

        int i = 1;

        terminal.addTextInput("Scegli un bersaglio:");

        for (ColorId t:playersBasicMode)
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        int anInt = terminal.inputInt(1, i - 1);


        targetBasicMode = playersBasicMode.get(anInt -1);


        i=1;
        terminal.addTextInput("Scegli una munizione:");

        for (Color t:playerAmmo)
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

         anInt = terminal.inputInt(1, i - 1);
        targetAmmo= playerAmmo.get(anInt-1);
    }


}
