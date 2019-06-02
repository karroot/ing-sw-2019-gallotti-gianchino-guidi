package it.polimi.deib.se2018.adrenalina.View;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

/**
 * @author Cysko7927
 */
public class ConnectionSocket extends Connection implements Runnable
{
    private Socket socket;

    private ObjectInputStream in;

    private ObjectOutputStream out;


    /**
     * Create a connection for a player of the game
     * It being used by controller to send and receive the message for him through the network
     * If there are problems the connection will not create
     * @param socket socket of the connection
     * @param view virtual view where there is the list of connections
     * @throws Exception if there were problems with the buffer of TCP
     */
    public ConnectionSocket(Socket socket, View view) throws Exception
    {
        this.socket = socket;
        this.view = view;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream()); //Set the streamers
        active = true;

    }

    //Ask at client the name and the color of the user that will use during the match and save them
    @Override
    protected void askCredentials() throws Exception
    {
        out.writeObject(new AskCredentials());//Send the request
        ResponseCredentials credentials = (ResponseCredentials) in.readObject();//Receive the response

        name = credentials.getName();//Save the information
        player = credentials.getColor();
    }

    /**
     * Change the socket for this connection
     * @param socket new socket
     */
    public void setSocket(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream()); //Set the streamers
            active = true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            active = false;
            view.checkState();
        }

    }

    /**
     * Send a message through the socket at client
     * @param message message to send
     * @throws Exception if the socket is not active or if there were problems of reading in the buffer TCP
     */
    @Override
    public void send(MessageNet message) throws Exception
    {
        if (!active)
            throw new IllegalStateException("Connessione non attiva impossibile inviare il messaggio");

        out.writeObject(message);
        out.flush();
    }


    /**
     * It waits that a message arrives in the socket and after it returns it
     * This method suspend the caller until the message doesn't arrive
     * @return message that is arrived
     * @throws Exception if the socket is not active or if there were problems of reading in the buffer TCP
     */
    @Override
    public  MessageNet receive() throws Exception
    {
        if (!active)
            throw new IllegalStateException("Connessione non attiva impossibile inviare il messaggio");

        return (MessageNet) in.readObject();
    }

    /**
     * Close the socket with the client
     */
    @Override
    public  void closeConnection()
    {
        try
        {
            socket.close();
            active = false;
            view.checkState();
        } catch (IOException e)
        {
            return;
        }
    }


    /**
     * Does a test Ping-Pong between the server with this connection and the client
     * @return true if the test has been successful else false
     */
    @Override
    public boolean pingPongTest()
    {
        Object response;
        try
        {
            out.writeObject(new Ping());
            out.flush();
            response = in.readObject(); //If receive the pong return 1
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            active = false;
            view.checkState();
            return false;
        }


        if (response instanceof Pong)
            return true;

        active = false;
        view.checkState();
        return false;


    }
}
