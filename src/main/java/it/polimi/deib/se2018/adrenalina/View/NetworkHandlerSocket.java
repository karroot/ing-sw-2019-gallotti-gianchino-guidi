package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 * @author Cysko7927
 */
public class NetworkHandlerSocket extends Observable<RequestInput> implements Observer<ResponseInput>
{
    //Socket attributes
    private Socket clientSocket;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    /**
     * Create a network Handler that handles the connection between the client and the server
     * If the connection fails the handler will not create
     * @param ip IPV4 address of the server
     * @param port port TCP of the server
     * @throws UnknownHostException if the ipv4 address is wrong
     * @throws IOException if there is no connection
     */
    public void startConnectionSocket(String ip, int port)  throws UnknownHostException, IOException
    {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

    }

    /**
     * Method used by private view to ask at the network handler to send a message
     * of ResponseInput
     * It uses the method sendMessageNet.
     * @param message message of ResponseInput to send at the server
     * @throws Exception if there were problems sending the message
     */
    @Override
    public void update(ResponseInput message) throws Exception
    {
        sendMessageNet(message);
    }

    /**
     * This method send a message through the network using the socket
     * @param msg message to send
     * @throws IOException if there were problems sending the message
     */
    public void sendMessageNet(MessageNet msg) throws IOException
    {
        out.writeObject(msg);
        out.flush();
    }

    /**
     * This method suspend the caller until a message sent by server doesn't arrive
     * The network handler open the message , sees the type of message and responds in different way:
     * 1)If the type of message is "StartTurn": the network handler starts a thread that executes the
     * method "startTurn" of the privateView
     * 2)If the type of message is "UpdateModel": the network handler executes the
     * method "updateModel" of the privateView
     * 3)If the type of message is "ping": the network handler responds sending at the server a message of pong
     * 4)If the type of message is "AskCredentials": the network handler responds sending the credentials
     * of the user
     * 5)If the type of message is "RequestInput":  the network handler calls the method update of the
     * privateView so it receives the request of the server
     * @throws Exception if there were problems reading the message
     */
    public void receiveMessageNet() throws Exception //Manca il caso della richiesta delle credenziali
    {
        Object msg = in.readObject();
        out.flush();

        if (msg instanceof StartTurn)
        {
            //view.startTurn() //Fore sarà meglio farlo eseguire da un thread a parte todo
            return;
        }
        else if (msg instanceof UpdateModel)
        {
            //view.updateModel(message)
            return;
        }
        else if (msg instanceof Ping)//If the message is a ping
        {
            sendMessageNet(new Pong());//Responds with a pong
            return;
        }
        else if(msg instanceof AskCredentials)//If the message is a request of credentials
        {
            ResponseCredentials credentials = getCredentials();//Get and Send the credentials of the user
            sendMessageNet(credentials);
            return;
        }

        notify((RequestInput) msg); //notifies the privateView with the request of the server


    }

    /**
     * This method notifies the privateView to update its copy of the model with the new
     * version sent by server
     * @param message new version sent by server
     * @throws RemoteException if there were problems
     */
    public void updateModel(UpdateModel message)throws RemoteException
    {
        //view.updateModel(message)todo

    }

    /**
     * Close the socket with the server
     * @throws IOException if the connection was closed before
     */
    public void stopConnectionSocket() throws IOException
    {
        clientSocket.close();
    }

    public void showMessage()
    {

    }

    //This method takes the credentials from view and return the message Response with all credential
    private ResponseCredentials getCredentials()//Todo
    {
        return null;
    }
}
