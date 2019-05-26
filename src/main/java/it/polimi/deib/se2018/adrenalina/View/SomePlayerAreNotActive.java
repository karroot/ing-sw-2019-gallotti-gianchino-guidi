package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
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

        ColorId color = connection.getPlayer(); //Get the credentials
        String name = connection.getName();

        Connection oldConnectionSocket = null;

        for (Connection t : view.getConnections())//Get the old Connection
        {
            if (t.getPlayer().equals(color) && t.getName().equals(name))
                oldConnectionSocket = t;
        }

        if (oldConnectionSocket != null)//If the player was connected before
        {
            view.getConnections().remove(oldConnectionSocket);//Remove the old connection
            view.getConnections().add(connection);//Add the new connection to the list
        }
        else
            connection.closeConnection(); //Refuse the connection
    }
}
