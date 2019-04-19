package it.polimi.deib.se2018.adrenalina.Model;


import java.util.Set;

public class Normal implements StatePlayer
{


    public Normal()
    {

    }

    /**
     * it return the reachable square with a maximum distance of 3
     */
    public void lookForRunAround()
    {
         GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 3);
    }

    /**
     * it return the reachable square with a maximum distance of 1
     * then
     */
    public void lookForGrabStuff()
    {
         GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 1);
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