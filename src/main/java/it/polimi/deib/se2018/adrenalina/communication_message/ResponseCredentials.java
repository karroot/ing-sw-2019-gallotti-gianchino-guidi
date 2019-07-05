package it.polimi.deib.se2018.adrenalina.communication_message;

import java.io.Serializable;

/**
 * This class represents the response message that the client will send at the server to say
 * the name and the hero comment of the user that has connected recently
 * @author Cysko7927
 */
public class ResponseCredentials extends ResponseInput implements Serializable
{
    private String name;
    private String action_hero_comment;

    /**
     * Create the response message that will contain the name  and the action hero comment
     * @param name name of the user
     * @param action_hero_comment hero comment of the user
     */
    public ResponseCredentials(String name, String action_hero_comment)
    {
        this.name = name;
        this.action_hero_comment = action_hero_comment;
    }

    /**
     * Getter for the name of the user
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the hero comment of the user
     * @return hero comment of the user
     */
    public String getAction_hero_comment() {
        return action_hero_comment;
    }
}
