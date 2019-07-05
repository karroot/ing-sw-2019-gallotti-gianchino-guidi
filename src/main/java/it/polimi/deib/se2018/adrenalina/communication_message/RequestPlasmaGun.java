package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestPlasmaGun extends  RequestInput
{
    //Attribute for the request
    private boolean[] availableMethod;
    private Map<String,List<ColorId>> playersWithSquaresBasicMode;//Targets to use the PlasmaGun in all different square
    private List<String> squaresAfterBasicEffect;//Squares that the user can choice after that he has used the basic effect
    private int xStart;//Coordinates where the player is in a first moment
    private int yStart;

    //Attribute for the response
    private ColorId targetBasicEffect = null;//Target chosen for the basic mode
    private int x =0;//Coordinates for the square chosen by user in the phase glide effect
    private int y = 0;
    private String[] orderEffect;//array that represent the order of the effect chosen by user

    //Others attributes
    private List<String> orderAva = new LinkedList<>();//List of support
    private List<String> orderTemp = new LinkedList<>();//Second List of support
    private List<String> printItalian=new LinkedList<>();// list to print in italian language
    /**
     * Create a message of Request of input for weapon Shotgun
     * @param availableMethod Represent the possible mode that can be used with this weapon
     * @param playersWithSquaresBasicMode Targets to use the PlasmaGun in all different square
     * @param squaresAfterBasicEffect Squares that the user can choice after that he has used the basic effect
     * @param x Coordinate x of the square where the player is in a first moment
     * @param y Coordinate y of the square where the player is in a first moment
     */
    public RequestPlasmaGun(boolean[] availableMethod, Map<String, List<ColorId>> playersWithSquaresBasicMode, List<String> squaresAfterBasicEffect,int x,int y)
    {
        xStart = x;
        yStart = y;
        this.availableMethod = availableMethod;
        this.playersWithSquaresBasicMode = playersWithSquaresBasicMode;
        this.squaresAfterBasicEffect = squaresAfterBasicEffect;
        responseIsReady = false;

        orderAva.add("basic");
        orderAva.add("with phase glide");

        if (availableMethod[1])//If the mode with charged shot can be used
            orderAva.add("with charged shot");//Add it in the list of the possible effects
    }

    /**
     *Ask at the user which effect to use and the targets to hit
     * The user can choice the effect in three possible mode:
     * 1) to move onto a square and shoot and add damage
     * 2)shoot somebody add damage and move
     * 3) or shoot people and then move
     * @param terminal terminal that will print the text and the option input at the user
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal)
    { this.terminal=terminal;
        terminal.addTextInput("Cosa vuoi fare:"); //Ask to user the first effect
        List<String> showedAction= new LinkedList<>();
        int choice;

        if (playersWithSquaresBasicMode.get("x = " + xStart + ", y = " + yStart)!= null)
        {
            terminal.addOptionInput("1:Colpire");
            terminal.addOptionInput("2:Spostarti");
            choice = terminal.inputInt(1, 2);
        }
        else
        {
            terminal.addOptionInput("2:Spostarti");
            choice = terminal.inputInt(2, 2);
        }




        if (choice == 1) //Ask the necessary dates to do the effect
        {
            choseTarget();
        }
        else
        {
            choseSquare();
            orderAva.remove("with phase glide");
            orderTemp.add("with phase glide");
        }

        int i = 1;
        for(String s : orderAva)
        {
            if(s.equals("with phase glide"))
                printItalian.add("slittamento di fase");
            if(s.equals("with charged shot"))
                printItalian.add("colpo caricato");
        }

        terminal.addTextInput("Scegli il secondo effetto:");

        for (String t:orderAva) //Ask to the user the second effect
        {
            if(!orderAva.get(i-1).equals("with charged shot"))
            {
            terminal.addOptionInput(i+":"+orderAva.get(i-1));
                showedAction.add(t);
            i++;
            }
        }

        choice = terminal.inputInt(1, i - 1);

        if (showedAction.get(choice-1).equals("with phase glide"))//Ask the necessary dates to do the effect
        {
            choseSquare();
            orderAva.remove("with phase glide");
            orderTemp.add("with phase glide");
        }
        else
        {
            choseTarget();
        }



        orderEffect = new String[orderTemp.size()]; //Creates the array that represents the order of the effects chosen by user

        for (int j = 0;j<orderEffect.length;j++)
            orderEffect[j] = orderTemp.get(j);

        responseIsReady = true;
    }
    /**
     *Generate the response message for the PlasmaGun with all player's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");
        return new ResponsePlasmaGun(targetBasicEffect, x,  y, orderEffect);

    }

    //Ask at the user to choice a target to hit
    private void choseTarget()
    {
        List<ColorId> players;

        if (x == 0 & y == 0) //If the player didn't move in an other square
            players = playersWithSquaresBasicMode.get("x = " + xStart + ", y = " + yStart);//use the starting coordinates
        else//Else
        {
            players = playersWithSquaresBasicMode.get("x = " + x + ", y = " + y);//Use the new coordinates
            if (players== null)
            {
                terminal.showMessage("Terzo effetto non utilizzabile");
                return;
            }

        }
        terminal.addTextInput("Scegli un bersaglio :");

        int i = 1;

        for (ColorId t:players)//Ask to user the target
        {
            terminal.addOptionInput(i+" : "+t);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);


            targetBasicEffect = players.get(choice-1);
            orderAva.remove("basic");
            orderTemp.add("basic");

  if (availableMethod[1]) {
      terminal.addTextInput("Cosa vuoi fare:"); //Ask to user the secondary effect , if user don't select this effect it wont be inserted in plasma basicmode so it wont be called


      terminal.addOptionInput("1:aggiungi danno");
      terminal.addOptionInput("2: non aggiungere danno");
      choice = terminal.inputInt(1, 2);

      if (choice == 1) {
          orderAva.remove("with charged shot");
          orderTemp.add("with charged shot");
      }
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

