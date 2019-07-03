package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;

import java.util.*;

public abstract class StatePlayer {

    public abstract Set<Square> lookForRunAround(Player player);


    public abstract Set<Square> lookForGrabStuff(Player player);


    /**
     * This method will return a list of all the players a player can see. It's useful when you have to check who you can shoot.
     *
     * @param player is the player looking for shoot
     * @return a list of player the player called this method can see
     */
    public List<Player> lookForShootPeople(Player player)
    {
        List<Player> playerList = new LinkedList<>();

        playerList.addAll(player.getSquare().getRoom().getPlayerRoomList()); //adds all the players in the room

        /*
         * Now i have to check if the player is close to a door. In this case i can see all the players in this adiacent room.
         * I will implement the method in this way:
         * 1. I check if the player can move to a different room with a distance 1 using the method squareReachableNoWall
         * 2. If the player can effectively move to a different room it means it is close to a door
         * 3. I will add to the list all the players in this different room
         */

        for (Square square : player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1))
        {
            if (!(player.getSquare().getColor().equals(square.getColor()))) // if the color of the reachable square is different from the color of the square
            // where the player is this means player can see in a different room
            {
                playerList.addAll(player.getSquare().getRoom().getPlayerRoomList()); //adds all the players in the room
            }

        }

        Set<Player> playerSet = new HashSet<>();
        playerSet.addAll(playerList);

        playerList.clear();

        playerList.addAll(playerSet);
        playerList.remove(player);


        return playerList;
    }


    /**
     * This method will check all the reloadable weapons of a player. After i choose to reload a weapon i must reiterate this method.
     *
     * @param player is the player checking is weapon
     * @return a list of reloadable weapons
     */
    public List<WeaponCard> checkReload(Player player)
    {
        List<WeaponCard> reloadableWeapons = new LinkedList<>(); //This is the weaponcard list i will return
        /*
         * For each weapon i check if the player has enough ammo to reload that weapon.
         * After a player choose a weapon to reload i have to delete the ammo from the player and reiterate this method.
         */
        for (WeaponCard weaponCard: player.getWeaponCardList())
        {
            if (weaponCard.getBlueAmmoCost()<=player.getAmmoBlue() && weaponCard.getRedAmmoCost()<=player.getAmmoRed() && weaponCard.getYellowAmmoCost()<=player.getAmmoYellow() && !weaponCard.isLoaded())
            {
                reloadableWeapons.add(weaponCard);
            }
        }

        return reloadableWeapons;
    }




}
