package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class LockRifle extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[2];

    /**
     * Create the card LockRifle
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public LockRifle(Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        this.name = "LockRifle";
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


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[1] = true;




        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @exception IllegalStateException if the basic mode can't be used
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
     * @param player2 player affected by the additional mode
     * @param  SecondLockMode is true if additional mode is active
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player,Player player2 ,boolean SecondLockMode) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        if(SecondLockMode)
        {
            if (!checkAvaliableMode()[1])
                throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

            markTarget(player2,1);
            this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        }
        doDamage(player,2);
        markTarget(player,1);
        this.isLoaded = false;
    }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the lock rifle in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<Player> checkSecondLock() throws  IllegalStateException
    {
        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        return checkBasicMode();
    }



}