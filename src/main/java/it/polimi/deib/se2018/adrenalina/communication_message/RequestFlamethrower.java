package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.CardinalDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author giovanni
 */
public class RequestFlamethrower extends WeaponWithModeAlternative
{

    //Attributes for the request
    HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapForModes;

    //Attributes for the response
    private ColorId targetBasicMode1 = null;
    private ColorId targetBasicMode2 = null;
    private ColorId targetBarbecueMode;
    private CardinalDirection targetDirectionBarbecueMode;


    public RequestFlamethrower (boolean [] availableMethod, HashMap<CardinalDirection, ArrayList<ColorId>[]> hashMapForModes)
    {
        this.availableMethod = availableMethod;
        this.nameAlternaivemode = "modalità barbecue";
        this.hashMapForModes = hashMapForModes;
        responseIsReady = false;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseFlamethrower(targetDirectionBarbecueMode, targetBarbecueMode);

        return new ResponseFlamethrower(targetBasicMode1, targetBasicMode2);
    }


    @Override
    protected void inputAlternativeMode()
    {
        int i = 1;

        List<CardinalDirection> cardinalDirections = new ArrayList<>();
        cardinalDirections.addAll(hashMapForModes.keySet());

        terminal.addTextInput("Scegli una direzione bersaglio:");

        for (CardinalDirection cardinalDirectionIterate : cardinalDirections) {
            terminal.addOptionInput(i + " " + cardinalDirectionIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);
        targetDirectionBarbecueMode = cardinalDirections.get(choice - 1);

        List<ColorId>[] colorIdTargets = hashMapForModes.get(targetDirectionBarbecueMode);

        targetBarbecueMode = colorIdTargets[0].get(0);

        responseIsReady = true;


    }

    @Override
    protected void inputBasicMode()
    {
        int i = 1;

        List<CardinalDirection> cardinalDirections = new ArrayList<>();
        cardinalDirections.addAll(hashMapForModes.keySet());

        terminal.addTextInput("Scegli una direzione bersaglio:");

        for (CardinalDirection cardinalDirectionIterate : cardinalDirections) {
            terminal.addOptionInput(i + " " + cardinalDirectionIterate);
            i++;
        }

        int choice = inputInt(1, i - 1);
        CardinalDirection targetDirectionBasicMode = cardinalDirections.get(choice - 1);

        List<ColorId>[] colorIdTargets = hashMapForModes.get(targetDirectionBasicMode);

        int j=1;

        terminal.addTextInput("Scegli il primo player bersaglio:");

        for (ColorId colorIdIterate : colorIdTargets[0]) {
            terminal.addOptionInput(i + " " + colorIdIterate);
            j++;
        }

        int choice1 = inputInt(1, i - 1);

        targetBasicMode1 = colorIdTargets[0].get(choice - 1);

        if (!colorIdTargets[1].isEmpty())
        {
            int k = 1;

            terminal.addTextInput("Scegli il secondo player bersaglio:");

            for (ColorId colorIdIterate : colorIdTargets[1]) {
                terminal.addOptionInput(i + " " + colorIdIterate);
                j++;
            }

            int choice2 = inputInt(1, i - 1);

            targetBasicMode2 = colorIdTargets[0].get(choice - 1);

        }

        responseIsReady = true;
    }

}
