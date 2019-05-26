package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.io.Serializable;

public class ResponseCredentials extends ResponseInput implements Serializable
{
    private ColorId color;
    private String name;

    public ResponseCredentials(ColorId color, String name) {
        this.color = color;
        this.name = name;
    }

    public ColorId getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
