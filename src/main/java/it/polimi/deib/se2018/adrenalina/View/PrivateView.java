package it.polimi.deib.se2018.adrenalina.View;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrivateView extends UnicastRemoteObject implements PrivateViewInterface
{

    public PrivateView() throws RemoteException
    {

    }

    @Override
    public void showMenu() throws RemoteException
    {
        System.out.println("Menu");
    }

    public static void main(String[] args) {
        try
        {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            PrivateViewInterface b = new PrivateView();
            Naming.rebind("rmi://localhost/myabc", b);
            System.out.println("[System] Chat Server is ready.");
        }
        catch (Exception e) {
            System.out.println("Chat Server failed: " + e);
        }
    }
}
