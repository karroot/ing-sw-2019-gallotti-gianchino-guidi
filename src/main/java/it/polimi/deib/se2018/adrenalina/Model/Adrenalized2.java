package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.List;
import java.util.Set;

/**
 * This class implements the adrenalized2 status of a player.
 *
 * @author giovanni
 */


public class Adrenalized2 implements StatePlayer
{


    public Adrenalized2()
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
        squareSet = gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
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
        squareSet = gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);
        return squareSet;
    }


    /**
     * This method will return a list of all the players a player can see. It's useful when you have to check who you can shoot.
     *
     * @param player is the player looking for shoot
     * @param gameBoard is needed to call the squareReachableNoWall function
     *
     * @return a list of player the player called this method can see
     */
    @Override
    public List<Player> lookForShootPeople(Player player, GameBoard gameBoard)
    {
        List<Player> playerList = null;

        try {
            playerList.addAll(player.getSquare().getRoom().getPlayerRoomList()); //adds all the players in the room
        } catch (NullPointerException e)
        {

        }

        /*
         * Now i have to check if the player is close to a door. In this case i can see all the players in this adiacent room.
         * I will implement the method in this way:
         * 1. I check if the player can move to a different room with a distance 1 using the method squareReachableNoWall
         * 2. If the player can effectively move to a different room it means it is close to a door
         * 3. I will add to the list all the players in this different room
         */

        for (Square square : gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1))
        {
            if (!(player.getSquare().getColor().equals(square.getColor()))) // if the color of the reachable square is different from the color of the square
            // where the player is this means player can see in a different room
            {
                try {
                    playerList.addAll(player.getSquare().getRoom().getPlayerRoomList()); //adds all the players in the room
                } catch (NullPointerException e)
                {

                }
            }

        }


        return playerList;
    }
    //todo in this case the player can also move before to shot. The controller will have to call the squareReachableNoWall
    // with distance 1, and then see who the player can see

    /**
     * This method will check all the reloadable weapons of a player. After i choose to reload a weapon i must reiterate this method.
     *
     * @param player is the player checking is weapon
     * @return a list of reloadable weapons
     */
    @Override
    public List<WeaponCard> checkReload(Player player)
    {
        List<WeaponCard> reloadableWeapons = null; //This is the weaponcard list i will return
        /*
         * For each weapon i check if the player has enough ammo to reload that weapon.
         * After a player choose a weapon to reload i have to delete the ammo from the player and reiterate this method.
         */
        for (WeaponCard weaponCard: player.getWeaponCardList())
        {
            if (weaponCard.getBlueAmmoCost()<=player.getAmmoBlue() && weaponCard.getRedAmmoCost()<=player.getAmmoRed() && weaponCard.getYellowAmmoCost()<=player.getAmmoYellow())
            {
                reloadableWeapons.add(weaponCard);
            }
        }

        return reloadableWeapons;
    }

}