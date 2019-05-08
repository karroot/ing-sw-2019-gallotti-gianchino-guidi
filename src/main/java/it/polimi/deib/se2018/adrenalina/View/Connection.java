package it.polimi.deib.se2018.adrenalina.View;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import it.polimi.deib.se2018.adrenalina.View.*;

public class Connection extends Observable<String> implements Runnable
{
    private Socket socket;

    private Scanner in;

    private PrintStream out;

    private Server server;

    private String name;

    private boolean active = true;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive()
    {
        return active;
    }

    @Override
    public void run()
    {

    }

    public void send()
    {

    }

    public void asyncSend()
    {

    }

    public synchronized void closeConnection()
    {

    }

    private void close()
    {

    }
}
