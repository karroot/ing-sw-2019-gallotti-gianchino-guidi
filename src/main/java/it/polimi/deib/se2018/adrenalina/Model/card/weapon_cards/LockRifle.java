package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class LockRifle extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[2];

    public LockRifle(Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        blueAmmoCost = 2;
        yellowAmmoCost = 0;

        redAmmoCost = 0;
    }




    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player==null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[1] = false;
        avaiableMethod[0] = false;




        if (isLoaded()&& player.getAmmoRed()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[1] = true;

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[0] = true;


        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<Player> checkBasicMode() throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma LockRifle non eseguibile");

        List<Player> listOfPlayer = new LinkedList<>();
        listOfPlayer.addAll(player.playerThatSee(player.getSquare().getGameBoard()));


        return listOfPlayer;//Returns all targets
    }



    /**
     * It uses the basic mode of the lock rifle
     * @param player player affected by weapon
     */
    public void basicMode(Player player, boolean SecondLockMode) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        if(SecondLockMode)
        {
            if (!checkAvaliableMode()[1])
                throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

            markTarget(player,1);
            this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        }
        doDamage(player,2);
        markTarget(player,1);
        this.isLoaded = false;
    }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the lock rifle in alternative mode
     */
    public List<Player> checkSecondLock() throws  IllegalStateException
    {
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        return checkBasicMode();
    }



}