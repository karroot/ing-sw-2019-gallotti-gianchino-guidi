package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.Set;

/**
 * This class implements some methods used in many status related classes.
 *
 * @author gioguidi
 */

public class StateSpecialMethods
{

    /**
     * This is a private constructor for the class.
     *
     */
    private StateSpecialMethods () { }


    /**
     * This method will check the valid squares to grab from. This means the square must contain an ammoTile (!= null) or it has avaiable weapons to grab.
     *
     * @param player is the player invoking the method
     * @param squareSet is the set of squares to check the validity
     * @return a set of valid squares to grab from
     */
    static Set<Square> checkValidSquares(Player player, Set<Square> squareSet)
    {
        Set<Square> squareSet1 = squareSet;

        for (Square square : squareSet)
        {
            if (square.isAmmoPoint)
            {
                AmmoPoint ammopoint = (AmmoPoint) square;
                if ( ammopoint.getAmmoTiles() == null)
                    squareSet1.remove(square);
            }

            if (square.isSpawnPoint)
            {
                SpawnPoint spawnPoint = (SpawnPoint) square;
                boolean validSquare = false;

                for (WeaponCard weaponCardIterate : spawnPoint.getWeaponCardList())
                {
                    if (checkGrabbable(weaponCardIterate, player))
                    {
                        validSquare = true;
                        break;
                    }
                }
                if (!validSquare)
                    squareSet1.remove(square);
            }
        }

        return squareSet1;
    }

    /**
     * Check if player can grab a weapon
     *
     * @param player player to check
     * @param weaponCard weapon to check if can be grab
     * @return true if the weapon can be grab, false if not
     */
    public static boolean checkGrabbable( WeaponCard weaponCard, Player player)
    {
        if(weaponCard.getColor().equals(Color.BLUE))
            if(player.getAmmoBlue()>= weaponCard.getBlueAmmoCost()-1 && player.getAmmoYellow() >= weaponCard.getYellowAmmoCost() && player.getAmmoRed()>=weaponCard.getRedAmmoCost())
                return true;
        if(weaponCard.getColor().equals(Color.RED))
            if(player.getAmmoBlue()>= weaponCard.getBlueAmmoCost() && player.getAmmoYellow() >= weaponCard.getYellowAmmoCost() && player.getAmmoRed()>=weaponCard.getRedAmmoCost()-1)
                return true;
        if(weaponCard.getColor().equals(Color.YELLOW))
            if(player.getAmmoBlue()>= weaponCard.getBlueAmmoCost() && player.getAmmoYellow() >= weaponCard.getYellowAmmoCost()-1 && player.getAmmoRed()>=weaponCard.getRedAmmoCost())
                return true;
        return false;
    }


    /**
     * This method checks the squares to move at distance 3.
     *
     * @param player is the player invoking the method
     * @return a set of reachable squares
     */
    static Set<Square> lookForRunAround3(Player player)
    {
        Set<Square>  squareSet;
        squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
        return squareSet;
    }


    /**
     * This method checks the squares to grab from at distance 2.
     *
     * @param player is the player invoking the method
     * @return a set of squares to grab from
     */
    static Set<Square> lookForGrab2(Player player)
    {
        Set<Square> squareSet;
        squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);
        return StateSpecialMethods.checkValidSquares(player, squareSet);
    }
}
