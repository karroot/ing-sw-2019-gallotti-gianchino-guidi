package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

/**
 * 
 */
public class Shotgun extends WeaponCard implements DoDamage, MoveTarget
{

    private boolean[] avaiableMethod = new boolean[2];


    public Shotgun(String name, Color color, int weaponID, boolean isLoaded)
    {
        super(name, color, weaponID, isLoaded);
        yellowAmmoCost = 2;
        blueAmmoCost = 0;
        redAmmoCost = 0;

    }

    @Override
    public void moveTarget()
    {

    }

    public void doDamage()
    {

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

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1)
            avaiableMethod[0] = true;

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1)
            avaiableMethod[1] = true;



        return avaiableMethod;

    }

    public void basicMode(Player player, boolean move, int x,int y)
    {

    }

    public void inLongBarrelMode(Player player)
    {

    }


}