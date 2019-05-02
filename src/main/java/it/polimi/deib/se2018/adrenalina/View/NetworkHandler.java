package it.polimi.deib.se2018.adrenalina.View;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkHandler extends Thread
{
    private Socket clientSocket;
    ObjectOutputStream out = null;



    @Override
    public void run()
    {

    }

    public void startConnectionSocket(String ip, int port)  throws UnknownHostException
    {


          //  clientSocket = new Socket(ip, port);



    }

    public String sendMessageSocket(String msg)
    {

        return msg;
    }

    public void stopConnectionSocket() {

       // clientSocket.close();
    }
}
