package it.polimi.deib.se2018.adrenalina.communication_message;

import java.io.Serializable;

public class ResponseCredentials extends ResponseInput implements Serializable
{
    private String name;
    private String action_hero_comment;

    public ResponseCredentials(String name, String action_hero_comment)
    {
        this.name = name;
        this.action_hero_comment = action_hero_comment;
    }

    public String getName() {
        return name;
    }

    public String getAction_hero_comment() {
        return action_hero_comment;
    }
}
