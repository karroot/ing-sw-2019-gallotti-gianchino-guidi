package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.Afk;

import java.io.IOException;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * This is a static class that provides the needed methods to handle the afk timer
 */
public class TimerAFK
{
    private static boolean socket;
    public  static NetworkHandlerSocket networkHandlerSocket = null;
    public static NetworkHandlerRMI networkHandlerRMI = null;
    private static Thread timer;

    public static void setNetworkHandlerSocket(NetworkHandlerSocket nw)
    {
        networkHandlerSocket = nw;
        socket = true;
    }

    public static void setNetworkHandlerRMI(NetworkHandlerRMI nw)
    {
        networkHandlerRMI = nw;
        socket = false;
    }

    public static void startTimer(Thread codeThread)
    {
        timer = new Thread(new TimerForAFK(socket ,codeThread));
        timer.start();
    }

    public static void interruptTimer()
    {
        timer.interrupt();
    }
}

/**
 * This class implements a timer for AFK
 * The timer starts in parallel and if it is over kill the thread passed like parameter in its constructor
 * and uses the network Handler to send a message AFK at the server
 */
class TimerForAFK implements Runnable
{
    private boolean socket;
    private long timer;//Represent the seconds of duration of the timer
    private Thread codeThread;

    public TimerForAFK(boolean socket,Thread codeOfThread)
    {
        this.timer = AppClient.timerAFK;
        this.socket = socket;
        this.codeThread = codeOfThread;
    }

    @Override
    public void run()
    {
        try
        {
            sleep(timer);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new ThreadDeath();
        }



        codeThread.interrupt();

        try
        {
            if (socket)
                TimerAFK.networkHandlerSocket.sendMessageNet(new Afk());
            else
                TimerAFK.networkHandlerRMI.update(new Afk());
        }
        catch (Exception e)
        {

        }


    }


    public long getTimer()
    {
        return timer;
    }

}


