package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.*;
import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.*;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a network handler that handles the comunication through RMI
 * @author Cysko7927
 */
public class NetworkHandlerRMI extends UnicastRemoteObject implements InterfaceNetworkHandlerRMI, Observer<ResponseInput>
{

    private PrivateView view;
    private static int registryPortNumber = 6000; //Port
    private Registry registry;
    Socket  socket;
    ObjectOutputStream out;
    private MessageNet messageToSend = null;
    private final Object msg = new Object(); //Variable used to synchronize  the methods getResponseMessage and update

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
     * Create a network handler tha handle the communication between the client and server
     * using RMI
     * This constructor instances the server RMI and do the rebind of the interface of the network handler
     * on the port 6000
     * @param view private view that will receive and will send the messages
     * @param ip IPV4 address of the server
     * @param port port TCP of the server
     * @throws IOException if there were problems of connections
     */
    public NetworkHandlerRMI(PrivateView view,String ip, int port) throws IOException
    {
        this.view = view;

        logicRound = new Thread(codeOfLogicRound);
        logicGrenade = new Thread(codeOfLogicGrenade);
        logicRespawn = new Thread(codeOfLogicRespawn);
        logicFrenesy = new Thread(codeOfLogicFrenesy);

        // Start RMI registry
        registry = LocateRegistry.createRegistry(registryPortNumber);

        //binding
        try
        {
            registry.rebind("networkH",this);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }


        socket = new Socket(ip,port+1);


        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeInt(registryPortNumber);
        out.flush();




        register(view);

    }

    /**
     * This method can be used by view to reactive RMI after a disconnection
     * @param ip IPV4 address of Server
     * @param port Port TCP of the serve
     * @throws IOException if there are problems with the connection
     */
    public void startConnection(String ip, int port) throws IOException
    {
        // Start RMI registry
        LocateRegistry.createRegistry(registryPortNumber);
        //binding
        Naming.rebind("networkH",this);

        Socket  socket = new Socket(ip,port);
        socket.close();

    }

    /**
     * this method being called by server in remote and if the message of response is ready
     * the method returns it else suspend the caller until the message is not ready
     * @return message to response
     * @throws RemoteException if there were problems with RMI
     */
    @Override
    public MessageNet getResponseMessage() throws RemoteException
    {
        synchronized (msg)
        {
            while (messageToSend == null)//If the message to send there isn't
            {
                try
                {
                    msg.wait();//Wait that it arrives
                }
                catch (InterruptedException e)
                {

                    view.showError("Thread che gestisce i messaggi in arrivo ha terminato la sua esecuzione");
                    view.showError("Sei stato disconesso");
                    view.askReconnection();
                    Thread.currentThread().interrupt();
                }
            }

            MessageNet temp = messageToSend; //Save the message to send

            messageToSend = null; //There is no more the message to send

            return temp;//And return it

            //If the the player is AFK
            //A message of timeout arrives at the server and the controller suspends the player
        }
    }

