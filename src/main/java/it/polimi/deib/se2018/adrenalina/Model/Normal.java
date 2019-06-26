package it.polimi.deib.se2018.adrenalina.Model;


import it.polimi.deib.se2018.adrenalina.Controller.Spawn;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.*;

/**
 * This class implements the normal status of a player.
 *
 * @author giovanni
 */
public class Normal extends StatePlayer
{


    public Normal()
    {

    }

    /**
     * When the player is in the normal status he can reach Square to a max distance of 3. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move
     * @return a set of possible reachable squares with max distance 3
     */
    @Override
    public Set<Square> lookForRunAround(Player player)
    {
         Set<Square>  squareSet;
         squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
         return squareSet;
    }




    /**
     * When the player is in the normal status he can reach Square to grab stuffs with a max distance of 1. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move to grab the stuff
     * @return a set of possible reachable squares with max distance 1
     */
    @Override
    public Set<Square> lookForGrabStuff(Player player)
    {
         Set<Square> squareSet;
         squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);

         return StateSpecialMethods.checkValidSquares(player, squareSet);

    }



}