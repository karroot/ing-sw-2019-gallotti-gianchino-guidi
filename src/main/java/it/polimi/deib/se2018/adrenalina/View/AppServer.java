package it.polimi.deib.se2018.adrenalina.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer
{
    static public View view;
    final static private int Port = 5000;
    final static private int timer = 120;

    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        try
        {
            view = new View(Port,timer);
        }
        catch (Exception e)
        {
            System.out.println("Errore nel creare gli accettatori provare a cambiare il numero di porta");
            return;
        }

        executor.submit(view);

    }
}
