package it.polimi.deib.se2018.adrenalina.Model;

import java.util.*;

public class Adrenalized1 implements StatePlayer
{


    public Adrenalized1()
    {

    }

    public void lookForRunAround()
    {
        GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 3);
    }

    public void lookForGrabStuff()
    {
        GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 2);
        Player.getSquare().getAmmoTiles();
    }

    public void lookForShootPeople()
    {
        Player.getSquare().getPlayerRoomList();
    }

    public void checkReload()
    {

    }

}