package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Room;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    //Check if the square "e" is located to North or East or South or West of the square with coordinates x and y
    public static boolean checkSquareOneDirectionTwoMoves(Square e, int x,int y)
    {
        if ((x == e.getX() + 1 && y == e.getY() ) || (x == e.getX() + 2 && y == e.getY()))//If the square (x,y) is at north
            return true;
        else if ((x == e.getX() - 1 && y == e.getY() ) || (x == e.getX() - 2 && y == e.getY()))//If the square (x,y) is at south
            return true;
        else  if ((x == e.getX() && y == e.getY() + 1 ) || (x == e.getX() && y == e.getY() + 2))//If the square (x,y) is at East
            return true;
        else  if ((x == e.getX() && y == e.getY() - 1 ) || (x == e.getX() && y == e.getY() - 2))//If the square (x,y) is at West
            return true;
        else
            return false;
    }

    public static List<Room> roomsThatIsee(Player player)
    {
        Set<Square> temp = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(),player.getSquare().getY(),1); //Obtain the square to distance 1

        Set<ColorRoom> colors = new HashSet<>(); //Create a set of colorRoom empty

        for (Square c : temp) //Obtain the colors of the square to distance 1
        {
            colors.add(c.getColor());
        }

        return player.getSquare().getGameBoard().getRoomList()
                .stream()
                .filter(room -> colors.contains(room.getColor()))
                .collect(Collectors.toList());
    }
}
