package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.View.GUI.SetupGui;

import javax.swing.*;
import java.io.IOException;

public class AppClient
{

    public static String path ="/Users/giovanni/IdeaProjects/ing_sw_2019_gallotti_gianchino_guidi/src/main/java/it/polimi/deib/se2018/adrenalina/View/Asset/";

    public static int timerAFK = 500000000; //Timer that starts when there is an request input (ms)

    public final static Object syncSetup = new Object(); //Object used to synchronize the phase of setup to obtain the options of the client

    static private PrivateView view;

    public void tests()
    {
        //CLI d = new CLI();

        //d.inputInt(1,2);

        //SetupGui y = new SetupGui();


/*
        BoardGUI x = new BoardGUI();
        ColorId[] expOut = new ColorId[12]; //Expected output
        List<ColorId> temp1 = new LinkedList<>();
        temp1.add(ColorId.YELLOW);
        temp1.add(ColorId.YELLOW);

        expOut[0] = ColorId.PURPLE;
        expOut[1] = ColorId.PURPLE;
        expOut[2] = ColorId.BLUE;
        expOut[3] = ColorId.BLUE;
        expOut[4] = ColorId.TERMINATOR;
        expOut[5] = ColorId.GREY;
        expOut[6] = ColorId.GREY;
        expOut[7] = ColorId.PURPLE;
        expOut[8] = ColorId.BLUE;
        expOut[9] = ColorId.BLUE;
        expOut[10] = ColorId.GREY;
        expOut[11] = ColorId.GREEN;



        x.setBoardsPossible("Tua Plancia");
        x.setBoardsPossible("Plancia Giocatore:BLUE");
        x.setArena(4);
        x.setPlayerBoard(ColorId.PURPLE);
        x.setWeapon("Cyberguanto",1,true);
        x.setWeapon("Martello Ionico",2,false);
        x.setWeapon("Falce Protonica",3,true);
        x.setWeaponPointB("");
        x.setWeaponPointR("");
        x.setWeaponPointY("");
        x.setAmmoY(String.valueOf(2));
        x.setAmmoR(String.valueOf(3));
        x.setAmmoB(String.valueOf(2));
        x.setPower(211,1);
        x.setPower(212,2);
        x.setPower(213,3);
        x.setPower(22,2);
        x.setScore(34);
        x.setSkullForScore(2);
        x.setDamages(expOut);

        List<Track> temp = new ArrayList<>();

        temp.add(new Track(ColorId.BLUE,1));
        temp.add(new Track(ColorId.GREY,1));
        temp.add(new Track(ColorId.PURPLE,1));


        x.setPanelSkulls(5,temp);

        x.setTextMarker(temp1.toString());

        x.setPlayerOnSquare(8,ColorId.BLUE);
        x.setPlayerOnSquare(8,ColorId.YELLOW);
        x.setPlayerOnSquare(8,ColorId.GREY);
        x.setPlayerOnSquare(8,ColorId.GREEN);
        x.setPlayerOnSquare(8,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(8,425);

        x.setPlayerOnSquare(0,ColorId.BLUE);
        x.setPlayerOnSquare(0,ColorId.YELLOW);
        x.setPlayerOnSquare(0,ColorId.GREY);
        x.setPlayerOnSquare(0,ColorId.GREEN);
        x.setPlayerOnSquare(0,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(0,425);

        x.setPlayerOnSquare(1,ColorId.BLUE);
        x.setPlayerOnSquare(1,ColorId.YELLOW);
        x.setPlayerOnSquare(1,ColorId.GREY);
        x.setPlayerOnSquare(1,ColorId.GREEN);
        x.setPlayerOnSquare(1,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(1,425);

        x.setPlayerOnSquare(2,ColorId.BLUE);
        x.setPlayerOnSquare(2,ColorId.YELLOW);
        x.setPlayerOnSquare(2,ColorId.GREY);
        x.setPlayerOnSquare(2,ColorId.GREEN);
        x.setPlayerOnSquare(2,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(2,425);

        x.setPlayerOnSquare(3,ColorId.BLUE);
        x.setPlayerOnSquare(3,ColorId.YELLOW);
        x.setPlayerOnSquare(3,ColorId.GREY);
        x.setPlayerOnSquare(3,ColorId.GREEN);
        x.setPlayerOnSquare(3,ColorId.PURPLE);

        x.setPlayerOnSquare(4,ColorId.BLUE);
        x.setPlayerOnSquare(4,ColorId.YELLOW);
        x.setPlayerOnSquare(4,ColorId.GREY);
        x.setPlayerOnSquare(4,ColorId.GREEN);
        x.setPlayerOnSquare(4,ColorId.PURPLE);

        x.setPlayerOnSquare(5,ColorId.BLUE);
        x.setPlayerOnSquare(5,ColorId.YELLOW);
        x.setPlayerOnSquare(5,ColorId.GREY);
        x.setPlayerOnSquare(5,ColorId.GREEN);
        x.setPlayerOnSquare(5,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(5,425);

        x.setPlayerOnSquare(6,ColorId.BLUE);
        x.setPlayerOnSquare(6,ColorId.YELLOW);
        x.setPlayerOnSquare(6,ColorId.GREY);
        x.setPlayerOnSquare(6,ColorId.GREEN);
        x.setPlayerOnSquare(6,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(6,425);

        x.setPlayerOnSquare(7,ColorId.BLUE);
        x.setPlayerOnSquare(7,ColorId.YELLOW);
        x.setPlayerOnSquare(7,ColorId.GREY);
        x.setPlayerOnSquare(7,ColorId.GREEN);
        x.setPlayerOnSquare(7,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(7,425);

        x.setPlayerOnSquare(9,ColorId.BLUE);
        x.setPlayerOnSquare(9,ColorId.YELLOW);
        x.setPlayerOnSquare(9,ColorId.GREY);
        x.setPlayerOnSquare(9,ColorId.GREEN);
        x.setPlayerOnSquare(9,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(9,425);

        x.setPlayerOnSquare(10,ColorId.BLUE);
        x.setPlayerOnSquare(10,ColorId.YELLOW);
        x.setPlayerOnSquare(10,ColorId.GREY);
        x.setPlayerOnSquare(10,ColorId.GREEN);
        x.setPlayerOnSquare(10,ColorId.PURPLE);

        x.setPlayerOnSquare(11,ColorId.BLUE);
        x.setPlayerOnSquare(11,ColorId.YELLOW);
        x.setPlayerOnSquare(11,ColorId.GREY);
        x.setPlayerOnSquare(11,ColorId.GREEN);
        x.setPlayerOnSquare(11,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(11,425);


        x.clearAllSquares();

        x.setPlayerOnSquare(11,ColorId.BLUE);
        x.setPlayerOnSquare(11,ColorId.YELLOW);
        x.setPlayerOnSquare(11,ColorId.GREY);
        x.setPlayerOnSquare(11,ColorId.GREEN);
        x.setPlayerOnSquare(11,ColorId.PURPLE);
        x.setAmmoTilesOnSquare(11,425);

        x.addTextForInput("ssaa");
        x.addOptionForInput("dsadsda");
        x.addOptionForInput("adsassasad");


        x.getInputChoice();

        System.out.println("Salve");*/

    }

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
