package it.polimi.deib.se2018.adrenalina.View;

/**
 * @author Cysko7927
 * This interface it is used to implement the different state of the server inside the virtual view during the match.
 * The server can have three states:
 * 1){@link StartLogin}
 * 2){@link AllPlayerAreActive}
 * 3){@link SomePlayerAreNotActive}
 */
public interface StateVirtualView
{
    public void insertConnection(Connection connection);
}
