package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Whisper extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[1];

    /**
     * Create the card Whisper
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public Whisper( Color color, int weaponID, boolean isLoaded) throws NullPointerException {
        super( color, weaponID, isLoaded);
        this.name = "Whisper";
        yellowAmmoCost = 1;
        blueAmmoCost = 2;
        redAmmoCost = 0;
    }
    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 1 the first represent the basic mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
       Set<Player> playerdistance2and1= new HashSet<>();
       Set<Player> playerdistance1= new HashSet<>();

        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        playerdistance1.addAll(MethodsWeapons.playersReachable(player.getSquare(),1));
        playerdistance2and1.addAll(MethodsWeapons.playersReachable(player.getSquare(),2));
        playerdistance2and1.removeAll(playerdistance1);


        avaiableMethod[0] = false;




        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1 &&  !playerdistance2and1.isEmpty())
            avaiableMethod[0] = true;



        return avaiableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        Set<Player> playerdistance2and1= new HashSet<>();
        Set<Player> playerdistance1= new HashSet<>();
        List<Player> pl = new LinkedList<>();
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        playerdistance1.addAll(MethodsWeapons.playersReachable(player.getSquare(),1));
        playerdistance2and1.addAll(MethodsWeapons.playersReachable(player.getSquare(),2));
        playerdistance2and1.removeAll(playerdistance1);
        pl.addAll(playerdistance2and1);

        return  pl;//Returns all targets
    }

    /**
     * It uses the basic mode of the Whisper
     * @param player player affected by weapon
     * @exception IllegalStateException if the basic mode can't be used
     */
    public void basicMode(Player player)  throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player, 3);
        markTarget(player, 1);

        this.isLoaded = false;
    }
}