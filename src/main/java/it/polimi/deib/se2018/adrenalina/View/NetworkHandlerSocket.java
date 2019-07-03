package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

/**
 * @author Cysko7927
 */
public class NetworkHandlerSocket extends Observable<RequestInput> implements Observer<ResponseInput>
{
    private PrivateView view;

    //Socket attributes
    private Socket clientSocket;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    //####### Threads used #######

    Runnable codeOfLogicRound = new Runnable()
    {
        @Override
        public void run()
        {
            view.startRound();
        }
    };

    Thread logicRound;

    Runnable codeOfLogicGrenade = new Runnable()
    {
        @Override
        public void run()
        {
            view.requestToUseGrenade();
        }
    };
    Thread logicGrenade;

    Runnable codeOfLogicRespawn = new Runnable()
{
    @Override
    public void run()
    {
        view.startRespawn();
    }
};
    Thread logicRespawn;

    Runnable codeOfLogicFrenesy = new Runnable()
    {
        @Override
        public void run()
        {
            view.startFrenesy(true);
        }
    };


    Runnable codeOfLogicFrenesyB = new Runnable()
    {
        @Override
        public void run()
        {
            view.startFrenesy(false);
        }
    };

    Thread logicFrenesy;

    Runnable codeOfLogicTerminator = new Runnable()
    {
        @Override
        public void run()
        {
            view.startTerminator();
        }
    };

    Thread logicTerminator;

    Runnable codeOfLogicRespawnTerminator = new Runnable()
    {
        @Override
        public void run()
        {
            view.respawnTerminator();
        }
    };

    Thread logicRespawnTerminator;

    /**
     * Create a network Handler that handles the connection between the client and the server
     * @param ip IPV4 address of the server
     * @param port port TCP of the server
     * @throws UnknownHostException if the ipv4 address is wrong
     */
    public  NetworkHandlerSocket(String ip, int port,PrivateView view)  throws IOException
    {
        logicRound = new Thread(codeOfLogicRound);
        logicGrenade = new Thread(codeOfLogicGrenade);
        logicRespawn = new Thread(codeOfLogicRespawn);
        logicFrenesy = new Thread(codeOfLogicFrenesy);

        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        this.view = view;

        register(view);

        startConnection(ip, port);
    }

    /**
     * This method create a connection TCP between the client and the server with IP address = ip and port = port
     * This method creates also a thread that executes in loop the method "receiveMessageNet" until the connection is active
     * This method can be called by view to reactive the socket after a disconnection
     * @param ip Ip address of the server
     * @param port port tcp of the process running on server
     */
    public void startConnection(String ip, int port) throws IOException
    {

        if (!clientSocket.isConnected())
        {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }

        Runnable codeOfTheThread = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while (true)
                    {
                        receiveMessageNet(); //Continue to read the buffer until the connection is active
                    }
                }
                catch (Exception e)
                {
                    view.showError("Thread che gestisce i messaggi in arrivo ha terminato la sua esecuzione");
                    view.showError("Sei stato disconesso");
                    System.out.println(e.getMessage());
                    System.out.println(e.getCause());
                    view.askReconnection();
                }


            }
        }; //Thread that read always the buffer of input of the socket and responds in different ways

        Thread handlerOfReceivingMessage = new Thread(codeOfTheThread);

        handlerOfReceivingMessage.start();//Active the thread that handle the receiving messages
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
    public synchronized void sendMessageNet(MessageNet msg) throws IOException
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
     * 6)If the type of message is "RequestToUseGrenade": the network handler starts a thread that executes the
     * method "requestToUseGrenade" of the privateView
     * 7)If the type of message is "RequestToRespawn": the network handler starts a thread that executes the
     * method "startRespawn" of the privateView
     * 8)If the type of message is "StartFrenesy" of "StartFrenesyB": the network handler starts a thread that executes the
     * method "startFrenesy" of the privateView
     * 9)If the type of message is "StartTerminator": the network handler starts a thread that executes the
     * method "startTerminator" of the privateView
     * 10)If the type of message is "RespawnTerminator": the network handler starts a thread that executes the
     * method "respawnTerminator" of the privateView
     * 11)If the type of message is "GenericMessage": the network handler using the gui or cli to print
     * the string inside the genericMessage
     * @throws Exception if there were problems reading the message
     */
    public void receiveMessageNet() throws Exception //Manca il caso della richiesta delle credenziali
    {
        Object msg = in.readObject();

        System.out.println(msg);

        if (msg instanceof RequestStartRound)
        {
            logicRound = new Thread(codeOfLogicRound);
            logicRound.start();
            return;
        }
        else if (msg instanceof UpdateModel)
        {
            updateModel((UpdateModel) msg);
            return;
        }
        else if (msg instanceof Ping)//If the message is a ping
        {
            sendMessageNet(new Pong());//Responds with a pong
            return;
        }
        else if(msg instanceof AskCredentials)//If the message is a request of credentials
        {

            view.setColorId(((AskCredentials) msg).getColorId()); //Set the color of the player
            ResponseCredentials credentials = getCredentials();//Get and Send the credentials of the user(name)
            sendMessageNet(credentials);
            return;
        }
        else if (msg instanceof RequestToUseGrenade) //If the message is a request using a grenade
        {
            logicGrenade = new Thread(codeOfLogicGrenade);
            logicGrenade.start(); //Start the thread that handles the request
            return;
        }
        else if (msg instanceof RequestToRespawn)//If the message is a request of respawn
        {
            logicRespawn = new Thread(codeOfLogicRespawn);
            logicRespawn.start(); //Start the thread that handles the respawn
            return;
        }
        else if (msg instanceof StartFrenesy || msg instanceof StartFrenesyB)
        {
            if (msg instanceof StartFrenesy)
            {
                logicFrenesy = new Thread(codeOfLogicFrenesy);
                logicFrenesy.start();
            }
            else
            {
                logicFrenesy = new Thread(codeOfLogicFrenesyB);
                logicFrenesy.start();
            }

            return;
        }
        else if (msg instanceof StartTerminator)
        {
            logicTerminator = new Thread(codeOfLogicTerminator);
            logicTerminator.start(); //Start the thread that handles the respawn
            return;
        }
        else if (msg instanceof RespawnTerminator)
        {
            logicRespawnTerminator = new Thread(codeOfLogicRespawnTerminator);
            logicRespawnTerminator.start(); //Start the thread that handles the respawn
            return;
        }
        else if (msg instanceof GenericMessage)
        {
            view.showMessage(((GenericMessage) msg).getText());
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
        view.updateModelCopy(message);
    }

    /**
     * Close the socket with the server
     * @throws IOException if the connection was closed before
     */
    public void stopConnectionSocket() throws IOException
    {
        clientSocket.close();
    }


    //This method takes the credentials from view and return the message Response with all credential
    private ResponseCredentials getCredentials()
    {
        return new ResponseCredentials(view.getName(),view.getAction_hero_comment());
    }
}
