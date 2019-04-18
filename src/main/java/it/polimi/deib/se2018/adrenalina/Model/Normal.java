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
    public Set<Square> runAround()
    {
        return GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 3);
    }

    /**
     * it return the reachable square with a maximum distance of 1
     * then
     */
    public Set<Square>,AmmoTiles grabStuff()
    {
        return GameBoard.getArena().squareReachableNoWall(Player.getSquare().getX(), Player.getSquare().getY(), 1);
         Player.getSquare().useAmmoTiles(Player);
    }

    public void shootPeople()
    {

    }

    public void checkReload()
    {

    }

}