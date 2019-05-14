package it.polimi.deib.se2018.adrenalina.communication_message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public abstract class RequestInput extends MessageNet
{
    protected boolean responseIsReady;

    abstract public void printActionsAndReceiveInput();
    abstract public ResponseInput generateResponseMessage();


    protected int inputInt(int min,int max)
    {
        boolean done = false;
        int choice = 0;

        while (!done)
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                choice = Integer.parseInt(reader.readLine());

                if (!(choice>= min && choice<=max))
                    throw new IOException();

                done = true;
            }
            catch (IOException|NumberFormatException e)
            {
                System.out.println("Input non valido");
            }

        }

        return  choice;
    }

    protected int inputInt(List<Integer> acceptedInt)
    {
        boolean done = false;
        int choice = 0;

        while (!done)
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                choice = Integer.parseInt(reader.readLine());

                if (!acceptedInt.contains(choice))
                    throw new IOException();

                done = true;
            }
            catch (IOException|NumberFormatException e)
            {
                System.out.println("Input non valido");
            }

        }

        return  choice;
    }
}
