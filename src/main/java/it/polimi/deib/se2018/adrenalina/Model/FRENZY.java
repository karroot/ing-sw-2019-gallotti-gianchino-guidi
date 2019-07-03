package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Controller.Controller;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static it.polimi.deib.se2018.adrenalina.Model.StateSpecialMethods.lookForGrab2;

/**
 * This class implements the FRENZY status of a player.
 *
 * @author giovanni
 */

public class FRENZY extends StatePlayer
{

    public FRENZY()
    {

    }

    /**
     *
     * @param player
     * @return
     */
    @Override
    public Set<Square> lookForRunAround(Player player)
    {

            Set<Square>  squareSet;
            squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 4);
            return squareSet;



    }

    /**
     *
     * @param player
     * @return
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