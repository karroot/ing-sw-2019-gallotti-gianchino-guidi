package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.*;

import static it.polimi.deib.se2018.adrenalina.Model.StateSpecialMethods.lookForGrab2;
import static it.polimi.deib.se2018.adrenalina.Model.StateSpecialMethods.lookForRunAround3;

/**
 * This class implements the adrenalized1 status of a player.
 *
 * @author giovanni
 */

public class Adrenalized1 extends StatePlayer
{


    /**
     * This is the public constructor of the class.
     *
     */
    public Adrenalized1()
    {

    }

    /**
     * When the player is in the adrenalized1 status he can reach Square to a max distance of 3. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move
     * @return a set of possible reachable squares with max distance 3
     */
    @Override
    public Set<Square> lookForRunAround(Player player)
    {
        return lookForRunAround3(player);
    }



    /**
     * When the player is in the adrenalized1 status he can reach Square to grab stuffs with a max distance of 2. This method make the player see all the possible reachable squares.
     *
     * @param player is the player that is looking where to move to grab the stuff
     * @return a set of possible reachable squares with max distance 2
     */
    @Override
    public Set<Square> lookForGrabStuff(Player player)
    {
        return lookForGrab2(player);

    }

}