    /**
     * this method being called by server in remote passing like parameter the message to send at network Handler
     * and it open the message , sees the type of message and responds in different way:
     * 1)If the type of message is "StartTurn": the network handler starts a thread that executes the
     * method "startTurn" of the privateView
     * 2)If the type of message is "ping": the network handler responds sending at the server a message of pong
     * 3)If the type of message is "AskCredentials": the network handler responds sending the credentials
     * of the user
     * 4)If the type of message is "RequestInput":  the network handler calls the method update (using notify) of the
     * privateView so it receives the request of the server
     * 5)If the type of message is "RequestToUseGrenade": the network handler starts a thread that executes the
     * method "requestToUseGrenade" of the privateView
     * 6)If the type of message is "RequestToRespawn": the network handler starts a thread that executes the
     * method "startRespawn" of the privateView
     * 7)If the type of message is "StartFrenesy" of "StartFrenesyB": the network handler starts a thread that executes the
     * method "startFrenesy" of the privateView
     * 8)If the type of message is "StartTerminator": the network handler starts a thread that executes the
     * method "startTerminator" of the privateView
     * 9)If the type of message is "RespawnTerminator": the network handler starts a thread that executes the
     * method "respawnTerminator" of the privateView
     * 10)If the type of message is "GenericMessage": the network handler using the gui or cli to print
     * the string inside the genericMessage
     * @param message message passed by server calling in remote this method
     * @throws RemoteException if there were problems with RMI
     * @throws Exception if there were problems with RMI
     */
    @Override
    public void receiveMessageRequest(MessageNet message)throws RemoteException,Exception
    {
        if (message instanceof RequestStartRound) //If the message says to start the turn of player
        {
            logicRound = new Thread(codeOfLogicRound);
            logicRound.start();
            return;
        }
        else if(message instanceof Ping) //If the message is a ping
        {
            update(new Pong()); //Responds with a pong
            return;
        }
        else if(message instanceof AskCredentials)//If the message is a request of credentials
        {
            view.setColorId(((AskCredentials) message).getColorId()); //Set the color of the player
            ResponseCredentials credentials = getCredentials();//Get and Send the credentials of the user
            update(credentials);//Save the credentials
            return;
        }
        else if (message instanceof RequestToUseGrenade) //If the message is a request using a grenade
        {
            logicGrenade = new Thread(codeOfLogicGrenade);
            logicGrenade.start(); //Start the thread that handles the request
            return;
        }
        else if (message instanceof RequestToRespawn)//If the message is a request of respawn
        {
            logicRespawn = new Thread(codeOfLogicRespawn);
            logicRespawn.start(); //Start the thread that handles the respawn
            return;
        }
        else if (message instanceof StartFrenesy || msg instanceof StartFrenesyB)
        {
            if (message instanceof StartFrenesy)
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
        else if (message instanceof StartTerminator)
        {
            logicTerminator = new Thread(codeOfLogicTerminator);
            logicTerminator.start(); //Start the thread that handles the respawn
            return;
        }
        else if (message instanceof RespawnTerminator)
        {
            logicRespawnTerminator = new Thread(codeOfLogicRespawnTerminator);
            logicRespawnTerminator.start(); //Start the thread that handles the respawn
            return;
        }
        else if (message instanceof GenericMessage)
        {
            view.showMessage(((GenericMessage) message).getText());
            return;
        }

        notify((RequestInput) message);
    }


    /**
     * this method can be called by server in remote to ask at private view to update
     * its copy of the model with the new version sent by server
     * @param message new version sent by server
     * @throws RemoteException if there were problems with RMI
     */
    @Override //Call the method
    public void updateModel(UpdateModel message)throws RemoteException
    {
        view.updateModelCopy(message);
    }

    /**
     * Method used by private view to ask at the network handler to send a message
     * of ResponseInput
     * The method saves the message in his buffer and after this message will take by server
     * using the method "getResponseMessage"
     * @param message message of ResponseInput to send at the server
     */
    @Override
    public void update(ResponseInput message)
    {
        synchronized (msg)
        {
            messageToSend = message;
            msg.notifyAll();
        }
    }


    //This method takes the credentials from view and return the message Response with all credential
    private ResponseCredentials getCredentials()
    {
        return new ResponseCredentials(view.getName(),view.getAction_hero_comment());
    }

    //This class is observable for the message of RequestInput by VirtualView

    //IMPLEMENTATION OF THE PATTERN OBSERVABLE

    private List<Observer<RequestInput>> observers = new ArrayList<Observer<RequestInput>>(); //List of all observer,in our case will contain only the
    //private view

    /**
     * Add a new observer for message of type RequestInput
     * @param observer observer to add
     */
    public void register(Observer<RequestInput> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Remove a observer for message of type RequestInput
     * @param observer observer to remove
     */
    public void deregister(Observer<RequestInput> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    //Notify to all observers sending a new RequestInput message
    protected void notify(RequestInput message) throws Exception
    {
        synchronized (observers) {
            for(Observer<RequestInput> observer : observers)
            {
                observer.update(message);
            }
        }
    }
}
