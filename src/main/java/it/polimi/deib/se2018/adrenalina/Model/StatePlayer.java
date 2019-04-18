package it.polimi.deib.se2018.adrenalina.Model;


import java.util.Set;

public interface StatePlayer
{


    public Set<Square> runAround();


    public Set<Square> grabStuff();


    public void shootPeople();


    public void checkReload();



}