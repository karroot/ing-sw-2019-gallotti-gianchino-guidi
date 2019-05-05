package it.polimi.deib.se2018.adrenalina.View;

import java.io.Serializable;

public abstract class  MessageSocket implements Serializable
{

    public abstract void printActions();
    public abstract MessageSocket receiveInput(int choice);


}
