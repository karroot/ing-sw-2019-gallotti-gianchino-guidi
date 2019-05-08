package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.LinkedHashSet;
import java.util.Set;

public class MethodsWeapons
{
    /**
     * Return all players reachable by a square with coordinate x and y that they are at a specified distance
     * @param square starting square
     * @param distance max distance to consider for
     * @return players reachable at the specified distance
     */
    public static Set<Player> playersReachable(Square square,int distance)
    {
        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(), square.getY(),distance);//Obtain the square reachable

        Set<Player> result = new LinkedHashSet<>();

        for (Square c:squares)//For each square obtained you take all player in these squares
        {
            result.addAll(c.getPlayerList());
        }

        return result; //Return all player reachable
    }

    /**
     * Check if there is a target in each square exactly at distance 1 from a player indicated
     * @param player player where it is taken the starting square
     * @return true if there are the target
     */
    public static boolean thereIsAPlayerInEachSquare(Player player)
    {

        //Obtain the square reachable to distance 1
        Set<Square> squares = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);

        squares.remove(player.getSquare()); //Remove the starting square


        for (Square x:squares)
        {
            if (x.getPlayerList().isEmpty())//If there is a square without players
                return false; //Return false
        }

        return true; //Else return false
    }
}
