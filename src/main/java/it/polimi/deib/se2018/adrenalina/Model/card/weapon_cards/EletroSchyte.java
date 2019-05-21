package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class EletroSchyte extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];


    /**
     * Create the card ElectroSchyte
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public EletroSchyte( Color color, int weaponID, boolean isLoaded) throws NullPointerException
    {
        super( color, weaponID, isLoaded);
        this.name = "ElectroSchyte";
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 0;
    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");//If this card doesn't belong at a player launch exception

        avaiableMethod[1] = false; //I suppose that the modes can't be used
        avaiableMethod[0] = false;

        if (this.isLoaded() && player.getSquare().getPlayerList().size() > 1)//If the first mode can be used
            avaiableMethod[0] = true;

        if (this.isLoaded() && player.getAmmoBlue()>1 &&  player.getSquare().getPlayerList().size() > 1)//If the second mode can be used
            avaiableMethod[1] = true;



        return avaiableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the electroScythe in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException(" Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = player.getSquare().getPlayerList();//Obtain all the player that they are in same square

        playerList.remove(player); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * It uses the basic mode of the ElectroSchyte
     * @param playerList  list of player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(List<Player> playerList)
    {

            for (Player p : playerList) {
                doDamage(p, 1);
            }
        isLoaded = false;
        }



    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the electroScythe in basic mode
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public List<Player> checkReaper() throws IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        List<Player> playerList = player.getSquare().getPlayerList();//Obtain all the player that they are in same square

        playerList.remove(player); //Remove from targets the player that shoot

        return playerList;//Returns all targets
    }

    /**
     * It uses the basic mode of the weapon
     * @param playerList  list of player affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void reaper(List<Player> playerList) throws  IllegalStateException
    {

        if (!checkAvaliableMode()[1])
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for(Player p:playerList) {
            doDamage(p, 2);
        }
        isLoaded = false;
        this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        this.player.setAmmoRed(this.player.getAmmoRed() - 1);
    }

}