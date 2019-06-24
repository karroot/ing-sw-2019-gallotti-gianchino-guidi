package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @author Cysko7927
 */
public abstract class RequestInput extends MessageNet
{
    protected ColorId targetBasicEffect = null;//Target chosen for the basic mode
    protected Map<String,List<ColorId>> playersWithSquaresBasicMode;//Targets to use the PlasmaGun in all different square
    protected List<String> squaresAfterBasicEffect;//Squares that the user can choice after that he has used the basic effect

    protected boolean responseIsReady;




    //Method to handle the inputs
    abstract public void printActionsAndReceiveInput(Terminal terminal);
    //Method to generate the response message
    abstract public ResponseInput generateResponseMessage() throws IllegalStateException;


    //Methods that ask at the user an int between min and max and returns the integer chosen by user
    protected int inputInt(int min,int max)
    {
        boolean done = false;
        int choice = 0;

        while (!done)//While the user doesn't insert a integer valid you continue to ask a integer
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                choice = Integer.parseInt(reader.readLine());//Read an input

                if (!(choice>= min && choice<=max)) //If int digits from user isn't in the range
                    throw new IOException();//Launch exception

                done = true; //If there aren't exception use: the integer is valid
            }
            catch (IOException|NumberFormatException e) //If there are problem
            {
                System.out.println("Input non valido");//Print that the input is not valid
            }

        }

        return  choice;
    }

    //Methods that ask at the user an int contained in the list acceptedInt and returns the integer chosen by user
    protected int inputInt(List<Integer> acceptedInt)
    {
        boolean done = false;
        int choice = 0;

        while (!done)//While the user doesn't insert a integer valid you continue to ask a integer
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                choice = Integer.parseInt(reader.readLine());//Read an input

                if (!acceptedInt.contains(choice))//If int inserted from user isn't in the list of accepted integer
                    throw new IOException();//Launch exception

                done = true;//If there aren't exception use: the integer is valid
            }
            catch (IOException|NumberFormatException e)//If there are problem
            {
                System.out.println("Input non valido");//Print that the input is not valid
            }

        }

        return  choice;
    }


}
