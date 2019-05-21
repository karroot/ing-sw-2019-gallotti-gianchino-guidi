package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Cysko7927
 */
public class RequestCyberblade extends RequestInput
{
    //Attribute for the request
    private boolean[] avaiableMethod;
    private Map<String,List<ColorId>> playersWithSquaresBasicMode;//Targets to use the Cyberblade in all different square
    private List<String> squaresAfterBasicEffect;//Squares that the user can choice after that he has used the basic effect
    private int xStart;//Coordinates where the player is in a first moment
    private int yStart;

    //Attribute for the response
    private ColorId targetBasicEffect = null;//Target chosen for the basic mode
    private ColorId targetForSliceEffect = null;//Target chosen for the slice and dice effect
    private int x =0;//Coordinates for the square chosen by user in the shadowstep effect
    private int y = 0;
    private String[] orderEffect;//array that represent the order of the effect chosen by user

    //Others attributes
    private List<String> orderAva = new LinkedList<>();//List of support
    private List<String> orderTemp = new LinkedList<>();//Second List of support

    /**
     * Create a message of Request of input for weapon Shotgun
     * @param avaiableMethod Represent the possible mode that can be used with this weapon
     * @param playersWithSquaresBasicMode Targets to use the Cyberblade in all different square
     * @param squaresAfterBasicEffect Squares that the user can choice after that he has used the basic effect
     * @param x Coordinate x of the square where the player is in a first moment
     * @param y Coordinate y of the square where the player is in a first moment
     */
    public RequestCyberblade(boolean[] avaiableMethod, Map<String, List<ColorId>> playersWithSquaresBasicMode, List<String> squaresAfterBasicEffect,int x,int y)
    {
        this.avaiableMethod = avaiableMethod;
        this.playersWithSquaresBasicMode = playersWithSquaresBasicMode;
        this.squaresAfterBasicEffect = squaresAfterBasicEffect;
        responseIsReady = false;
        xStart = x;
        yStart = y;
        orderAva.add("basic");
        orderAva.add("with shadowstep");

        if (avaiableMethod[2])//If the mode with slice and dice can be used
            orderAva.add("with slice and dice");//Add it in the list of the possible effects
    }

    /**
     *Ask at the user which effect to use and the targets to hit
     * The user can choice the effect in three possible mode:
     * 1) to move onto a square and whack 2 people
     * 2)whack somebody, move, and whack somebody else
     * 3) or whack 2 people and then move
     */
    @Override
    public void printActionsAndReceiveInput()
    {
        System.out.println("Cosa vuoi fare:"); //Ask to user the first effect

        System.out.println("1:Colpire");
        System.out.println("2:Spostarti");

        int choice = inputInt(1, 2);

        if (choice == 1) //Ask the necessary dates to do the effect
        {
            choseTarget();
        }
        else
        {
            choseSquare();
            orderAva.remove("with shadowstep");
            orderTemp.add("with shadowstep");
        }

        int i = 1;

        for (String t:orderAva) //Ask to the user the second effect
        {
            System.out.println(i+":"+orderAva.get(i-1));
            i++;
        }

        choice = inputInt(1, i - 1);

        if (orderAva.get(choice-1).equals("with shadowstep"))//Ask the necessary dates to do the effect
        {
            choseSquare();
            orderAva.remove("with shadowstep");
            orderTemp.add("with shadowstep");
        }
        else
        {
            choseTarget();
        }

        if (!orderAva.isEmpty()) //It there is a third effect possible
        {
            if (orderAva.get(0).equals("with shadowstep"))//Ask the necessary dates to do the effect
            {
                choseSquare();
                orderAva.remove("with shadowstep");
                orderTemp.add("with shadowstep");
            }
            else
            {
                choseTarget();
            }
        }

        orderEffect = new String[orderTemp.size()]; //Creates the array that represents the order of the effects chosen by user

        for (int j = 0;j<orderEffect.length;j++)
            orderEffect[i] = orderTemp.get(i);

        responseIsReady = true;
    }

    /**
     *Generate the response message for the Cyberblade with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        return null;
    }

    //Ask at the user to choice a target to hit
    private void choseTarget()
    {
        List<ColorId> players;

        if (x == 0 & y == 0) //If the player didn't move in an other square
            players = playersWithSquaresBasicMode.get("x = " + xStart + ",y = " + yStart);//use the starting coordinates
        else//Else
            players = playersWithSquaresBasicMode.get("x = " + x + ",y = " + y);//Use the new coordinates

            System.out.println("Scegli un bersaglio:");

            int i = 1;

            for (ColorId t:players)//Ask to user the target
            {
                System.out.println(i+":"+t);
                i++;
            }

        int choice = inputInt(1, i - 1);

        if (targetBasicEffect == null) //If this is the first target => the user has used the basic effect
        {
            targetBasicEffect = players.get(choice-1);
            orderAva.remove("basic");
            orderTemp.add("basic");
        }
        else//the user has used the with slice and dice effect
        {
            targetForSliceEffect = players.get(choice -1);
            orderAva.remove("with slice and dice");
            orderTemp.add("with slice and dice");
        }

    }

    //Ask at the user to choice a square where to move
    private void choseSquare()
    {
        List<String> squares;

        if (targetBasicEffect == null)//if the player didn't use the basic effect
            squares = new ArrayList<>(playersWithSquaresBasicMode.keySet()); //Take the squares where there are targets
        else //Else
            squares = squaresAfterBasicEffect;//Take all the possible squares

        System.out.println("Scegli un quadrato dove spostarti:");

        int i = 1;

        for (String t:squares)//Ask the square at the user
        {
            System.out.println(i+":"+t);
            i++;
        }

        int choice = inputInt(1, i - 1);

        //Save the coordinate
        x = Integer.parseInt(squares.get(choice -1).substring(4,5));//Works if the coordinates are between 1 and 9
        y = Integer.parseInt(squares.get(choice -1).substring(11));

    }
}
