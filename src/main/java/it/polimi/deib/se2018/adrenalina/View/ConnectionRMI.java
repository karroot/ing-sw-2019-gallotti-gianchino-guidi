package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.GenericMessage;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseCredentials;
import it.polimi.deib.se2018.adrenalina.communication_message.UpdateModel;

import java.io.IOException;

public class ConnectionRMI extends Connection implements Runnable
{

    InterfaceNetworkHandlerRMI client;

    public ConnectionRMI(View view,InterfaceNetworkHandlerRMI client)
    {
        this.view = view;
        this.client = client;
    }

    //Ask at client the name and the color of the user that will use during the match and save them
    @Override
    protected void askCredentials() throws IOException,ClassNotFoundException
    {
        client.receiveMessageRequest(new GenericMessage("Credentials?"));//Send the request
        ResponseCredentials credentials = (ResponseCredentials) client.getResponseMessage();//Receive the response

        name = credentials.getName();//Save the information
        player = credentials.getColor();
    }


    /**
     * Send a message through an interface remote of RMI at client
     * @param message message to send
     * @throws IOException if there were problems with the sending
     * @throws IllegalStateException if the connection is not active
     */
    @Override
    public void send(MessageNet message) throws IOException,IllegalStateException
    {
        if (!active)
            throw new IllegalStateException("Connessione non attiva impossibile inviare il messaggio");

        client.receiveMessageRequest(message);
    }

    /**
     * It invoke a method remote of NetworkHandler and after it returns a message
     * This method suspend the caller until the message doesn't arrive
     * @return message that is arrived
     * @throws ClassNotFoundException if there were problems of reading in the buffer TCP(NO RMI)
     * @throws IOException if there were problems of reading in the buffer TCP(No RMI)
     * @throws IllegalStateException if the connection is not active
     */
    @Override
    public  MessageNet receive() throws ClassNotFoundException,IOException
    {
        if (!active)
            throw new IllegalStateException("Connessione non attiva impossibile inviare il messaggio");

        return client.getResponseMessage();
    }

    /**
     * Close the connection with the client
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
     * @throws Exception if there were problems with the connections or receiving message
     */
    @Override
    public boolean pingPongTest()
    {
        GenericMessage response;

        client.receiveMessageRequest(new GenericMessage("Ping"));//Send the ping
        response = (GenericMessage) client.getResponseMessage();//Receive the pong


        if (response.getText().equals("pong"))//If you received the pong return true
            return true;

        active = false;
        view.checkState();
        return false;


    }

    public void updateModel(UpdateModel message)
    {
        client.receiveMessageRequest(message);
    }
}
