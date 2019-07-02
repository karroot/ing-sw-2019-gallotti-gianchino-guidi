package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.concurrent.TimeUnit;

/**
 * This class implement the logic of the server about the insertion of new connection
 * in case of some player are disconnected
 * @author Cysko7927
 */
public class SomePlayerAreNotActive implements StateVirtualView
{
    View view;

    /**
     * Create the object that will contain the method to insert the connections that will use by virtual view,after it was created
     * @param view reference to virtual view
     */
    public SomePlayerAreNotActive(View view)
    {
        this.view = view;
    }

    /**
     * Check if the new connection is of a player that was playing before and in this case add it
     * @param connection connection to add
     */
    @Override
    public void insertConnection(Connection connection)
    {
        view.getExecutor().submit(new Thread(connection));//Run a thread to ask the credentials

        while (true) // sospendo il controller e  aspetta 200 ms e se tutti i thread del pool (executor) sono terminati restituisco true
        {
            try
            {
                if (!view.getExecutor().awaitTermination(100, TimeUnit.MILLISECONDS)) break;
            }
            catch (InterruptedException e)
            {
                connection.closeConnection(); //Refuse the connection
                Thread.currentThread().interrupt();
                return;
            }

        }

        //Get the credentials
        String name = connection.getName();
        String action = connection.getAction_hero_comment();

        Connection oldConnectionSocket = null;

        for (Connection t : view.getConnections())//Get the old Connection
        {
            if (t.getName().equals(name) && t.getAction_hero_comment().equals(action))
                oldConnectionSocket = t;
        }

        if (oldConnectionSocket != null)//If the player was connected before
        {
            view.getConnections().remove(oldConnectionSocket);//Remove the old connection
            connection.setPlayer(oldConnectionSocket.getPlayer()); //Add the color of the player
            view.getConnections().add(connection);//Add the new connection to the list
            connection.setPlayer(oldConnectionSocket.getPlayer());
        }
        else
            {
                System.out.println("Connessione rifiutata:"+connection.getName()+"Non è un player della partita in corso");
                connection.closeConnection(); //Refuse the connection
            }

    }
}
