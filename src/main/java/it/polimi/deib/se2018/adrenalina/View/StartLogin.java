package it.polimi.deib.se2018.adrenalina.View;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.concurrent.TimeUnit;

/**
 * @author Cysko7927
 */
public class StartLogin implements StateVirtualView
{
    View view;

    ColorId[] allColors;

    /**
     * Create the object that will contain the method to insert the connections that will use by virtual view,after it was created
     * @param view reference to virtual view
     */
    public StartLogin(View view)
    {
        allColors = new ColorId[5];

        allColors[0] = ColorId.BLUE;
        allColors[1] = ColorId.YELLOW;
        allColors[2] = ColorId.GREY;
        allColors[3] = ColorId.GREEN;
        allColors[4] = ColorId.PURPLE;

        this.view = view;
    }

    /**
     * insert the connection in the list of connections in the virtual view
     * If there are more of three player connected it starts also the timer in view to wait before to start the match
     * @param connection connection to add
     */
    @Override
    public void insertConnection(Connection connection)
    {
        if (view.getConnections().size() >= 5)//If there are already five players
        {
            view.creationIsFinished = true;//change the state of the virtual view to start the match
            view.checkState();
            connection.closeConnection();//Refuse the connection
            return;//Stop
        }



        if (view.getTimer() != null && view.getTimer().isAlive()) //If the timer is on
            view.getTimer().interrupt();//Interrupt it

        connection.setPlayer(allColors[view.getConnections().size()]); //Set the color of the player

        view.getConnections().add(connection);//Add the connection to the list
        view.getExecutor().submit(new Thread(connection));//Run a thread to get the credentials of the player

        try
        {
            Thread.sleep(400);
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }

        try
        {
            while (view.getExecutor().awaitTermination(100, TimeUnit.MILLISECONDS))//Wait to obtain the credentials
            {

            }
        }
        catch (InterruptedException e)
        {
            //Do nothing
        }

        System.out.println("Player:"+connection.getName()+" "+connection.getPlayer()+" " + connection.getAction_hero_comment()+" si è connesso");

        //if there are more of three player do to start the timer in an other thread
        if (view.getConnections().size() >= 3 && view.getConnections().size() <= 4 && !view.creationIsFinished )
        {
            view.setTimer(new  Thread(new Timer(view.lenghtTimer,view)));
            view.getTimer().start();
        }

        if (view.getConnections().size() == 5) //If now there are 5 player
        {
            view.creationIsFinished = true;//change the state of the virtual view to start the match
            view.checkState();
        }

    }
}
