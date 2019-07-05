package it.polimi.deib.se2018.adrenalina.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer
{
    static public View view;
    static public int Port = 5000; //port of the acceptor Socket(Port AcceptorRMI = Port +1)
    static public int timer = 10;//second of the timer for login
    static public boolean terinator = false;
    static public int skullCounter = 5;
    static public int codeArena = 4;

    public static void main(String[] args)
    {
        if (args.length != 4)
        {
            System.out.println("Parametri sbagliati");
            return;
        }

        AppServer.timer = Integer.parseInt(args[0]);

        if(args[1].equals("true"))
            AppServer.terinator  = true;
        else
            AppServer.terinator = false;

        if (Integer.parseInt(args[2]) >= 5 && Integer.parseInt(args[2]) <= 8)
            AppServer.skullCounter = Integer.parseInt(args[2]);

        if (Integer.parseInt(args[3]) >= 1 && Integer.parseInt(args[3]) <= 4)
            AppServer.codeArena = Integer.parseInt(args[3]);



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
