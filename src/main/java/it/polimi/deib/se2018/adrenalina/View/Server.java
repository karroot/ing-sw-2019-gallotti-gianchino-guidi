package it.polimi.deib.se2018.adrenalina.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
        private static final int PORT = 12345;
        private ServerSocket serverSocket;

        private ExecutorService executor =
                Executors.newFixedThreadPool(128);

        private List<Connection> connections = new ArrayList<Connection>();

        private Map<String, Connection> waitingConnection = new HashMap<>();

        private Map<Connection, Connection> playingConnection = new HashMap<>();


    private synchronized void registerConnection(Connection c)
    {
        connections.add(c);
    }

    /*

    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        Connection enemy = playingConnection.get(c);
        if(enemy != null)
            enemy.closeConnection();
        playingConnection.remove(c);
        playingConnection.remove(enemy);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }

    }

    /*
     * Mi metto in attesa di un altro giocatore

    public synchronized void rendezvous(Connection c, String name){
        waitingConnection.put(name, c);
        if(waitingConnection.size() == 2){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            RemoteView player1 = new RemoteView(new Player(keys.get(0)), keys.get(1), c1);
            RemoteView player2 = new RemoteView(new Player(keys.get(1)), keys.get(0), c2);
            Model model = new Model();
            Controller controller = new Controller(model);
            model.register(player1);
            model.register(player2);
            player1.register(controller);
            player2.register(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                Connection connection = new Connection(newSocket, this);
                registerConnection(connection);
                executor.submit(connection);//Equivalente a new Thread(c).start();
            } catch (IOException e) {
                System.out.println("Errore di connessione!");
            }
        }
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossibile inizializzare il server: " + e.getMessage() + "!");
        }
    }
     */

}
