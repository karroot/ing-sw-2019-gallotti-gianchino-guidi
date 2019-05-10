package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.Set;

public class PowerGlow extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    /**
     * Create the card PowerGlow
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public PowerGlow( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        this.name = "PowerGlow";
        yellowAmmoCost = 2;
        blueAmmoCost = 0;
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

        avaiableMethod[0] = false; //I suppose that the modes can't be used
        avaiableMethod[1] = false;

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1)//If the first mode can be used
            avaiableMethod[0] = true;

        //Checking second mode
        //For each player reachable to distance 1 see if there are other player reachable to distance 1 in same direction
        Set<Player> players = MethodsWeapons.playersReachable(player.getSquare(), 1);

        for (Player x : players)
        {

        }


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size() > 3 )//If the second mode can be used
            avaiableMethod[1] = true;



        return avaiableMethod;

    }

}