package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Whisper extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[1];


    public Whisper( Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 1;
        blueAmmoCost = 2;
        redAmmoCost = 0;
    }

    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
       Set<Player> playerdistance2and1= new HashSet<>();
       Set<Player> playerdistance1= new HashSet<>();
       Set<Player> playerdistance2= new HashSet<>();

        playerdistance1.addAll(MethodsWeapons.playersReachable(player.getSquare(),1));
        playerdistance2and1.addAll(MethodsWeapons.playersReachable(player.getSquare(),2));
        playerdistance2and1.removeAll(playerdistance1);
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

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
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = (List<Player>) player.playerThatSee(player.getSquare().getGameBoard());


        return playerList;//Returns all targets
    }


    public void basicMode(Player player)  throws  IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        doDamage(player, 3);
        markTarget(player, 1);

        this.isLoaded = false;
    }
}