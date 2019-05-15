package it.polimi.deib.se2018.adrenalina;

import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main
{
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Socket clientSocket;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        clientSocket = new Socket("localhost",9999);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        Object msg = in.readObject();
        out.flush();
        RequestInput temp = (RequestInput) msg;

        temp.printActionsAndReceiveInput();
    }
}
