package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class THOR extends WeaponCard implements DoDamage
{
    private boolean[] avaiableMethod = new boolean[3];

    public THOR( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[0] = false;
        avaiableMethod[1] = false;
        avaiableMethod[2] = false;

        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[0] = true;
        if (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>1 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[2] = true;





        return avaiableMethod;

    }

    @Override
    public void doDamage(Player player, int quantity) {
        for (int i = 0; i < quantity; i++)
            player.doDamage(player.getColor());
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
    }
    public List<Player> checkChainReaction()
    {
        return checkBasicMode();
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param player1 player affected by weapon
     * @param  player2 second player affected by weapon
     */
    public void ChainReaction(Player player1 , Player player2) throws IllegalArgumentException
    {
        if (player1.equals(player2))
            throw new IllegalArgumentException("player1 must be different from player2");
        basicMode(player);
        doDamage(player2,1);

    }
    public List<Player> checkHighVoltage()
    {
        return checkBasicMode();
    }

    /**
     * It uses the basic mode of the lock rifle
     * @param player1 player affected by weapon
     * @param  player2 second player affected by weapon
     * @param  player3 third player affected by weapon
     */
    public void highVoltage(Player player1 , Player player2, Player player3) throws IllegalArgumentException
    {
        if (player1.equals(player2))
            throw new IllegalArgumentException("player1 must be different from player2");
        if (player2.equals(player3))
            throw new IllegalArgumentException("player2 must be different from player3");
        if (player1.equals(player3))
            throw new IllegalArgumentException("player1 must be different from player3");
        ChainReaction(player1,player2);

        doDamage(player3,1);

    }

}