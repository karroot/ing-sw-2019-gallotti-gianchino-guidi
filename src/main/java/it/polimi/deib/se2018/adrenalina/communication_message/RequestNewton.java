package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Cysko7927
 * This class represents the request that the conrollere sends at the client if the player decided
 * to use the Newton
 */
public class RequestNewton extends RequestInput
{
    //Attribute for the request
    Map<ColorId, List<String>> targetAndSquares;

    //Attribute for the response
    int x;
    int y;
    ColorId target;

    /**
     * Create a request message to ask the inputs at the user to use the Newton
     * @param targetAndSquares hash map with the targets and the square where they can be moved
     */
    public RequestNewton(Map<ColorId, List<String>> targetAndSquares)
    {
        this.targetAndSquares = targetAndSquares;
        responseIsReady = false;
    }

    /**
     * Ask at the user which player wants to move and in which square put him
     * @param terminal terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    { this.terminal=terminal;
        List<ColorId> targets = new ArrayList<>(targetAndSquares.keySet());

        int  k = 1;

        terminal.addTextInput("Scegli un bersaglio:");

        for (ColorId b:targets)
        {
            terminal.addOptionInput(k + ":" + b);
            k++;
        }

        int choice = terminal.inputInt(1,k-1);

        target = targets.get(choice-1);

        List<String> squares = targetAndSquares.get(target);

        terminal.addTextInput("Scegli un quadrato dove spostare il bersaglio " + target + ":");

        k = 1;

        for (String t:squares)
        {
            terminal.addOptionInput(k + ":" + t);
            k++;
        }

        choice = terminal.inputInt(1,k-1);

        x = MethodsWeapons.getXFromString(squares.get(choice-1));
        y = MethodsWeapons.getYFromString(squares.get(choice-1));

        responseIsReady = true;
    }

    /**
     * Generate the response message for the Newton with the square and the player chosen by user
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseNewton(x,y,target);
    }
}
