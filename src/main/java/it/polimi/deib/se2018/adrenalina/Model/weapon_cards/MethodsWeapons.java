package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
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
}
