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


    }

    /**
     * it return the reachable player for shooting , checking the square in room that the player can see
     */
    public void lookForShootPeople()
    {

        for (Square varSquare : GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 1))
        {
            if (!(Player.getSquare().getColor().equals(varSquare.getColor())))
            {
                for (Square tempSquare : varSquare.getRoom().getSquareList())
                {
                tempSquare.getPlayerList();
                }
            }

            else varSquare.getPlayerList();
        }


    }

    public void checkReload()
    {

    }

}