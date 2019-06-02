package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public class Newton extends PowerUpCard
{

    public Newton(Color color, int idPU) throws NullPointerException
    {
        super(color, idPU);
    }

    /**
     * To use the newton card
     * Move a player located in a square in an other square
     * (Requires) The coordinates must be such that the player will be moved in an unique direction(North,East,South,West)
     * @param player player to move
     * @param x coordinate x of the square
     * @param y coordinate y of the square
     */

    public void usePowerUp(Player player, int x, int y)
    {
        moveTarget(player,x,y);//Move player in the square with coordinates x and y
    }

    /**
     * Return a hash map that has like key a player and value all the coordinates of the squares where the player
     * can be moved
     * @param arena Game board where are located the square
     * @return hash map to insert in a message of request
     */
    public Map<ColorId,List<String>> checkMoveTarget(GameBoard arena)throws NullPointerException
    {
        if (player == null || arena == null)//Check null
            throw new NullPointerException("Parametro player o arena nullo");

        Set<Square> squaresCorrect = new LinkedHashSet<>(); //Create an empty set
        Map<ColorId,List<String>> result = new HashMap<>();

        for (Player t:arena.getAllPlayer()) //For each player in the match
        {
            if (!t.getColor().equals(player.getColor())) //if the player taken isn't the one who owns the card
            {
                Set<Square> squares = arena.getArena().squareReachableNoWall(t.getSquare().getX(), t.getSquare().getY(), 2);
                //Obtain all square reachable from where the player is located

                int x = t.getSquare().getX(); //Obtain coordinate of the square where the player is located
                int y = t.getSquare().getY();

                for (Square temp : squares) //For each square in all square reachable
                {
                    if (MethodsWeapons.checkSquareOneDirectionTwoMoves(temp,x,y) && !t.getSquare().equals(temp)) //Check if the square is valid for to use the newton card
                        squaresCorrect.add(temp);//Add to the set
                }

                //Add the player and his square reachable in the hash map
                result.putIfAbsent(t.getColor(),squaresCorrect.stream().map(Square::toStringCoordinates).collect(Collectors.toList()));

                squaresCorrect.clear();
            }
        }


        return result; //Return all square correct
    }




}