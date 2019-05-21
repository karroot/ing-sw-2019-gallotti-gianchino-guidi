package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Cysko7927
 */
public abstract class RequestInput extends MessageNet
{
    protected boolean responseIsReady;
    //Attribute for the request
    protected List<ColorId> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected ColorId targetBasicMode;//Target chosen for the basic mode

    //Method to handle the inputs
    abstract public void printActionsAndReceiveInput();
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

    //Ask at the user one target to hit with the weapon
    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un bersaglio:");

        for (ColorId t:playersBasicMode)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        targetBasicMode = playersBasicMode.get(anInt -1);

    }
}
