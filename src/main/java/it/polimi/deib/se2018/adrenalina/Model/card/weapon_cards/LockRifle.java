package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.*;

import java.util.*;
import java.util.stream.Collectors;

public class LockRifle extends WeaponCard
{
    private boolean[] availableMethod = new boolean[2];

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
        this.name = "Distruttore";
        blueAmmoCost = 2;
        yellowAmmoCost = 0;
        redAmmoCost = 0;
    }

    /**
     * this method is used to use the weapon
     * @param responseMessage response message specified for the weapon
     */
    @Override
    public void useWeapon(ResponseInput responseMessage) {
        basicMode(((ResponseLockRifle) responseMessage).getTargetBasicMode(),((ResponseLockRifle) responseMessage).getTargetsAdditionalMode(),((ResponseLockRifle) responseMessage).isMode());
    }

    /**
     * method used to return the reqeuest message
     * @return the asked request input
     */
    public RequestInput getRequestMessage()
    {
        if (checkAvailableMode()[0] && checkAvailableMode()[1])
            return new RequestLockRifle(checkAvailableMode(),checkBasicMode(),checkSecondLock());
        else
            return new RequestLockRifle(checkAvailableMode(),checkBasicMode(),new LinkedList<>());
    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player==null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore.");

        availableMethod[1] = false;
        availableMethod[0] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1 )
            availableMethod[0] = true;

        if (isLoaded()&& player.getAmmoRed()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>2)
            availableMethod[1] = true;




        return availableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<ColorId> checkBasicMode() throws  IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");
        List<ColorId> playerList = new LinkedList<>();
        for (Player p : player.playerThatSee(player.getSquare().getGameBoard()) )
        {
            if(!p.equals(this.player))
                playerList.add(p.getColor());
        }


        return playerList;//Returns all targets
    }



    /**
     * It uses the basic mode of the lock rifle
     * @param colorPlayer player affected by weapon
     * @param colorPlayer2 player affected by the additional mode
     * @param  SecondLockMode is true if additional mode is active
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(ColorId colorPlayer, ColorId colorPlayer2 , boolean SecondLockMode) throws IllegalStateException
    {
        if (!checkAvailableMode()[0])
            throw  new IllegalStateException("Modalità base dell'arma "+name+" non eseguibile.");

        if(SecondLockMode)
        {
            if (!checkAvailableMode()[1])
                throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");


            for(Player p : player.getSquare().getGameBoard().getAllPlayer())
            {
                if(p.getColor().equals(colorPlayer2))
                    markTarget(p,1);
            }
            if(this.player.getSquare().getGameBoard().isTerminatorMode())
            {
                if(colorPlayer2.equals(ColorId.PURPLE))
                {
                    markTarget(player.getSquare().getGameBoard().getTermi(),1);
                }

            }
            this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        }
        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),2);
        markTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);
        if(this.player.getSquare().getGameBoard().isTerminatorMode())
        {
            if(colorPlayer.equals(ColorId.PURPLE))
            {
                doDamage(player.getSquare().getGameBoard().getTermi(),2);
                markTarget(player.getSquare().getGameBoard().getTermi(),1);
            }

        }

        this.isLoaded = false;
    }

    /**
     * Return the list of all target available for using the alternative mode of this weapon
     * @return all player that can be affected with the lock rifle in alternative mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<ColorId> checkSecondLock() throws  IllegalStateException
    {
        if (!checkAvailableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma "+name+" non eseguibile.");

        return checkBasicMode();
    }



}