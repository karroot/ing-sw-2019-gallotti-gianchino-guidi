package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;

import java.io.IOException;

/**
 * @author Cysko7927
 * This class represent a connection between the client and the server and can be implemented in different ways
 * The implementation depends by thecnology
 */
public abstract class Connection implements Runnable
{

    protected View view;

    protected ColorId player;

    protected String name;
    protected String action_hero_comment;

    protected boolean active = true;


    //Ask at client the name and the color of the user that will use during the match and save them
    protected abstract void askCredentials() throws Exception;

    public abstract void send(MessageNet message) throws Exception;
    public abstract MessageNet receive() throws Exception;
    public abstract void closeConnection();
    public abstract boolean pingPongTest();

    /**
     * Say the action hero comment of player associated at this connection
     * @return string that represent the action hero comment
     */
    public String getAction_hero_comment() {
        return action_hero_comment;
    }

    /**
     * Say the name of player associated at this connection
     * @return name of the player
     */
    public String getName()
    {
        return name;
    }

    /**
     * Say the color of the player associated at this connection
     * @return color of player
     */
    public ColorId getPlayer()
    {
        return player;
    }

    /**
     * This method set the color of player associated at this connection
     * @param player color to set
     */
    public void setPlayer(ColorId player) {
        this.player = player;
    }

    /**
     * Say if the connection is active or not
     * @return true if the client is connected
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * It is a thread that starts when the connection will insert in the list of Connections
     * This thread sends a message to ask at client the credentials of the player (ColorId ,name)
     * It receives the credentials and it saves them
     * If there are problem sending message the connection will be no active
     */
    @Override
    public void run()
    {
        try
        {
            askCredentials();//Ask at client the name and the color of the user that will use during the match
        }
        catch (Exception e) //If there are problems
        {
            action_hero_comment = "";
            name = "";

            System.out.println(e.getMessage());//Player isn't active
            active = false;
            view.checkState();
        }
    }


}
