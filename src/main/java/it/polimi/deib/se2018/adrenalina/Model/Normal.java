package it.polimi.deib.se2018.adrenalina.Model;


import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.*;


public class Normal implements StatePlayer
{


    public Normal()
    {

    }

    /**
     * When the player is in the normal status he can reach Square to a max distance of 3. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move
     * @param gameBoard is needed to call the squareReachableNoWall function
     * @return a set of possible reachable squares with max distance 3
     */
    @Override
    public Set<Square> lookForRunAround(Player player, GameBoard gameBoard)
    {
         Set<Square>  squareSet;
         squareSet = GameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
         return squareSet;
    }

    /**
     * When the player is in the normal status he can reach Square to grab stuffs with a max distance of 1. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move to grab the stuff
     * @param gameBoard is needed to call the squareReachableNoWall function
     * @return a set of possible reachable squares with max distance 1
     */
    @Override
    public Set<Square> lookForGrabStuff(Player player, GameBoard gameBoard)
    {
         Set<Square> squareSet;
         squareSet = GameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);
         return squareSet;
    }


    /**
     * it return the reachable player for shooting , checking the square in room that the player can see
     */
    @Override
    public List<Player> lookForShootPeople(Player player, GameBoard gameBoard)
    {

        for (Square varSquare : GameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1)) //for every square returned by squareReachableNoWall with distance 1
        {
            if (!(player.getSquare().getColor().equals(varSquare.getColor()))) // if the color of the reachable square is different from the color of the square where the player is this means player can see in a different room
            {
                for (Square tempSquare : varSquare.getRoom().getSquareList()) //for every square in this different room
                {
                return tempSquare.getPlayerList(); //return the playerlist of those squares
                }
            }

            else return varSquare.getPlayerList();
        }


        return null;
    }

    public void checkReload()
    {

    }

}