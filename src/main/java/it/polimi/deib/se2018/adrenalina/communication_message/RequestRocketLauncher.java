package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author giovanni
 */

public class RequestRocketLauncher extends RequestInput
{
    private boolean[] availableMethod;
    private List<ColorId> colorIdListBasicMode;
    private HashMap<String, List<ColorId>> squaresAndTargetsRocketJump;
    private HashMap<ColorId, List<String>> hashToMovePlayerBasicMode;
    private List<String> allSquaresNoMove;



    private ColorId targetPlayerBasicMode;
    private String targetSquareCoordinatesAsStringPlayerToMove = null;
    private String targetSquareCoordinatesAsStringTargetToMove = null;
    boolean withFragWarhead = false;



    private List<String> orderAva = new LinkedList<>();//List of support
    private List<String> orderTemp = new LinkedList<>();//Second List of support

    public RequestRocketLauncher (boolean[] availableMethod, List<ColorId> colorIdListBasicMode, HashMap<String, List<ColorId>> squaresAndTargetsRocketJump, HashMap<ColorId, List<String>> hashToMovePlayerBasicMode, List<String> allSquaresNoMove)
    {
        this.availableMethod = availableMethod;
        this.colorIdListBasicMode = colorIdListBasicMode;
        this.squaresAndTargetsRocketJump = squaresAndTargetsRocketJump;
        this.hashToMovePlayerBasicMode = hashToMovePlayerBasicMode;
        this.allSquaresNoMove = allSquaresNoMove;
        responseIsReady = false;

    }


    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    {
        this.terminal=terminal;

        int choice;

        terminal.addTextInput("Cosa vuoi fare?"); //Ask to user the first effect

        terminal.addOptionInput("1: sparare");

        if (availableMethod[1])
            terminal.addOptionInput("2: spostarti");

        if (availableMethod[1])
        {
             choice = terminal.inputInt(1, 2);

            if (choice == 1) //Ask the necessary dates to do the effect
            {
                chooseTarget();

                terminal.addTextInput("Vuoi spostarti adesso?");

                terminal.addOptionInput("1: sì");
                terminal.addOptionInput("2: no");

                choice=terminal.inputInt(1,2);

                if (choice == 1)
                    moveTargetFromOriginalSquare();

            } else {
                chooseSquare();
                chooseTargetAfterMove();
            }
        } else {

            choice = terminal.inputInt(1, 1);
            chooseTarget();
        }
        responseIsReady = true;

    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");
        return new ResponseRocketLauncher(targetPlayerBasicMode, targetSquareCoordinatesAsStringPlayerToMove,  targetSquareCoordinatesAsStringTargetToMove, withFragWarhead);
    }

    private void chooseTarget() {

        List<ColorId> players = colorIdListBasicMode;

        terminal.addTextInput("Scegli un bersaglio :");

        int i = 1;

        for (ColorId colorIdIterate : players)//Ask to user the target
        {
            terminal.addOptionInput(i + " : " + colorIdIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);
        targetPlayerBasicMode = players.get(choice - 1);

        terminal.addTextInput("Vuoi spostare il bersaglio?");

        terminal.addOptionInput("1: sì");
        terminal.addOptionInput("2: no");

        int choice1 = terminal.inputInt(1, 2);

        if (choice1 == 1)
        {
            List<String> stringList = new ArrayList<>();

            int j = 1;

            terminal.addTextInput("Dove vuoi spostare il bersaglio?");

            for (String stringIterate : hashToMovePlayerBasicMode.get(targetPlayerBasicMode))
            {
                terminal.addOptionInput(j + " : " + stringIterate);
                stringList.add(stringIterate);
                j++;
            }

            int choice2 = terminal.inputInt(1, j - 1);
            targetSquareCoordinatesAsStringTargetToMove = stringList.get(choice2 - 1);
        }




        if (availableMethod[2]) {
            terminal.addTextInput("Vuoi utilizzare la modalità granata a frammentazione?"); //Ask to user the secondary effect , if user don't select this effect it wont be inserted in plasma basicmode so it wont be called

            terminal.addOptionInput("1: sì");
            terminal.addOptionInput("2: no");

            choice = terminal.inputInt(1, 2);

            if (choice == 1) {
                withFragWarhead = true;
            }
        }
    }

    protected void chooseSquare()
    {
        List<String> squares = new ArrayList<>();

        squares.addAll(squaresAndTargetsRocketJump.keySet());

        terminal.addTextInput("Scegli un quadrato dove spostarti: ");

        int i = 1;

        for (String t : squares)//Ask the square at the user
        {
            terminal.addOptionInput(i+" : "+t);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetSquareCoordinatesAsStringPlayerToMove = squares.get(choice - 1);

    }

    protected void chooseTargetAfterMove ()
    {
        List<ColorId> colorIdList = new ArrayList<>();

        colorIdList.addAll(squaresAndTargetsRocketJump.get(targetSquareCoordinatesAsStringPlayerToMove));

        int i = 1;

        terminal.addTextInput("Scegli un bersaglio :");

        for (ColorId colorIdIterate : colorIdList)//Ask to user the target
        {
            terminal.addOptionInput(i + " : " + colorIdIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);
        targetPlayerBasicMode = colorIdList.get(choice - 1);

        terminal.addTextInput("Vuoi spostare il bersaglio?");

        terminal.addOptionInput("1: sì");
        terminal.addOptionInput("2: no");

        int choice1 = terminal.inputInt(1, 2);

        if (choice1 == 1)
        {
            List<String> stringList = new ArrayList<>();

            int j = 1;

            terminal.addTextInput("Dove vuoi spostare il bersaglio?");

            for (String stringIterate : hashToMovePlayerBasicMode.get(targetPlayerBasicMode))
            {
                terminal.addOptionInput(j + " : " + stringIterate);
                stringList.add(stringIterate);
                j++;
            }

            int choice2 = terminal.inputInt(1, j - 1);
            targetSquareCoordinatesAsStringTargetToMove = stringList.get(choice2 - 1);
        }




        if (availableMethod[2]) {
            terminal.addTextInput("Vuoi utilizzare la modalità granata a frammentazione?"); //Ask to user the secondary effect , if user don't select this effect it wont be inserted in plasma basicmode so it wont be called

            terminal.addOptionInput("1: sì");
            terminal.addOptionInput("2: no");

            choice = terminal.inputInt(1, 2);

            if (choice == 1) {
                withFragWarhead = true;
            }
        }

    }

    protected void moveTargetFromOriginalSquare ()
    {

        List<String> stringList = new ArrayList<>();
        stringList.addAll(allSquaresNoMove);

        terminal.addTextInput("Scegli un quadrato dove spostarti: ");

        int i = 1;

        for (String t : stringList)//Ask the square at the user
        {
            terminal.addOptionInput(i+" : "+t);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetSquareCoordinatesAsStringPlayerToMove = stringList.get(choice - 1);
    }

}

