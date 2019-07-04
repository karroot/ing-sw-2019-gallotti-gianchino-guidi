package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Controller.Controller;

import java.util.Set;

import static it.polimi.deib.se2018.adrenalina.Model.StateSpecialMethods.lookForGrab2;

/**
 * This class implements the FRENZY status of a player.
 *
 * @author giovanni
 */

public class FRENZY extends StatePlayer
{

    /**
     * This is the public constructor of the class.
     *
     */
    public FRENZY()
    {

    }

    /**
     * This method checks the possible square to move into (distance 4)
     *
     * @param player is the player invoking the method.
     * @return a set of square reachable
     */
    @Override
    public Set<Square> lookForRunAround(Player player)
    {
            Set<Square>  squareSet;
            squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 4);
            return squareSet;
    }

    /**
     * This method checks the squares to grab from in the FRENZY status.
     *
     * @param player is the player invoking the method
     * @return a set of valid squares
     */
    @Override
    public Set<Square> lookForGrabStuff(Player player)
    {
        if (Controller.first)
        {
            Set<Square> squareSet;
            squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
            return squareSet;
        }
        else
        {
            return lookForGrab2(player);
        }

    }

}