package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.*;

import java.rmi.Naming;
import java.rmi.Remote;


public class View
{

    /*
    Questa è la virtual view.
     */
    private PrivateViewInterface c;

    public View()
    {
        try
        {
            c = (PrivateViewInterface) Naming.lookup("rmi://localhost/myabc");
            System.out.println("ok1");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private ColorId playerOfRound;


    public void showMenu()
    {
        try
        {
            c.showMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ok2");
    }

    public void newGame()
    {

    }

    public void showBoard()
    {

    }

    public void startRound( Player playofround)
    {

    }

    public void showPowerUp()
    {

    }

    public void selectPowerUp()
    {

    }

    public void showWeapons()
    {

    }

    public void selectWeapon()
    {

    }

    public void showAction()
    {

    }

    public void selectAction()
    {

    }

    public void startFrenesy()
    {

    }

    public void showFinalScore()
    {

    }

    public void showMessage(String message)
    {

    }

    public void showError(String message)
    {

    }

    public void showPlayerBoard()
    {

    }

    public static void main(String[] args)
    {
        View v = new View();

        v.showMenu();

    }

}
