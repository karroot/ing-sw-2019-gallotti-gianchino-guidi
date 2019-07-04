package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller.Afk;

import static java.lang.Thread.sleep;

/**
 * This is a static class that provides the needed methods to handle the afk timer
 * @author Cysko7927
 */
public class TimerAFK
{
    private static boolean socket;
    public  static NetworkHandlerSocket networkHandlerSocket = null;
    public static NetworkHandlerRMI networkHandlerRMI = null;
    private static Thread timer;

    /**
     * Say at the timer that it will have to use a network Handler socket to
     * send the message of Afk
     * @param nw network Handler to use sending message afk
     */
    public static void setNetworkHandlerSocket(NetworkHandlerSocket nw)
    {
        networkHandlerSocket = nw;
        socket = true;
    }

    /**
     * Say at the timer that it will have to use a network Handler RMI to
     * send the message of Afk
     * @param nw network Handler to use sending message afk
     */
    public static void setNetworkHandlerRMI(NetworkHandlerRMI nw)
    {
        networkHandlerRMI = nw;
        socket = false;
    }

    /**
     * This method starts the timer of AFK in parallel.
     * The timer being executed by a distinct thread
     * @param codeThread thread to kill if the timer is expired
     */
    public static void startTimer(Thread codeThread)
    {
        timer = new Thread(new TimerForAFK(socket ,codeThread));
        timer.start();
    }

    /**
     * This method stops the timer
     * If used then the player has done an input
     */
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

    /**
     * Create an object that contains the timer of AFK
     * @param socket say if the timer will have to use an network handler socket or not
     *               to send the message of AFK if it is expired(if it is false the timer will use RMI)
     * @param codeOfThread thread to kill if the timer is expired
     */
    public TimerForAFK(boolean socket,Thread codeOfThread)
    {
        this.timer = AppClient.timerAFK;
        this.socket = socket;
        this.codeThread = codeOfThread;
    }

    /**
     * Method executes by thread that handles the timer
     * If the timer expires the method sends a message of AFK at hte server using
     * a network Handler(Socket Or RMI)
     */
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


    /**
     * Getter for the timer duration in milliseconds
     * @return timer duration in milliseconds
     */
    public long getTimer()
    {
        return timer;
    }

}


