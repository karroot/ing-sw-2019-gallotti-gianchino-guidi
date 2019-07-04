package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Controller.Controller;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.GenericMessage;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * This class implements the server and the virtual View
 * @author Cysko7927
 */
public class View extends Observable<ResponseInput> implements Observer<UpdateModel>,Runnable
{

    /*
    Questa è la virtual view.
    e fa anche da server
     */

    protected int PORT;
    protected Thread timer;
    protected int lenghtTimer;
    private ExecutorService executor = Executors.newFixedThreadPool(10);
    private List<Connection> connections = new LinkedList<>();
    protected  boolean creationIsFinished;
    protected boolean gameIsStarted;
    private StateVirtualView state;
    private UpdateModel lastModelUpdated; //This variable saves the last version of the model updated
    private Controller controller;
    private Thread mainThread;//Thread that executes the logic of the match on controller


    /**
     * Create a virtual view with inside a server that listen in a port and set a timeout that being used by server
     * to wait before to start the match
     * @param PORT number of port that will use the server for the socket
     * @param timer timeout used by server to wait the user after at least three user will connect.
     *              it being used by Network Handler also to wait the input of user
     */
    public View(int PORT,int timer)
    {
        this.PORT = PORT;
        this.lenghtTimer = timer;
        gameIsStarted = false;
        creationIsFinished = false;
        state = new StartLogin(this);
        controller = new Controller(AppServer.terinator,AppServer.codeArena,AppServer.skullCounter,this);
        register(controller);
        connections = Collections.synchronizedList(connections);
    }

    /**
     *The controller use this method to send a request of input a one player
     * The method does also a ping-pong test to verify that the connection is active
     * @param message message to send to ask the input
     * @param player  player that will receive the request of input
     * @throws Exception if there were problems with the sending
     */
    public void requestInput(RequestInput message,ColorId player) throws Exception //Beta Version
    {
        
        //The controller uses this method to ask an input to user
        Connection correctConnection = connections
                .stream()
                .filter(connection -> connection.getPlayer().equals(player))
                .collect(Collectors.toList())
                .get(0);


        System.out.println("Ping Pong per:"+correctConnection.getPlayer());
        final boolean esite = correctConnection.pingPongTest();

        if (esite)
        {   //If the ping pong test has been successful
            System.out.println("Ping Pong Superato");
            System.out.println("invio messaggio a :"+correctConnection.getPlayer());
            correctConnection.send(message);//Ask to send the message
        }
        else
            throw new ClosedChannelException();//Test failed = connection isn't active
        System.out.println("Richiesta inviata");
    }

    /**
     * The controller uses this method to receive the response of a request of input sand before at a player
     * This method suspend the controller until the message doesn't arrive and at the End it calls the method update
     * of the controller passing the response input
     * @param player player that send the response
     * @throws Exception if there are problem of connection
     */
    public void getResponseWithInputs(ColorId player) throws Exception //Beta Version
    {
        Connection correctConnection = connections //Find the correct connection
                .stream()
                .filter(connection -> connection.getPlayer().equals(player))
                .collect(Collectors.toList()).get(0);

        System.out.println("Attesa messaggio da parte di:"+correctConnection.getPlayer());
        notify((ResponseInput) correctConnection.receive());//Wait the message that will arrive
    }

    /**
     * This method being used by controller
     * to send a message in broadcast at all the player(Used in case of Afk for a player or disconnection)
     * @param message message to send
     */
    public void sendMessageGenericBroadcast(GenericMessage message)
    {
        //Send a message generic in broadcast at all active player
        for (Connection z: connections)
        {
            try
            {
                if (z.isActive())
                    z.send(message);
            }
            catch (Exception e)
            {
                System.out.println("Messaggio: non mandato al player:"+z.getPlayer());
                z.closeConnection();
            }
        }
    }

    /**
     * If there were changes in the model the controller use this method to send the model updated at all player active
     * The method continues to send the message until the client doesn't respond with a message of correct reception
     * About the connections that are no active the message will not send
     * @param message message to send that contain the model updated
     */
    @Override
    public void update(UpdateModel message)//Beta version(Incomplete)
    {
        //Send a message update model in broadcast at all active player
        for (Connection t: connections)
        {
            try
            {
                if (t.isActive())
                    t.send(message);
            }
            catch (Exception e)
            {
                System.out.println("Copia model: non mandata al player:"+t.getPlayer());
                t.closeConnection();
            }
        }

        lastModelUpdated = message;
    }

    /**
     * Thread that creates the acceptors Socket and RMI
     */
    @Override
    public void run()
    {
        try
        {
            executor.submit(new AcceptorSocket(this));
            System.out.println("creazione dell' Accettatore di ConnectionSocket riuscita");
        }
        catch (IOException e)
        {
            System.out.println("Errore nella creazione dell' Accettatore di ConnectionSocket");
            System.out.println("Non sarà possibile connetersi");
        }

        try
        {
            executor.submit(new AcceptorRMI(this));
            System.out.println("creazione dell' Accettatore di ConnectionRMI riuscita");
        }
        catch (Exception e)
        {
            System.out.println("Errore  nella creazione dell' Accettatore di ConnectionRMI");
            System.out.println("Non sarà possibile per i client usare RMI per connetersi");
        }

    }

