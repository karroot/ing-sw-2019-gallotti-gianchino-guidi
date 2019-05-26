package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkHandlerSocket implements Runnable
{
    //Socket attributes
    private Socket clientSocket;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    //RMI attributes


    //Thread that send or receive the message
    @Override
    public void run()
    {

    }

    public void startConnectionSocket(String ip, int port)  throws UnknownHostException, IOException
    {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

    }

    public void sendMessageNet(MessageNet msg) throws IOException
    {
        out.writeObject(msg);
        out.flush();
    }

    public MessageNet receiveMessageNet() throws IOException, ClassNotFoundException
    {
        Object msg = in.readObject();
        out.flush();

        return (MessageNet) msg;
    }

    public void stopConnectionSocket() throws IOException
    {
        clientSocket.close();
    }
}
