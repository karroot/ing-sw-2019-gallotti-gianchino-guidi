package it.polimi.deib.se2018.adrenalina.Model;

import java.util.Set;

public class Adrenalized2 implements StatePlayer
{


    public Adrenalized2()
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
        GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 1);
        Player.getSquare().getPlayerRoomList();
    }

    public void checkReload()
    {

    }

}