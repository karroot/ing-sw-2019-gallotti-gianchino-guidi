package it.polimi.deib.se2018.adrenalina.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkHandler extends Thread
{
    //Socket attributes
    private Socket clientSocket;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    //RMI attributes



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

    public void sendMessageSocket(MessageSocket msg) throws IOException
    {
        out.writeObject(msg);
        out.flush();
    }

    public MessageSocket receiveMessageSocket() throws IOException, ClassNotFoundException
    {
        Object msg = in.readObject();
        out.flush();

        return (MessageSocket) msg;
    }

    public void stopConnectionSocket() throws IOException
    {
        clientSocket.close();
    }
}
