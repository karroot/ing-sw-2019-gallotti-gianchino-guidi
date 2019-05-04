package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;

public class LockRifle extends WeaponCard implements DoDamage, MarkTarget
{
    private boolean[] avaiableMethod = new boolean[2];

    public LockRifle(Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 2;
        redAmmoCost = 0;
    }


    @Override
    public void doDamage(Player player, int quantity) {
        for (int i = 0; i < quantity; i++)
            player.doDamage(player.getColor());
    }

    @Override
    public void MarkTarget(Player player, int quantity) {
        for (int i = 0; i < quantity; i++)
            player.addMark(player.getColor());
    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[0] = false;
        avaiableMethod[1] = false;

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[1] = true;



        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<Player> checkBasicMode()
    {
        List<Player> playerList = (List<Player>) player.playerThatSee(player.getSquare().getGameBoard());//Obtain all the player that they are in same square


        return playerList;//Returns all targets
    }



    /**
     * It uses the basic mode of the lock rifle
     * @param player player affected by weapon
     */
    public void basicMode(Player player)
    {
        doDamage(player,2);
        MarkTarget(player,1);
    }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the lock rifle in alternative mode
     */
    public List<Player> checkSecondLock()
    {
        return checkBasicMode();
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param player player affected by weapon
     */
    public void SecondLockMode(Player player)
    {
        basicMode(player);
        MarkTarget(player,1);
    }


}