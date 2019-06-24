package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author giovanni
 */

public class RequestRocketLauncher extends RequestInput {
    private boolean[] availableMethod;
    private List<ColorId> colorIdListBasicMode;
    private List<ColorId> colorIdListWithFragmentingWarhead;
    int xstart;
    int ystart;


    private ColorId targetPlayerBasicMode;
    private String targetSquareCoordinatesAsStringPlayerToMove;
    private String targetSquareCoordinatesAsStringTargetToMove;
    private String[] orderEffect;
    private List<String> squaresToMove = new ArrayList<>();
    private int x = 0;
    private int y = 0;
    List<ColorId> playersAfterMove = new ArrayList<>();
    List<ColorId> playersBasicMode = new ArrayList<>();

    private List<String> orderAva = new LinkedList<>();//List of support
    private List<String> orderTemp = new LinkedList<>();//Second List of support

    public RequestRocketLauncher (boolean[] availableMethod, List<ColorId> colorIdListBasicMode, List<ColorId> colorIdListWithFragmentingWarhead, int xstart, int ystart, List<String> squaresToMove, List<ColorId> playersAfterMove)
    {
        this.availableMethod = availableMethod;
        this.colorIdListBasicMode = colorIdListBasicMode;
        this.colorIdListWithFragmentingWarhead = colorIdListWithFragmentingWarhead;
        this.xstart = xstart;
        this.ystart = ystart;
        this.squaresToMove = squaresToMove;
        responseIsReady = false;
        this.playersAfterMove=playersAfterMove;

        orderAva.add("base");

        if (availableMethod[1])
        orderAva.add("con razzi portatili");

        if (availableMethod[2])
            orderAva.add("con granata a frammentazione");
    }


    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        int choice;

        terminal.addTextInput("Cosa vuoi fare?"); //Ask to user the first effect

        terminal.addOptionInput("1: Sparare");
        if (availableMethod[2])
        terminal.addOptionInput("2: Spostarti");

        if (availableMethod[2])
        {
             choice = terminal.inputInt(1, 2);

            if (choice == 1) //Ask the necessary dates to do the effect
            {
                choseTarget();
            } else {
                choseSquare();
                orderAva.remove("con razzi portatili");
                orderTemp.add("con razzi portatili");
            }
        } else {
            choice = terminal.inputInt(1, 1);

            if (choice == 1) //Ask the necessary dates to do the effect
            {
                choseTarget();
            }

        }

        int i = 1;

        for (String t:orderAva) //Ask to the user the second effect
        {
            terminal.addOptionInput(i+" : "+orderAva.get(i-1));
            i++;
        }

        choice = terminal.inputInt(1, i - 1);

        if (orderAva.get(choice-1).equals("con razzi portatili"))//Ask the necessary dates to do the effect
        {
            choseSquare();
            orderAva.remove("con razzi portatili");
            orderTemp.add("con razzi portatili");
        }
        else
        {
            choseTarget();
        }


        orderEffect = new String[orderTemp.size()]; //Creates the array that represents the order of the effects chosen by user

        for (int j = 0; j < orderEffect.length;j++)
            orderEffect[i] = orderTemp.get(i);

        responseIsReady = true;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }

    private void choseTarget() {
        List<ColorId> players;

        if (x == 0 & y == 0) //If the player didn't move in an other square
            players = colorIdListBasicMode;
        else//Else
            players = playersAfterMove;
        terminal.addTextInput("Scegli un bersaglio :");

        int i = 1;

        for (ColorId t : players)//Ask to user the target
        {
            terminal.addOptionInput(i + " : " + t);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);


        targetBasicEffect = players.get(choice - 1);
        orderAva.remove("base");
        orderTemp.add("base");

        if (availableMethod[2]) {
            terminal.addTextInput("Cosa vuoi fare:"); //Ask to user the secondary effect , if user don't select this effect it wont be inserted in plasma basicmode so it wont be called


            terminal.addOptionInput("1: modalità granata a frammentazione");
            choice = terminal.inputInt(1, 1);

            if (choice == 1) {
                terminal.addTextInput("Scegli un bersaglio :");

                int j = 1;

                for (ColorId t : colorIdListWithFragmentingWarhead)//Ask to user the target
                {
                    terminal.addOptionInput(j + " : " + t);
                    j++;
                }

                int choice2 = terminal.inputInt(1, i - 1);


                targetBasicEffect = players.get(choice2 - 1);
                orderAva.remove("con granata a frammentazione");
                orderTemp.add("con granata a frammentazione");
            }
        }
    }

    protected void choseSquare()
    {
        List<String> squares;

        squares = squaresToMove;

        terminal.addTextInput("Scegli un quadrato dove spostarti: ");

        int i = 1;

        for (String t:squares)//Ask the square at the user
        {
            terminal.addOptionInput(i+" : "+t);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        //Save the coordinate
        x = Integer.parseInt(squares.get(choice -1).substring(4,5));//Works if the coordinates are between 1 and 9
        y = Integer.parseInt(squares.get(choice -1).substring(11));

    }

}

