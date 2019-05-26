package it.polimi.deib.se2018.adrenalina.View;


/**
 * @author Cysko7927
 */
public class StartLogin implements StateVirtualView
{
    View view;

    /**
     * Create the object that will contain the method to insert the connections that will use by virtual view,after it was created
     * @param view reference to virtual view
     */
    public StartLogin(View view)
    {
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
            connection.closeConnection();//Refuse the connection
            return;//Stop
        }


        if (view.getTimer().isAlive()) //If the timer is on
            view.getTimer().interrupt();//Interrupt it

        view.getConnections().add(connection);//Add the connection to the list
        view.getExecutor().submit(new Thread(connection));//Run a thread to get the credentials of the player

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
