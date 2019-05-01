package it.polimi.deib.se2018.adrenalina.View;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
public class NetworkHandler extends Thread
{
    private Socket clientSocket;





    public void startConnection(String ip, int port)  throws UnknownHostException
    {
        try
        {
            clientSocket = new Socket(ip, port);
        }
        catch (UnknownHostException e)
        {

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String sendMessage(String msg)
    {

        return msg;
    }

    public void stopConnection() {

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
    }
}