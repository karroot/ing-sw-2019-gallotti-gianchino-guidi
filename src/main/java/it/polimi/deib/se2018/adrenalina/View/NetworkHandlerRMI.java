package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cysko7927
 */
public class NetworkHandlerRMI extends UnicastRemoteObject implements InterfaceNetworkHandlerRMI, Observer<ResponseInput>
{

    static int registryPortNumber = 5000; //Port
    private MessageNet messageToSend = null;

    /**
     * Create a network handler tha handle the communication between the client and server
     * using RMI
     * This constructor instances the server RMI and do the rebind of the interface of the network handler
     * on the port 5000
     * @throws RemoteException if there were problems with RMI
     * @throws MalformedURLException if there were problems with RMI
     */
    protected NetworkHandlerRMI() throws RemoteException, MalformedURLException
    {
        // Start RMI registry
        LocateRegistry.createRegistry(registryPortNumber);
        // Effettuiamo il bind
        Naming.rebind("networkH",this);

        System.out.println("Server RMI funzionante"); //Verrà stampato da un interfaccia grafica todo
    }

    /**
     * this method being called by server in remote and if the message of response is ready
     * the method returns it else suspend the caller until the message is not ready
     * @return message to response
     * @throws RemoteException if there were problems with RMI
     */
    @Override
    public MessageNet getResponseMessage() throws RemoteException
    {
        synchronized (messageToSend)
        {
            if (messageToSend == null)//If the message to send there isn't
            {
                try
                {
                    messageToSend.wait();//Wait that it arrives
                }
                catch (InterruptedException e)
                {

                    return new TimeoutUser();//If the view interrupts the thread because the player is FK
                    //A message of timeout arrives at the server and the controller suspends the player
                }
            }

            MessageNet temp = messageToSend; //Save the message to send

            messageToSend = null; //There is no more the message to send

            return temp;//And return it
        }
    }

    /**
     * this method being called by server in remote passing like parameter the message to send at network Handler
     * and it open the message , sees the type of message and responds in different way:
     * 1)If the type of message is "StartTurn": the network handler starts a thread that executes the
     * method "startTurn" of the privateView
     * 2)If the type of message is "ping": the network handler responds sending at the server a message of pong
     * 3)If the type of message is "AskCredentials": the network handler responds sending the credentials
     * of the user
     * 4)If the type of message is "RequestInput":  the network handler calls the method update (using notify) of the
     * privateView so it receives the request of the server
     * @param message message passed by server calling in remote this method
     * @throws RemoteException if there were problems with RMI
     * @throws Exception if there were problems with RMI
     */
    @Override
    public void receiveMessageRequest(MessageNet message)throws RemoteException,Exception
    {
        if (message instanceof StartTurn) //If the message says to start the turn of player
        {
            //view.startTurn() //Fore sarà meglio farlo eseguire da un thread a parte todo
            return;
        }
        else if(message instanceof Ping) //If the message is a ping
        {
            update(new Pong()); //Responds with a pong
            return;
        }
        else if(message instanceof AskCredentials)//If the message is a request of credentials
        {
            ResponseCredentials credentials = getCredentials();//Get and Send the credentials of the user
            update(credentials);
            return;
        }
        notify((RequestInput) message);
    }

    /**
     * this method can be called by server in remote to ask at private view to update
     * its copy of the model with the new version sent by server
     * @param message new version sent by server
     * @throws RemoteException if there were problems with RMI
     */
    @Override //Call the method
    public void updateModel(UpdateModel message)throws RemoteException
    {
        //view.updateModel(message) todo

    }

    /**
     * Method used by private view to ask at the network handler to send a message
     * of ResponseInput
     * The method saves the message in his buffer and after this message will take by server
     * using the method "getResponseMessage"
     * @param message message of ResponseInput to send at the server
     * @throws Exception
     */
    @Override
    public void update(ResponseInput message) throws Exception
    {
        synchronized (messageToSend)
        {
            messageToSend = message;
            messageToSend.notifyAll();
        }
    }


    //This method takes the credentials from view and return the message Response with all credential
    private ResponseCredentials getCredentials()
    {
        return null;
    }

    //This class is observable for the message of RequestInput by VirtualView

    //IMPLEMENTATION OF THE PATTERN OBSERVABLE

    private List<Observer<RequestInput>> observers = new ArrayList<Observer<RequestInput>>();

    public void register(Observer<RequestInput> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     *
     * @param observer
     */
    public void deregister(Observer<RequestInput> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    //Notify to all observers
    protected void notify(RequestInput message) throws Exception
    {
        synchronized (observers) {
            for(Observer<RequestInput> observer : observers){
                observer.update(message);
            }
        }
    }
}
