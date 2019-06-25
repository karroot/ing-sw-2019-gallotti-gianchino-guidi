package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

public class ConnectionRMI extends Connection implements Runnable
{

    InterfaceNetworkHandlerRMI client;

    public ConnectionRMI(View view, InterfaceNetworkHandlerRMI client)
    {
        this.view = view;
        this.client = client;
        active = true;
    }

    //Ask at client the name and send the color of the user that will use during the match and saves the name
    @Override
    protected void askCredentials() throws Exception
    {
        client.receiveMessageRequest(new AskCredentials(player));//Send the request
        ResponseCredentials credentials = (ResponseCredentials) client.getResponseMessage();//Receive the response

        name = credentials.getName();//Save the information
        action_hero_comment = credentials.getAction_hero_comment();
    }


    /**
     * Send a message through an interface remote of RMI at client
     * @param message message to send
     * @throws Exception if the connection is not active or a remote Exception was called
     */
    @Override
    public void send(MessageNet message) throws Exception
    {
        if (!active)
            throw new IllegalStateException("Connessione non attiva impossibile inviare il messaggio");

        if (message instanceof UpdateModel)
        {
            client.updateModel((UpdateModel) message);
            return;
        }



        client.receiveMessageRequest(message);
        System.out.println("Messaggio inviato");
    }

    /**
     * It invoke a method remote of NetworkHandler and after it returns a message
     * This method suspend the caller until the message doesn't arrive
     * @return message that is arrived
     * @throws Exception if the connection is not active or a remote Exception was called
     */
    @Override
    public  MessageNet receive() throws Exception
    {
        if (!active)
            throw new IllegalStateException("Connessione non attiva impossibile inviare il messaggio");

        System.out.println("......");
        return client.getResponseMessage();
    }

    /**
     * Close the connection with the client
     * This method makes the connection inactive
     */
    @Override
    public  void closeConnection()
    {
        active = false;
        view.checkState();
    }

    /**
     * Does a test Ping-Pong between the server with this connection and the client
     * @return true if the test has been successful else false
     */
    @Override
    public boolean pingPongTest()
    {
        Pong response = null;

        try
        {
            client.receiveMessageRequest(new Ping());//Send the ping
            response = (Pong) client.getResponseMessage();//Receive the pong

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            active = false;
            view.checkState();
            return false;
        }

        if (response != null && response instanceof Pong)//If you received the pong return true
            return true;

        active = false;
        view.checkState();
        return false;


    }

}
