package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.CardinalDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the request that the controller sends to the client if the player decided to use the weapon Flamethrower
 *
 * @author gioguidi
 *
 */
public class RequestFlamethrower extends WeaponWithModeAlternative
{

    //Attributes for the request
    private HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapForModes;

    //Attributes for the response
    private ColorId targetBasicMode1 = null;
    private ColorId targetBasicMode2 = null;
    private ColorId targetBarbecueMode;
    private CardinalDirection targetDirectionBarbecueMode;


    /**
     * This method implement the request for the weapon.
     *
     * @param availableMethod is a vector of boolean that indicates the available mode of the weapon
     * @param hashMapForModes is an hashmap of cardinaldirection and a vector of arraylist of color of the targets in that direction
     */
    public RequestFlamethrower (boolean [] availableMethod, HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapForModes)
    {
        this.availableMethod = availableMethod;
        this.nameAlternaivemode = "modalità barbecue";
        this.hashMapForModes = hashMapForModes;
        responseIsReady = false;
    }

    /**
     * This method generates the response message for the weapon with all the choices of the player
     *
     * @return the response message
     */
    @Override
    public ResponseInput generateResponseMessage() {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi.");

        if (mode)
            return new ResponseFlamethrower(targetDirectionBarbecueMode, targetBarbecueMode);

        return new ResponseFlamethrower(targetBasicMode1, targetBasicMode2);
    }


    /**
     * This method sets the targets to use the alternative mode. The player will choose the input from the possible targets value.
     */
    @Override
    protected void inputAlternativeMode()
    {
        int i = 1;

        List<CardinalDirection> cardinalDirections = new ArrayList<>();
        cardinalDirections.addAll(hashMapForModes.keySet());

        terminal.addTextInput("Scegli una direzione bersaglio:");

        for (CardinalDirection cardinalDirectionIterate : cardinalDirections) {
            terminal.addOptionInput(i + ": " + cardinalDirectionIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);
        targetDirectionBarbecueMode = cardinalDirections.get(choice - 1);

        List<ColorId>[] colorIdTargets = hashMapForModes.get(targetDirectionBarbecueMode);

        targetBarbecueMode = colorIdTargets[0].get(0);

        responseIsReady = true;

    }

    /**
     * This method  the targets to use the basic mode. The player will choose the input from the possible targets value.
     */
    @Override
    protected void inputBasicMode()
    {
        int i = 1;

        List<CardinalDirection> cardinalDirections = new ArrayList<>();
        cardinalDirections.addAll(hashMapForModes.keySet());

        terminal.addTextInput("Scegli una direzione bersaglio:");

        for (CardinalDirection cardinalDirectionIterate : cardinalDirections) {
            terminal.addOptionInput(i + "  :  " + cardinalDirectionIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);
        CardinalDirection targetDirectionBasicMode = cardinalDirections.get(choice - 1);

        List<ColorId>[] colorIdTargets = hashMapForModes.get(targetDirectionBasicMode);

        int j = 1;

        terminal.addTextInput("Scegli il primo player bersaglio:");

        for (ColorId colorIdIterate : colorIdTargets[0])
        {
            terminal.addOptionInput(j + ": " + colorIdIterate);
            j++;
        }

        int choice1 = terminal.inputInt(1, j - 1);

        targetBasicMode1 = colorIdTargets[0].get(choice1 - 1);

        if (!colorIdTargets[1].isEmpty())
        {
            int k = 1;

            terminal.addTextInput("Scegli il secondo player bersaglio:");

            for (ColorId colorIdIterate : colorIdTargets[1])
            {
                terminal.addOptionInput(k + ": " + colorIdIterate);
                j++;
            }

            int choice2 = terminal.inputInt(1, k - 1);

            targetBasicMode2 = colorIdTargets[1].get(choice2 - 1);

        }

        responseIsReady = true;
    }

}
