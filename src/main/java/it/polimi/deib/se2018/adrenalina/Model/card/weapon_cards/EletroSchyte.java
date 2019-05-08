package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class EletroSchyte extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    public EletroSchyte( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 0;
    }


    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        avaiableMethod[1] = false; //I suppose that the modes can't be used
        avaiableMethod[0] = false;

        if (isLoaded() && player.getSquare().getPlayerList().size() > 1)//If the first mode can be used
            avaiableMethod[0] = true;

        if (isLoaded() && player.getAmmoBlue()>1 &&  player.getSquare().getPlayerList().size() > 1)//If the second mode can be used
            avaiableMethod[1] = true;



        return avaiableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the electroScythe in basic mode
     */
    public List<Player> checkBasicMode()
    {
        List<Player> playerList = player.getSquare().getPlayerList();//Obtain all the player that they are in same square

        playerList.remove(player); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param playerList  list of player affected by weapon
     */
    public void basicMode(List<Player> playerList)
    {
        for(Player p:playerList) {
            doDamage(p, 1);
        }
    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the electroScythe in basic mode
     */
    public List<Player> checkReaper()
    {
        List<Player> playerList = player.getSquare().getPlayerList();//Obtain all the player that they are in same square

        playerList.remove(player); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param playerList  list of player affected by weapon
     */
    public void reaper(List<Player> playerList)
    {
        for(Player p:playerList) {
            doDamage(p, 2);
        }
    }

}