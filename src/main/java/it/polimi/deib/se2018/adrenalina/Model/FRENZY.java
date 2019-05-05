package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class implements the FRENZY status of a player.
 *
 * @author giovanni
 */

//todo manca doc

public class FRENZY implements StatePlayer
{

    public FRENZY()
    {

    }

    //qui va implementato un po' diverso. Bisognerebbe prima far scegliere se si vuole muovere per sparare o raccogliere e poi lanciare il metodo opportuno.
    // Forse avrebbe più senso implementarlo a parte

    //Se primo: muovi 2 ricarica e spara OPPURE muove 3 e raccogli

    //se non primo: muovi 1, ricarica e spara OPPURE muovi 4 OPPURE muovi 2 e raccogli
    //todo documentazione


    @Override
    public Set<Square> lookForRunAround(Player player, GameBoard gameBoard)
    {
        if (!player.isFirst())
        {
            Set<Square>  squareSet;
            squareSet = gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 4);
            return squareSet;

        }
        else
        {
           return null; //todo qui non si può chiamare se non primo. Mettere errore?
        }
    }

    @Override
    public Set<Square> lookForGrabStuff(Player player, GameBoard gameBoard)
    {
        if (player.isFirst())
        {
            Set<Square> squareSet;
            squareSet = gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
            return squareSet;
        }
        else
        {
            Set<Square> squareSet;
            squareSet = gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);
            return squareSet;
        }

    }

    @Override
    public List<Player> lookForShootPeople(Player player, GameBoard gameBoard)
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

        for (Square square : gameBoard.getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 1))
        {
            if (!(player.getSquare().getColor().equals(square.getColor()))) // if the color of the reachable square is different from the color of the square
            // where the player is this means player can see in a different room
            {
                playerList.addAll(player.getSquare().getRoom().getPlayerRoomList()); //adds all the players in the room

            }

        }


        return playerList;
    }

    @Override
    public List<WeaponCard> checkReload(Player player) {
        List<WeaponCard> reloadableWeapons = new LinkedList<>(); //This is the weaponcard list i will return
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