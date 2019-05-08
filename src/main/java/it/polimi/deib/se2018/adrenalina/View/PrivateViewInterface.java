package it.polimi.deib.se2018.adrenalina.View;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrivateViewInterface extends Remote
{

    public void showMenu() throws RemoteException;
}
