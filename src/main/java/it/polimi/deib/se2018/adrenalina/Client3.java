package it.polimi.deib.se2018.adrenalina;

import it.polimi.deib.se2018.adrenalina.View.AppClient;
import it.polimi.deib.se2018.adrenalina.View.GUI.SetupGui;
import it.polimi.deib.se2018.adrenalina.View.PrivateView;

import javax.swing.*;
import java.io.IOException;

public class Client3
{
    public static String path = "C:\\Users\\Cysko7927\\IdeaProjects\\ing-sw-2019-gallotti-gianchino-guidi\\src\\main\\java\\it\\polimi\\deib\\se2018\\adrenalina\\View\\Asset\\";

    public static int timerAFK = 120000; //Timer that starts when there is an request input (ms)

    public final static Object syncSetup = new Object(); //Object used to synchronize the phase of setup to obtain the options of the client

    static private PrivateView view;


    public static void main(String[] args)
    {
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
                Client3.view = new PrivateView(setupGui.getTechnology(),setupGui.getIP(),setupGui.getPort(),setupGui.getGui(),setupGui.getName(),setupGui.getHeroEffect());
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(setupGui.getSetupWindow(),"Impossible connetersi:Controlla le informazioni inserite");
                finished = false;
            }

        }
    }
}
