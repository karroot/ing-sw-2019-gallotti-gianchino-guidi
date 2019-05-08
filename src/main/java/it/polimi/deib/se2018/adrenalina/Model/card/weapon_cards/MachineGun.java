package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class MachineGun extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[3];

    public MachineGun( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }



    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 3 the first represent the basic mode the second and third the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[1] = false;
        avaiableMethod[0] = false;
        avaiableMethod[2] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[0] = true;

        if (isLoaded()&& player.getAmmoYellow()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[2] = true;

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
     * @param player1 player affected by weapon
     * @param player2 player affected by weapon
     */
    public void basicMode(Player player1, Player player2) throws IllegalArgumentException
    {
        if (player1.equals(player2))
            throw new IllegalArgumentException("player1 must be different from player2");
        doDamage(player1,1);
        if (player2!= null)
        doDamage(player2,1);

    }

    /**
     * Return the list of all target available for using the focus shoot mode of this weapon
     * @return all player that can be affected with the lock rifle in focus shoot mode
     */
    public List<Player> checkFocusShotcMode()
    {
        List<Player> playerList = (List<Player>) player.playerThatSee(player.getSquare().getGameBoard());//Obtain all the player that they are in same square


        return playerList;//Returns all targets
    }



    /**
     * It uses the focus shot mode of the lock rifle
     * @param player1 player affected by weapon with an additive damage
     * @param player2 player affected by weapon
     */
    public void FocusShotcMode(Player player1, Player player2) throws  IllegalArgumentException
    {

        basicMode(player1,player2);
        doDamage(player1, 1);
    }
    /**
     * Return the list of all target available for using the focus Turret Tripode mode of this weapon
     * @return all player that can be affected with the lock rifle in focus shoot mode
     */
    public List<Player> checkTurretTripodeMode()
    {
        List<Player> playerList = (List<Player>) player.playerThatSee(player.getSquare().getGameBoard());//Obtain all the player that they are in same square



        return playerList;//Returns all targets
    }



    /**
     * It uses the Turret Tripode mode of the lock rifle
     * @param player1 player affected by weapon with an additive damage
     * @param player2 player affected by weapon
     */
    public void TurretTripode(Player player1, Player player2,Player player3, boolean addDamage, boolean basic) throws IllegalArgumentException
    {
        if (player3==null && !addDamage)
            throw new IllegalArgumentException("Mode: "+ name + " select at least one between damage player1 or damage player 3");//If this card doesn't belong at a player launch exception

        if (!(player3==null))
        {
            if (player3.equals(player2) || player3.equals(player1))
            {
                throw new IllegalArgumentException("player3 must be different from player2 and player1");
            }
            doDamage(player3, 1);
        }
        if (addDamage==true)
            if (basic==true){
                basicMode(player1,player2);
                doDamage(player1,1);
            }
            else{
                FocusShotcMode(player1,player2);
                doDamage(player1,1);
            }
    }

}