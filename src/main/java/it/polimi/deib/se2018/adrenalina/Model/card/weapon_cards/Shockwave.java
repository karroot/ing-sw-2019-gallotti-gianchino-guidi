package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Shockwave extends WeaponCard
{

    private boolean[] avaiableMethod = new boolean[2];

    public Shockwave( Color color, int weaponID, boolean isLoaded)
    {
        super( color, weaponID, isLoaded);
        this.name = "Shockwave";
        yellowAmmoCost = 1;
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

        //If there are at least 3 players and one in each square
        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 3 && MethodsWeapons.thereIsAPlayerInEachSquare(player))// the first mode can be used
            avaiableMethod[0] = true;

        if (isLoaded() && MethodsWeapons.playersReachable(player.getSquare(),1).size() > 1)//If the second mode can be used
            avaiableMethod[1] = true;



        return avaiableMethod;

    }

    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the shotgun in basic mode
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        Set<Player> target = MethodsWeapons.playersReachable(player.getSquare(),1); //Obtain all players that can be seen

        target.remove(player);//Remove the player that has this card

        return new ArrayList<>(target);//Returns all targets
    }

    /**
     * It uses the basic mode of the Shockwave
     * @param players players affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(List<Player> players) throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])//check mode
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        for (Player x:players)//For each player
        {
            doDamage(player,1);//Do one damage
        }

        isLoaded = false;

    }

    /**
     * It uses the alternative mode of the Shockwave
     * It does one damage at all players at distance 1
     * @param players players affected by weapon
     * @exception IllegalStateException if the alternative mode can't be used
     */
    public void inTsunamirMode(List<Player> players) throws IllegalStateException
    {
        if (!checkAvaliableMode()[1])//Check mode
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        for (Player x:players) //For each player
        {
            doDamage(x,1);//Do one damage
        }

        isLoaded = false;
    }

}