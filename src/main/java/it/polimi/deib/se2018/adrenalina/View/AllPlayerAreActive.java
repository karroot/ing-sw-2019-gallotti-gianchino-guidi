package it.polimi.deib.se2018.adrenalina.View;

/**
 * This class implement the logic of the server about the insertion of new connection
 * in case of there aren't players disconnected
 * @author Cysko7927
 */
public class AllPlayerAreActive implements StateVirtualView
{
    View view;

    /**
     * Create the object that will contain the method to insert the connections that will use by virtual view if
     * all player are connected during the match
     * @param view reference to virtual view
     */
    public AllPlayerAreActive(View view)
    {
        this.view = view;
    }

    /**
     * Close the connection and doesn't add in the list of connections
     * @param connection connection to add
     */
    @Override
    public void insertConnection(Connection connection)
    {
        System.out.println("Connessione Rifiutata");
        connection.closeConnection();
    }
}
