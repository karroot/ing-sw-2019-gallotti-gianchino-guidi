package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class implements the FRENZY status of a player.
 *
 * @author giovanni
 */

//todo manca doc

public class FRENZY extends StatePlayer
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
    public Set<Square> lookForRunAround(Player player)
    {
        if (!player.isFirst())
        {
            Set<Square>  squareSet;
            squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 4);
            return squareSet;

        }
        else
        {
           return null; //todo qui non si può chiamare se non primo. Mettere errore?
        }
    }

    @Override
    public Set<Square> lookForGrabStuff(Player player)
    {
        if (player.isFirst())
        {
            Set<Square> squareSet;
            squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 3);
            return squareSet;
        }
        else
        {
            Set<Square> squareSet;
            squareSet = player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX(), player.getSquare().getY(), 2);
            return squareSet;
        }

    }


    @Override
    public List<Player> lookForShootPeople(Player player)
    {
        return super.lookForShootPeople(player);
    }


    @Override
    public List<WeaponCard> checkReload(Player player)
    {
        return super.checkReload(player);
    }

}