    /**
     * The behavior of this method depends by state of the Virtual View:
     * 1)If the virtual view is in the initial state: the method insert the connection in the list
     * ,if there are three players connected it starts the timer to wait before to start the match
     * 2)If the match is active and there aren't player disconnected : the method doesn't insert anything
     * 3)If the match is active and there are player disconnected : the method insert a new connection only if the player
     * was connected before.
     * @param connection connection to insert
     */
    public synchronized void insertConnection(Connection connection)
    {
        state.insertConnection(connection);
        checkState();
    }

    /**
     * Get the thread that handle the timer
     * @return thread with timer
     */
    public Thread getTimer()
    {
        return timer;
    }

    /**
     * Change the thread that handle the timer
     * @param timer new thread
     */
    public void setTimer(Thread timer)
    {
        this.timer = timer;
    }

    /**
     * Obtain the object that handle the thread
     * @return object executor
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Obtain the list of all connections
     * @return list of all connections
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * Obtain the duration of the timer in seconds
     * @return duration of the timer in seconds
     */
    public int getLenghtTimer()
    {
        return lenghtTimer;
    }

    /**
     * Say if the phase of login of the player is finished
     * @return true if the phase of login of the player is finished
     */
    public boolean isCreationIsFinished() {
        return creationIsFinished;
    }

    /**
     * Obtain the last copy immutable of the model updated
     * @return object with all the information that represent the model
     */
    public UpdateModel getLastModelUpdated() {
        return lastModelUpdated;
    }

    /**
     * Change the copy immutable of the model inside the virtual view
     * @param lastModelUpdated new copy of the model
     */
    public void setLastModelUpdated(UpdateModel lastModelUpdated) {
        this.lastModelUpdated = lastModelUpdated;
    }


    //Thi method handles the state of the server in virtual view after it was created
    protected synchronized void checkState()
    {
        //The state will change if the match is started
        boolean AllPlayerAreActive = connections
                                        .stream()
                                        .filter(connection -> !connection.isActive())
                                        .collect(Collectors.toList()).isEmpty();

        if (creationIsFinished && !gameIsStarted)
        {

            gameIsStarted = true;
            System.out.println("Partita Iniziata");
            Runnable code = new Runnable()
            {
                @Override
                public void run()
                {
                    controller.startGame();
                }
            };

            mainThread = new Thread(code);
            mainThread.start();//This thread starts the match
        }


        if (creationIsFinished && AllPlayerAreActive)
            state = new AllPlayerAreActive(this);
        else if (creationIsFinished)
            state = new SomePlayerAreNotActive(this);
    }
}

/**
 * This class implements an acceptor and a builder of connection Socket
 */
class AcceptorSocket implements Runnable
{
    private ServerSocket serverSocket;
    private View view;

    public AcceptorSocket(View view) throws IOException
    {
        this.serverSocket = new ServerSocket(view.PORT);
        this.view = view;
    }

    @Override
    public void run()//Da completare
    {

        while (true)
        {
            try
            {
                Socket newSocket = serverSocket.accept();
                System.out.println();
                ConnectionSocket connectionSocket = new ConnectionSocket(newSocket,view);
                view.insertConnection(connectionSocket);
            } catch (Exception e) {
                System.out.println("Errore di connessione!" + e.getMessage());
            }
        }
    }



}

/**
 * This class implements an acceptor and a builder of connection RMI
 */
class AcceptorRMI implements Runnable
{
    private ServerSocket serverSocket;
    private View view;

    public AcceptorRMI(View view) throws Exception
    {
        this.serverSocket = new ServerSocket(view.PORT + 1);
        this.view = view;

    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Accept RMI");

                ObjectInputStream in = new ObjectInputStream(newSocket.getInputStream());
                int portNumber = in.readInt(); //Obtain the number of port of the ServerRMI
                InetAddress ip =newSocket.getInetAddress();

                Registry registry = LocateRegistry.getRegistry(ip.getHostAddress(), portNumber);

                InterfaceNetworkHandlerRMI client = (InterfaceNetworkHandlerRMI) registry.lookup("networkH");
                ConnectionRMI connection = new ConnectionRMI(view,client);
                newSocket.close();
                view.insertConnection(connection);
            } catch (Exception e) {
                System.out.println("Errore di connessione!"+ e.getMessage());
            }
        }
    }
}

/**
 * This class implements a timer
 * This timer being used by virtual view like countdown before that the match starts
 * The timer starts if there are three players connected
 */
class Timer implements Runnable
{
    private long timer;//Represent the seconds of duration of the timer
    private View view;

    public Timer(int timer,View view)
    {
        this.timer = timer;
        this.view = view;
    }

    @Override
    public void run()
    {
        try
        {
            sleep(timer*1000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new ThreadDeath();
        }

        view.creationIsFinished = true;
        view.checkState();


    }


    public long getTimer()
    {
        return timer;
    }

}


