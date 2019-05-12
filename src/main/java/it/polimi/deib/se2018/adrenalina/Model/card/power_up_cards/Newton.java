package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

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
     * Return a set of all square where the player indicated can be move in an unique direction
     * @param player player to move
     * @param arena Game board where are located the square
     * @return set of all square where the player can be move in an unique direction
     */
    public Set<Square> checkMoveTarget(Player player, GameBoard arena)throws NullPointerException
    {
        if (player == null || arena == null)//Check null
            throw new NullPointerException("Parametro player o arena nullo");

        Set<Square> squaresCorrect = new LinkedHashSet<>(); //Create an empty set

        Set<Square> squares = arena.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);
        //Obtain all square reachable from where the player is located

        int x = player.getSquare().getX(); //Obtain coordinate of the square where the player is located
        int y = player.getSquare().getY();

        for (Square temp : squares) //For each square in all square reachable
        {
            if (MethodsWeapons.checkSquareOneDirectionTwoMoves(temp,x,y)) //Check if the square is valid for to use the newton card
                squaresCorrect.add(temp);//Add to the set to return
        }

        return squaresCorrect; //Return all square correct
    }




}