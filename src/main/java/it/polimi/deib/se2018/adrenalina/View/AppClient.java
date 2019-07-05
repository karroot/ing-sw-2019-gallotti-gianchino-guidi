package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Track;
import it.polimi.deib.se2018.adrenalina.View.GUI.BoardGUI;
import it.polimi.deib.se2018.adrenalina.View.GUI.SetupGui;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AppClient
{

    public static String path = "/../Asset/";

    public static int timerAFK = 600000; //Timer that starts when there is an request input (ms)

    public final static Object syncSetup = new Object(); //Object used to synchronize the phase of setup to obtain the options of the client

    static private PrivateView view;


    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Parametri sbagliati");
            return;
        }

        AppClient.timerAFK = Integer.parseInt(args[0]);

        AppClient app = new AppClient();

        app.startSetup();


    }

    public void startSetup()
    {
        SetupGui setupGui;
        boolean finished = false;

        while (!finished)
        {
            finished = true;

            synchronized (AppClient.syncSetup)
            {
                setupGui = new SetupGui();

                while (!setupGui.isReady())
                {
                    try
                    {
                        AppClient.syncSetup.wait();
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            try
            {
                AppClient.view = new PrivateView(setupGui.getTechnology(),setupGui.getIP(),setupGui.getPort(),setupGui.getGui(),setupGui.getName(),setupGui.getHeroEffect());
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(setupGui.getSetupWindow(),"Impossible connetersi:Controlla le informazioni inserite");
                finished = false;
            }

        }
    }
}
