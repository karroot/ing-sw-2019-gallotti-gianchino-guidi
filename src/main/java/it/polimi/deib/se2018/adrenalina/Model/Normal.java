package it.polimi.deib.se2018.adrenalina.Model;


import java.util.Set;

import static it.polimi.deib.se2018.adrenalina.Model.StateSpecialMethods.lookForRunAround3;

/**
 * This class implements the normal status of a player.
 *
 * @author gioguidi
 */
public class Normal extends StatePlayer {


    /**
     * This is the public constructor of the class.
     *
     */
    public Normal() {

    }


    /**
     * When the player is in the normal status he can reach Square to a max distance of 3. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move
     * @return a set of possible reachable squares with max distance 3
     */
    @Override
    public Set<Square> lookForRunAround(Player player) {
        return lookForRunAround3(player);
    }



    /**
     * When the player is in the normal status he can reach Square to grab stuffs with a max distance of 1. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move to grab the stuff
     * @return a set of possible reachable squares with max distance 1
     */
    @Override
    public Set<Square> lookForGrabStuff(Player player) {
        Set<Square> squareSet;
        squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1);

        return StateSpecialMethods.checkValidSquares(player, squareSet);

    }




}